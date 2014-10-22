/* FieldEncoder.java - created on 6 de Abr de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.binary.basetype;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;

/**
 * An interface for abstracting the protobuf specific code for encoding and decoding. Used mainly by
 * the <code>FieldConverterInterface</code> implementations
 * 
 * @author Nuno Freire (nfreire@gmail.com)
 * @param <T>
 *            The supported class for encoding/decoding
 * @date 6 de Abr de 2011
 */
public interface BaseTypeEncoder<T> {
    /**
     * decode a value from the input
     * 
     * @param input
     *            a protobuf input stream
     * @return the decoded value
     * @throws Exception
     */
    T decode(CodedInputStream input) throws Exception;

    /**
     * Encode a value into the buffer
     * 
     * @param fieldId
     * @param value
     *            the value to encode
     * @param output
     *            the protobuf output stream
     * @throws Exception
     */
    void encode(int fieldId, T value, CodedOutputStream output) throws Exception;
}
