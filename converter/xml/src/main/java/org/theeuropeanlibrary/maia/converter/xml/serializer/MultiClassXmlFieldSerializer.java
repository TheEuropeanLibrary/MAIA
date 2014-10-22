/* CollectionFieldBufferConverter.java - created on 6 de Abr de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.xml.serializer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.theeuropeanlibrary.maia.common.converter.ConverterException;
import org.theeuropeanlibrary.maia.converter.xml.AbstractEntityXmlConverter;
import org.w3c.dom.DOMException;

import org.w3c.dom.Element;

/**
 * A serializer that checks at runtime, the class of the object to be serialized
 * and uses the appropriate serializer.
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @date 2 de May de 2011
 */
@SuppressWarnings("rawtypes")
public class MultiClassXmlFieldSerializer implements XmlFieldSerializer {

    private Method fieldSet;
    private Method fieldGet;
    private final Map<Class, XmlFieldConverter> converters = new HashMap<>();
    private final Map<String, Class> simpleClassNames = new HashMap<>();

    /**
     * Creates a new instance of this class.
     */
    public MultiClassXmlFieldSerializer() {
    }

    @Override
    public void configure(Method fieldSet, Method fieldGet) {
        this.fieldSet = fieldSet;
        this.fieldGet = fieldGet;
    }

    @Override
    public void decode(Object bean, Element xmlElement) throws ConverterException {
        try {
            XmlFieldConverter converter = null;
            Element subEl = (Element) xmlElement.getChildNodes().item(0);
            // Element subEl=XmlUtil.elements(xmlElement).iterator().next();
            String type = subEl.getNodeName();
            Class classOfValue = simpleClassNames.get(type);
            converter = converters.get(classOfValue);
            fieldSet.invoke(bean, converter.decode(subEl));
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | ConverterException e) {
            throw new ConverterException("Exception during serialization", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Element encode(Object bean, Element xmlParentElement, String elementName) throws ConverterException {
        try {
            Object obj = fieldGet.invoke(bean);
            if (obj != null) {
                Element containerEl = xmlParentElement.getOwnerDocument().createElementNS(
                        AbstractEntityXmlConverter.XML_NAMESPACE, elementName);
                xmlParentElement.appendChild(containerEl);
                Element valueEl = xmlParentElement.getOwnerDocument().createElementNS(
                        AbstractEntityXmlConverter.XML_NAMESPACE, obj.getClass().getSimpleName());
                containerEl.appendChild(valueEl);
                XmlFieldConverter converter = converters.get(obj.getClass());
                converter.encode(obj, valueEl);
                return containerEl;
            }
            return null;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | DOMException e) {
            throw new ConverterException("Exception during serialization", e);
        }
    }

    /**
     * Adds a supported Class for conversion, and the corresponding converter
     *
     * @param convertedClass
     * @param converter
     */
    public void add(Class convertedClass, XmlFieldConverter converter) {
        converters.put(convertedClass, converter);
        simpleClassNames.put(convertedClass.getSimpleName(), convertedClass);
    }

    /**
     * @return serializer
     */
    public Map<Class, XmlFieldConverter> getConverters() {
        return converters;
    }
}
