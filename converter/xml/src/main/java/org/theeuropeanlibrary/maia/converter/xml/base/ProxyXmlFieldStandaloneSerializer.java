/* ProxyXmlFieldSerializer.java - created on 6 de Jul de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.xml.base;

import org.theeuropeanlibrary.maia.common.converter.ConverterException;
import org.theeuropeanlibrary.maia.converter.xml.common.XmlFieldConverter;
import org.w3c.dom.Element;

/**
 * A proxy for a serializer. This is needed in order to specify serializers
 * which are recursive during initialization
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @param <T> Serialized type
 * @date 6 de Jul de 2011
 */
public class ProxyXmlFieldStandaloneSerializer<T> implements XmlFieldConverter<T> {

    private XmlFieldConverter<T> actualSerializer;

    @Override
    public T decode(Element xmlElement) throws ConverterException {
        return actualSerializer.decode(xmlElement);
    }

    @Override
    public void encode(T bean, Element xmlParentElement) throws ConverterException {
        actualSerializer.encode(bean, xmlParentElement);
    }

    /**
     * @param actualConverter the proxied serializer
     */
    public void setSerializer(XmlFieldConverter<T> actualConverter) {
        this.actualSerializer = actualConverter;
    }

    /**
     * @return serializer
     */
    public XmlFieldConverter<T> getProxiedSerializer() {
        return actualSerializer;
    }
}
