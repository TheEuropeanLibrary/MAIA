/* AnnotationBasedByteConverter.java - created on 5 de Abr de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.xml.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.theeuropeanlibrary.maia.common.converter.ConverterException;
import org.theeuropeanlibrary.maia.common.FieldId;
import org.theeuropeanlibrary.maia.converter.xml.common.XmlFieldConverter;
import org.theeuropeanlibrary.maia.converter.xml.common.XmlFieldSerializer;
import org.theeuropeanlibrary.maia.converter.xml.util.XmlElementDefinition;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Performs XML serialization of object in the Object Model, using java
 * reflection and the FieldId annotations on the class fields.
 *
 * @param <T> class to be serialized
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @since 9 de Mai de 2011
 */
public class AnnotationBasedXmlConverter<T> implements XmlFieldConverter<T> {

    private final Class<T> theClass;
    private final ArrayList<XmlElementDefinition> idIndexedFieldArray;
    private final HashMap<String, XmlElementDefinition> xmlNameToField;

    /**
     * Creates a new instance of this class.
     *
     * @param theClass The class to be Serialized
     * @param customSerializers Serializers for particular fields of the class
     */
    public AnnotationBasedXmlConverter(Class<T> theClass,
            Map<Integer, XmlFieldSerializer> customSerializers) {
        this.theClass = theClass;
        xmlNameToField = new HashMap<>();
        ArrayList<XmlElementDefinition> fieldsWithAnnotatoins = new ArrayList<>();
        HashMap<Integer, XmlElementDefinition> idToFieldMap = new HashMap<>();
        int maxFieldId = initFieldsFromClass(theClass, customSerializers, fieldsWithAnnotatoins,
                idToFieldMap);

        idIndexedFieldArray = new ArrayList<>(maxFieldId + 1);
        for (int i = 0; i <= maxFieldId; i++) {
            idIndexedFieldArray.add(idToFieldMap.get(i));
        }
    }

    private int initFieldsFromClass(Class<?> theClass,
            Map<Integer, XmlFieldSerializer> customEncoders,
            ArrayList<XmlElementDefinition> fieldsWithAnnotatoins,
            HashMap<Integer, XmlElementDefinition> idToFieldMap) {
        int maxFieldId = 0;
        if (!theClass.getSuperclass().equals(Object.class)) {
            maxFieldId = initFieldsFromClass(theClass.getSuperclass(), customEncoders,
                    fieldsWithAnnotatoins, idToFieldMap);
        }

        for (Field f : theClass.getDeclaredFields()) {
            FieldId ann = f.getAnnotation(FieldId.class);
            if (ann != null) {
                XmlFieldSerializer fldConv = null;
                maxFieldId = Math.max(maxFieldId, ann.value());
                String capFieldName = Character.toUpperCase(f.getName().charAt(0))
                        + f.getName().substring(1);
                Method setMethod;
                Method getMethod;
                try {
                    setMethod = theClass.getDeclaredMethod("set" + capFieldName, f.getType());
                    try {
                        getMethod = theClass.getDeclaredMethod("get" + capFieldName);
                    } catch (NoSuchMethodException e) {
                        getMethod = theClass.getDeclaredMethod("is" + capFieldName);
                    }
                    if (customEncoders != null) {
                        fldConv = customEncoders.get(ann.value());
                    }
                    if (fldConv == null) {
                        fldConv = new BaseTypeXmlSerializer(f.getType());
                    }
                    fldConv.configure(setMethod, getMethod);
                } catch (SecurityException e) {
                    throw new RuntimeException("No access to get/set method for field "
                            + f.getName(), e);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException("Missing get/set method for field " + f.getName(), e);
                }

                XmlElementDefinition xmlDef = new XmlElementDefinition(capFieldName, fldConv);
                // fieldToIdMap.put(f, ann.id());
                idToFieldMap.put(ann.value(), xmlDef);
                fieldsWithAnnotatoins.add(xmlDef);
                xmlNameToField.put(capFieldName, xmlDef);
            }
        }
        return maxFieldId;
    }

    @Override
    public T decode(Element xmlElement) throws ConverterException {
        try {
            T obj = theClass.newInstance();
            NodeList childNodes = xmlElement.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                if (item instanceof Element) {
                    Element el = (Element) item;
                    XmlElementDefinition fieldDef = xmlNameToField.get(el.getLocalName());

                    fieldDef.getSerializer().decode(obj, el);
                }
            }
            return obj;
        } catch (InstantiationException e) {
            throw new RuntimeException("Class default contructor thrown exception", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Class does not provide an accesible default contructor", e);
        }
    }

    @Override
    public void encode(Object bean, Element xmlParentElement) throws ConverterException {
        if (!bean.getClass().equals(theClass)) {
            throw new IllegalArgumentException("Invalid type. Received: "
                    + bean.getClass().getName() + " Expected: "
                    + theClass.getName());
        }

        for (int i = 1; i < idIndexedFieldArray.size(); i++) {
            XmlElementDefinition conv = idIndexedFieldArray.get(i);
            if (conv != null) {
                conv.getSerializer().encode(bean, xmlParentElement, conv.getElementName());
            }
        }
    }

    /**
     * @return serialized class
     */
    @SuppressWarnings("rawtypes")
    public Class getSerializedClass() {
        return theClass;
    }

    /**
     * @return elements
     */
    public List<XmlElementDefinition> getElements() {
        return idIndexedFieldArray;
    }
}
