package org.theeuropeanlibrary.maia.converter.json.basetype;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;

/**
 * A <code>BaseTypeEncoder</code> for <code>Field</code>
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 22.10.2014
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class BaseTypeJsonFactory {

    public static JsonSerializer newFieldSerializerFor(Class<?> type) {
        JsonSerializer encoder = null;
        if (type.equals(String.class)) {
            encoder = new StringSerializer();
//        }
//        else if (type.equals(Integer.class) || type.equals(int.class)) {
//            encoder = new IntegerEncoder();
//        } else if (type.equals(Long.class) || type.equals(long.class)) {
//            encoder = new LongEncoder();
//        } else if (type.equals(Float.class) || type.equals(float.class)) {
//            encoder = new FloatEncoder();
//        } else if (type.equals(Date.class)) {
//            encoder = new DateEncoder();
//        } else if (type.equals(Double.class) || type.equals(double.class)) {
//            encoder = new DoubleEncoder();
//        } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
//            encoder = new BooleanEncoder();
        } else if (type.isEnum() || type.equals(Enum.class)) {
            encoder = new EnumSerializer((Class<? extends Enum>)type);
        }
        if (encoder == null) {
            throw new IllegalArgumentException(
                    "Class contains fields not supported for serialization (and no converter was provided): "
                    + type.getName());
        }
        return encoder;
    }

    public static JsonDeserializer newFieldDeserializerFor(Class<?> type) {
        JsonDeserializer encoder = null;
        if (type.equals(String.class)) {
            encoder = new StringDeserializer();
//        }
//        else if (type.equals(Integer.class) || type.equals(int.class)) {
//            encoder = new IntegerEncoder();
//        } else if (type.equals(Long.class) || type.equals(long.class)) {
//            encoder = new LongEncoder();
//        } else if (type.equals(Float.class) || type.equals(float.class)) {
//            encoder = new FloatEncoder();
//        } else if (type.equals(Date.class)) {
//            encoder = new DateEncoder();
//        } else if (type.equals(Double.class) || type.equals(double.class)) {
//            encoder = new DoubleEncoder();
//        } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
//            encoder = new BooleanEncoder();
        } else if (type.isEnum() || type.equals(Enum.class)) {
            encoder = new EnumDeserializer((Class<? extends Enum>)type);
        }
        if (encoder == null) {
            throw new IllegalArgumentException(
                    "Class contains fields not supported for serialization (and no converter was provided): "
                    + type.getName());
        }
        return encoder;
    }
}
