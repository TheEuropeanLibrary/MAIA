/* AnnotationBasedByteConverter.java - created on 5 de Abr de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.theeuropeanlibrary.maia.common.FieldId;
import org.theeuropeanlibrary.maia.converter.json.basetype.BaseTypeJsonFactory;

/**
 * Performs XML serialization of object in the Object Model, using java
 * reflection and the FieldId annotations on the class fields.
 *
 * @param <T> class to be serialized
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @since 9 de Mai de 2011
 */
public class AnnotationBasedJsonSerializer<T> extends JsonSerializer<T> {

    private final Class<T> serializeClass;
    private final List<JsonFieldSerializer> idIndexedFieldArray;
    private final List<String> xmlNameToField;

    /**
     * Creates a new instance of this class.
     *
     * @param serializeClass The class to be Serialized
     * @param customSerializers serializers for particular fields of the class
     */
    public AnnotationBasedJsonSerializer(Class<T> serializeClass) {
        this(serializeClass, null);
    }

    /**
     * Creates a new instance of this class.
     *
     * @param serializeClass The class to be Serialized
     * @param customSerializers serializers for particular fields of the class
     */
    public AnnotationBasedJsonSerializer(Class<T> serializeClass,
            Map<Integer, JsonFieldSerializer> customSerializers) {
        this.serializeClass = serializeClass;

        List<JsonFieldSerializer> fieldsWithAnnotatoins = new ArrayList<>();
        Map<Integer, JsonFieldSerializer> idToFieldMap = new HashMap<>();
        Map<Integer, String> nameToFieldMap = new HashMap<>();
        int maxFieldId = initFieldsFromClass(serializeClass, customSerializers, fieldsWithAnnotatoins,
                idToFieldMap, nameToFieldMap);

        xmlNameToField = new ArrayList<>(maxFieldId + 1);
        idIndexedFieldArray = new ArrayList<>(maxFieldId + 1);
        for (int i = 0; i <= maxFieldId; i++) {
            idIndexedFieldArray.add(idToFieldMap.get(i));
            xmlNameToField.add(nameToFieldMap.get(i));
        }
    }

    private int initFieldsFromClass(Class<?> initClass,
            Map<Integer, JsonFieldSerializer> customEncoders,
            List<JsonFieldSerializer> fieldsWithAnnotatoins,
            Map<Integer, JsonFieldSerializer> idToFieldMap, Map<Integer, String> nameToFieldMap) {
        int maxFieldId = 0;
        if (!initClass.getSuperclass().equals(Object.class)) {
            maxFieldId = initFieldsFromClass(initClass.getSuperclass(), customEncoders,
                    fieldsWithAnnotatoins, idToFieldMap, nameToFieldMap);
        }

        for (Field f : initClass.getDeclaredFields()) {
            FieldId ann = f.getAnnotation(FieldId.class);
            if (ann != null) {
                JsonFieldSerializer fldConv = null;
                maxFieldId = Math.max(maxFieldId, ann.value());
                String capFieldName = Character.toUpperCase(f.getName().charAt(0))
                        + f.getName().substring(1);
                Method getMethod;
                try {
                    try {
                        getMethod = initClass.getDeclaredMethod("get" + capFieldName);
                    } catch (NoSuchMethodException e) {
                        getMethod = initClass.getDeclaredMethod("is" + capFieldName);
                    }
                    if (customEncoders != null) {
                        fldConv = customEncoders.get(ann.value());
                    }
                    if (fldConv == null) {
                        JsonSerializer serializer = BaseTypeJsonFactory.newFieldSerializerFor(f.getType());
                        if (serializer == null) {
                            serializer = new AnnotationBasedJsonSerializer(f.getType());
                        }
                        fldConv = new JsonFieldSerializer(serializer);
                    }
                    fldConv.configure(getMethod);
                } catch (SecurityException e) {
                    throw new RuntimeException("No access to get/set method for field "
                            + f.getName(), e);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException("Missing get/set method for field " + f.getName(), e);
                }

                // fieldToIdMap.put(f, ann.id());
                idToFieldMap.put(ann.value(), fldConv);
                nameToFieldMap.put(ann.value(), capFieldName);
                fieldsWithAnnotatoins.add(fldConv);
            }
        }
        return maxFieldId;
    }

    @Override
    public void serialize(T bean, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        if (!bean.getClass().equals(serializeClass)) {
            throw new IllegalArgumentException("Invalid type. Received: "
                    + bean.getClass().getName() + " Expected: "
                    + serializeClass.getName());
        }

        for (int i = 1; i < idIndexedFieldArray.size(); i++) {
            JsonFieldSerializer conv = idIndexedFieldArray.get(i);
            if (conv != null) {
                jg.writeFieldName(xmlNameToField.get(i));
                conv.serialize(bean, jg, sp);
            }
        }
    }

    /**
     * @return serialized class
     */
    @SuppressWarnings("rawtypes")
    public Class getSerializedClass() {
        return serializeClass;
    }

    /**
     * @return elements
     */
    public List<JsonFieldSerializer> getElements() {
        return idIndexedFieldArray;
    }
}
