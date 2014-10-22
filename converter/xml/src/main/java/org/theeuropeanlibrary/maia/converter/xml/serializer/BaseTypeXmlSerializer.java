/* FieldBufferSetter.java - created on 5 de Abr de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.xml.serializer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.theeuropeanlibrary.maia.common.converter.ConverterException;
import org.theeuropeanlibrary.maia.converter.xml.AbstractEntityXmlConverter;

import org.theeuropeanlibrary.maia.converter.xml.basetype.BaseTypeEncoder;
import org.theeuropeanlibrary.maia.converter.xml.basetype.BaseTypeEncoderFactory;
import org.w3c.dom.Element;

/**
 * /** A <code>FieldConverterInterface</code> implementation for base types
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @date 5 de Abr de 2011
 */
public class BaseTypeXmlSerializer implements XmlFieldSerializer {

    private Method fieldSet;
    private Method fieldGet;
    private int id;
    private String xmlElementName;
    @SuppressWarnings("rawtypes")
    private final BaseTypeEncoder encoder;

    /**
     * Creates a new instance of this class.
     *
     * @param theClass
     */
    public BaseTypeXmlSerializer(Class<?> theClass) {
        this.encoder = BaseTypeEncoderFactory.newFieldEncoderFor(theClass);
    }

    /**
     * Creates a new instance of this class.
     *
     * @param baseTypeEncoder
     */
    @SuppressWarnings("rawtypes")
    public BaseTypeXmlSerializer(BaseTypeEncoder baseTypeEncoder) {
        this.encoder = baseTypeEncoder;
    }

    @Override
    public void configure(Method fieldSet, Method fieldGet) {
        this.fieldSet = fieldSet;
        this.fieldGet = fieldGet;
        xmlElementName = fieldSet.getName().substring(3);
    }

    /**
     * @param xmlElement
     * @return object from xml element
     */
    public Object decode(final Element xmlElement) {
        try {
            return encoder.decodeFromString(xmlElement.getTextContent());
        } catch (Exception e) {
            throw new RuntimeException("Exception during serialization", e);
        }
    }

    @Override
    public void decode(Object bean, Element xmlElement) throws ConverterException {
        try {
            fieldSet.invoke(bean, decode(xmlElement));
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
                        AbstractEntityXmlConverter.XML_NAMESPACE, xmlElementName);
                fieldElement.setTextContent(encoder.encodeToString(value));
                xmlParentElement.appendChild(fieldElement);
                return fieldElement;
            }
            return null;
        } catch (Exception e) {
            throw new ConverterException("Exception during serialization", e);
        }
    }

    /**
     * @return encoder
     */
    @SuppressWarnings("rawtypes")
    public BaseTypeEncoder getEncoder() {
        return encoder;
    }
}
