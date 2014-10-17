/* CollectionFieldBufferConverter.java - created on 6 de Abr de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.xml.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import org.theeuropeanlibrary.maia.common.ConverterException;
import org.theeuropeanlibrary.maia.converter.xml.AbstractEntityXmlConverter;
import org.theeuropeanlibrary.maia.converter.xml.common.XmlFieldConverter;
import org.theeuropeanlibrary.maia.converter.xml.common.XmlFieldSerializer;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

/**
 * Handles the serialization of java Collections in XML
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @param <T> The type of class inside the collection serialized
 * @date 6 de Abr de 2011
 */
public class CollectionXmlFieldSerializer<T> implements XmlFieldSerializer {

    private Method fieldGet;
    private final XmlFieldConverter<T> itemSerializer;

    /**
     * Creates a new instance of this class.
     *
     * @param itemSerializer the converter for the items in the collection
     */
    public CollectionXmlFieldSerializer(XmlFieldConverter<T> itemSerializer) {
        this.itemSerializer = itemSerializer;
    }

    @Override
    public void configure(Method fieldSet, Method fieldGet) {
        this.fieldGet = fieldGet;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void decode(Object bean, Element xmlElement) throws ConverterException {
        try {
            Collection collection = (Collection) fieldGet.invoke(bean);
            collection.add(decode(xmlElement));
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new ConverterException("Exception during serialization", e);
        }
    }

    private Object decode(Element xmlElement) throws ConverterException {
        return itemSerializer.decode(xmlElement);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Element encode(Object bean, Element xmlParentElement, String elementName) throws ConverterException {
        try {
            Collection<T> collection = (Collection<T>) fieldGet.invoke(bean);
            for (T obj : collection) {
                if (obj != null) {
                    Element fieldElement = xmlParentElement.getOwnerDocument().createElementNS(
                            AbstractEntityXmlConverter.XML_NAMESPACE, elementName);
                    itemSerializer.encode(obj, fieldElement);
                    xmlParentElement.appendChild(fieldElement);
                }
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
    public XmlFieldConverter getItemSerializer() {
        return itemSerializer;
    }
}
