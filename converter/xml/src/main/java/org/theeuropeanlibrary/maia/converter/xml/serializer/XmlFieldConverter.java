package org.theeuropeanlibrary.maia.converter.xml.serializer;

import org.theeuropeanlibrary.maia.common.converter.ConverterException;
import org.w3c.dom.Element;

/**
 * The interface for Serialization implementations of objects in XML
 *
 * @param <T> type of class serialized
 * @author Nuno Freire (nuno.freire@theeuropeanlibrary.org)
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public interface XmlFieldConverter<T> {

    /**
     * Reads the object from the XML representation, and converts it to a java
     * object
     *
     * @param xmlElement the XML representation of the object
     * @return the converted java object
     * @throws ConverterException
     */
    T decode(Element xmlElement) throws ConverterException;

    /**
     * Serializes an object into XML
     *
     * @param bean the object to Serialize
     * @param xmlParentElement the parent XML element where the object should be
     * serialized
     * @throws ConverterException
     */
    void encode(T bean, Element xmlParentElement) throws ConverterException;
}
