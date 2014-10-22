/* ObjectToXmlFieldSerializer.java - created on 2 de Mai de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.xml.serializer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.theeuropeanlibrary.maia.common.converter.ConverterException;
import org.theeuropeanlibrary.maia.converter.xml.AbstractEntityXmlConverter;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

/**
 * Wraps an <code>AnnotationBasedXmlSerializer</code> into a
 * <code>XmlFieldSerializer</code> for use in the serialization of nested in
 * other objects.
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @date 2 de Mai de 2011
 */
public class ObjectToXmlFieldSerializer implements XmlFieldSerializer {

    @SuppressWarnings("rawtypes")
    private XmlFieldConverter objectSerializer;
    private Method fieldSet;
    private Method fieldGet;

    /**
     * Creates a new instance of this class.
     *
     * @param objectSerializer
     */
    @SuppressWarnings("rawtypes")
    public ObjectToXmlFieldSerializer(XmlFieldConverter objectSerializer) {
        super();
        this.objectSerializer = objectSerializer;
    }

    @Override
    public void configure(Method fieldSet, Method fieldGet) {
        this.fieldGet = fieldGet;
        this.fieldSet = fieldSet;
    }

    @Override
    public void decode(Object bean, Element xmlElement) throws ConverterException {
        try {
            fieldSet.invoke(bean, objectSerializer.decode(xmlElement));
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new ConverterException("Exception during serialization", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Element encode(Object bean, Element xmlParentElement, String elementName) throws ConverterException {
        try {
            Object value = fieldGet.invoke(bean);
            if (value != null) {
                Element fieldElement = xmlParentElement.getOwnerDocument().createElementNS(
                        AbstractEntityXmlConverter.XML_NAMESPACE, elementName);
                objectSerializer.encode(value, fieldElement);
                xmlParentElement.appendChild(fieldElement);
                return fieldElement;
            }
            return null;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | ConverterException | DOMException e) {
            throw new ConverterException("Exception during serialization", e);
        }
    }

    /**
     * @return serializer
     */
    @SuppressWarnings("rawtypes")
    public XmlFieldConverter getObjectSerializer() {
        return objectSerializer;
    }
}
