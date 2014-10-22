/* EditionBeanBytesConverter.java - created on 5 de Abr de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.xml.serializer;

import org.apache.commons.lang.StringUtils;
import org.theeuropeanlibrary.maia.converter.xml.basetype.BaseTypeEncoder;
import org.w3c.dom.Element;

/**
 * Handles the serialization to XML of simple java types (Integers, Strings,
 * etc)
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @param <T> The type of class serialized
 * @date 9 de Mai de 2011
 */
public class BaseTypeEncoderBasedXmlSerializer<T> implements
        XmlFieldConverter<T> {

    @SuppressWarnings("rawtypes")
    BaseTypeEncoder encoder;

    /**
     * Creates a new instance of this class.
     *
     * @param encoder
     */
    public BaseTypeEncoderBasedXmlSerializer(@SuppressWarnings("rawtypes") BaseTypeEncoder encoder) {
        super();
        this.encoder = encoder;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T decode(Element xmlElement) {
        try {
            String content = xmlElement.getTextContent();
            content = StringUtils.trimToEmpty(content);
            return (T) encoder.decodeFromString(content);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void encode(T bean, Element xmlParentElement) {
        try {
            xmlParentElement.setTextContent(encoder.encodeToString(bean));
        } catch (Exception e) {
            throw new RuntimeException(e);
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
