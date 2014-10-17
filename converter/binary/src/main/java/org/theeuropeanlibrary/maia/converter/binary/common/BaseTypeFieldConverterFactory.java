package org.theeuropeanlibrary.maia.converter.binary.common;

import java.util.Date;
import org.theeuropeanlibrary.central.convert.DoubleEncoder;
import org.theeuropeanlibrary.maia.common.Converter;
import org.theeuropeanlibrary.maia.converter.binary.basetype.BinaryEncoder;
import org.theeuropeanlibrary.maia.converter.binary.basetype.BooleanEncoder;
import org.theeuropeanlibrary.maia.converter.binary.basetype.DateEncoder;
import org.theeuropeanlibrary.maia.converter.binary.basetype.EnumEncoder;
import org.theeuropeanlibrary.maia.converter.binary.basetype.FloatEncoder;
import org.theeuropeanlibrary.maia.converter.binary.basetype.IntegerEncoder;
import org.theeuropeanlibrary.maia.converter.binary.basetype.LongEncoder;
import org.theeuropeanlibrary.maia.converter.binary.basetype.StringEncoder;

/**
 * Factory to retrieve base type converters.F
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @since 17.10.2014
 */
public class BaseTypeFieldConverterFactory {

    /**
     * Creates a <code>FieldConverterInterface</code> implementation for the
     * given type
     *
     * @param type
     * @return a <code>FieldConverterInterface</code> implementation for the
     * given type
     */
    @SuppressWarnings("unchecked")
    public static BaseTypeConverter newInstanceFor(Class<?> type) {
        return new BaseTypeConverter(newFieldEncoderFor(type));
    }

    /**
     * @param type type for the encoder
     * @return an encoder
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static BaseTypeEncoder newFieldEncoderFor(Class<?> type) {
        BaseTypeEncoder encoder = null;
        if (type.equals(String.class)) {
            encoder = new StringEncoder();
        } else if (type.equals(Integer.class) || type.equals(int.class)) {
            encoder = new IntegerEncoder();
        } else if (type.equals(Long.class) || type.equals(long.class)) {
            encoder = new LongEncoder();
        } else if (type.equals(Float.class) || type.equals(float.class)) {
            encoder = new FloatEncoder();
        } else if (type.equals(Date.class)) {
            encoder = new DateEncoder();
        } else if (type.equals(Double.class) || type.equals(double.class)) {
            encoder = new DoubleEncoder();
        } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
            encoder = new BooleanEncoder();
        } else if (type.isEnum() || type.equals(Enum.class)) {
            encoder = new EnumEncoder((Class<? extends Enum>) type);
        } else if (type.equals(byte[].class)) {
            encoder = new BinaryEncoder();
        }
        if (encoder == null) {
            throw new IllegalArgumentException(
                    "Class contains fields not supported for serialization (and no converter was provided): "
                    + type.getName());
        }
        return encoder;
    }

    /**
     * Creates a new Converter for a base type
     *
     * @param type the converted base type
     * @return converter
     */
    @SuppressWarnings("rawtypes")
    public static Converter newConverterFor(Class type) {
        return new BaseTypeEncoderBasedConverter(
                newFieldEncoderFor(type));
    }

}
