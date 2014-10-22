/* FieldBufferSetter.java - created on 5 de Abr de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.binary.annotation;

import java.lang.reflect.Field;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;

/**
 * Abstracts the interface for converting individual fields, used by the
 * <code>AnnotationBasedByteConverter</code> Implementations will support the
 * java primitive types, collections, and complex Objects.
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @since 06.04.2011
 */
public interface FieldConverter {

    /**
     * Initializes the field converter
     *
     * @param field to set the decoded value into the object
     * @param id the field id for the protobuf
     */
    void configure(Field field, int id);

    /**
     * Decodes a value of the field from the protobuf
     *
     * @param bean The bean where the value is to be set
     * @param input where to decode the value from
     */
    void decode(Object bean, CodedInputStream input);

    /**
     * Encodes the value of the field into the protobuf
     *
     * @param bean The bean where the value is to be obtained
     * @param output where to encode the value
     */
    void encode(Object bean, CodedOutputStream output);

}
