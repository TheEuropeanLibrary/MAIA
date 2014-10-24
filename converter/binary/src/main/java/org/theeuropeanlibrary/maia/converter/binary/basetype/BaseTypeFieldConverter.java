/* FieldBufferSetter.java - created on 5 de Abr de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.binary.basetype;

import java.lang.reflect.Field;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import org.theeuropeanlibrary.maia.converter.binary.complex.FieldConverter;

/**
 * /** A <code>FieldConverterInterface</code> implementation for base types
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @since 06.04.2011
 */
public class BaseTypeFieldConverter implements FieldConverter {

    private final BaseTypeEncoder<Object> encoder;
    private Field field;
    private int id;

    /**
     * Creates a new instance of this class.
     *
     * @param encoder
     */
    public BaseTypeFieldConverter(BaseTypeEncoder<Object> encoder) {
        this.encoder = encoder;
    }

    @Override
    public void configure(Field field, int fieldId) {
        this.field = field;
        this.id = fieldId;
    }

    @Override
    public void decode(Object bean, CodedInputStream input) {
        try {
            field.set(bean, encoder.decode(input));
        } catch (Exception e) {
            throw new RuntimeException("Exception during serialization", e);
        }
    }

    @Override
    public void encode(Object bean, CodedOutputStream output) {
        try {
            Object value = field.get(bean);
            if (value != null) {
                encoder.encode(id, value, output);
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception during serialization", e);
        }
    }

}
