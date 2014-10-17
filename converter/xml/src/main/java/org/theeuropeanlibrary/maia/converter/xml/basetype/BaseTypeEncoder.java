/* FieldEncoder.java - created on 6 de Abr de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.xml.basetype;


/**
 * An interface for abstracting the protobuf specific code for encoding and decoding. Used mainly by
 * the <code>FieldConverterInterface</code> implementations Also supports String encoding/decoding
 * for use in XML serialization
 * 
 * @author Nuno Freire (nfreire@gmail.com)
 * @param <T>
 *            The supported class for encoding/decoding
 * @date 6 de Abr de 2011
 */
public interface BaseTypeEncoder<T> {

    /**
     * Decode a value from a String
     * 
     * @param value
     * @return the decoded value
     * @throws Exception
     */
    public T decodeFromString(String value) throws Exception;

    /**
     * Encode a value into a String
     * 
     * @param value
     * @return the String representation of the value
     * @throws Exception
     */
    public String encodeToString(T value) throws Exception;

//    /**
//     * @param parentEl
//     */
//    public void toXmlSchema(Element parentEl);
}
