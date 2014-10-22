package org.theeuropeanlibrary.maia.converter.xml.serializer;

import java.lang.reflect.Method;
import org.theeuropeanlibrary.maia.common.converter.ConverterException;

import org.w3c.dom.Element;

/**
 * Handles the serialization of fields using the reflection based serialization
 *
 * @author Nuno Freire (nuno.freire@theeuropeanlibrary.org)
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public interface XmlFieldSerializer {

    /**
     * Initializes the field serializer
     *
     * @param fieldSet the method to invoke in order to set the decoded value
     * into the object
     * @param fieldGet the method to invoke in order to get, from the object,
     * the value to be encoded
     */
    void configure(Method fieldSet, Method fieldGet);

    /**
     * Reads the object from the XML representation, converts it to a java
     * object, and sets it in the java bean
     *
     * @param bean the bean to set the value into
     * @param xmlElement the XML element containing the XML serialization
     * @throws org.theeuropeanlibrary.maia.common.ConverterException
     */
    void decode(Object bean, Element xmlElement) throws ConverterException;

    /**
     * Serializes a field from the bean into XML
     *
     * @param bean the object to get the field to serialize
     * @param xmlParentElement the parent XML element where the object should be
     * serialized
     * @param elementName the name of the XML element
     * @return the created XML element containing the serialized field, or null
     * if field has a null value
     * @throws org.theeuropeanlibrary.maia.common.ConverterException
     */
    Element encode(Object bean, Element xmlParentElement, String elementName) throws ConverterException;
}
