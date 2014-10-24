/* BaseConverterFactory.java - created on 7 de Jun de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.binary.factory;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.theeuropeanlibrary.central.convert.DoubleEncoder;
import org.theeuropeanlibrary.central.convert.ShortEncoder;
import org.theeuropeanlibrary.maia.common.converter.Converter;
import org.theeuropeanlibrary.maia.common.FieldId;
import org.theeuropeanlibrary.maia.common.TKey;
import org.theeuropeanlibrary.maia.converter.binary.basetype.BooleanEncoder;
import org.theeuropeanlibrary.maia.converter.binary.basetype.DateEncoder;
import org.theeuropeanlibrary.maia.converter.binary.basetype.FloatEncoder;
import org.theeuropeanlibrary.maia.converter.binary.basetype.IntegerEncoder;
import org.theeuropeanlibrary.maia.converter.binary.basetype.LongEncoder;
import org.theeuropeanlibrary.maia.converter.binary.basetype.StringEncoder;
import org.theeuropeanlibrary.maia.converter.binary.basetype.BaseTypeEncoder;
import org.theeuropeanlibrary.maia.converter.binary.complex.AnnotationBasedByteConverter;

/**
 * This class defines generic functionality to get specific converters for given
 * and base types.
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @since 7 de Jun de 2011
 */
@SuppressWarnings({"rawtypes", "cast", "unchecked"})
public class BaseBinaryConverterFactory implements BinaryConverterFactory {

    protected final Map<Class<?>, Converter> converters = new HashMap<>();
    protected final Map<Class<?>, BaseTypeEncoder> baseTypeEncoders = new HashMap<>();

    protected final Map<TKey<?, ?>, Integer> tkeyFieldId = new HashMap<>();
    protected final Map<Integer, TKey<?, ?>> fieldIdTkey = new HashMap<>();

    protected final Map<String, String> enumFieldId = new HashMap<>();
    protected final Map<String, String> fieldIdEnum = new HashMap<>();

    public BaseBinaryConverterFactory() {
        setupBaseTypeConverters();
    }

    public BaseBinaryConverterFactory(Class<?> registry) {
        this();
        setupKeys(registry);
        setupQualifiers(registry);
        setupConverters();
    }

    private void setupKeys(Class<?> keyRegistry) {
        for (Field f : keyRegistry.getDeclaredFields()) {
            FieldId ann = f.getAnnotation(FieldId.class);
            if (ann != null) {
                Object key;
                try {
                    key = f.get(TKey.class);
                } catch (IllegalAccessException | IllegalArgumentException ex) {
                    key = null;
                }
                if (key == null || !(key instanceof TKey)) {
                    continue;
                }
                if (fieldIdTkey.containsKey(ann.value())) {
                    throw new RuntimeException(
                            "Duplicate field id '" + ann.value() + "' is not allowed!");
                }

                tkeyFieldId.put((TKey<?, ?>) key, ann.value());
                fieldIdTkey.put(ann.value(), (TKey<?, ?>) key);
            }
        }
    }

    private void setupQualifiers(Class<?> qualifierRegistry) {
        for (Field f : qualifierRegistry.getDeclaredFields()) {
            FieldId fann = f.getAnnotation(FieldId.class);
            if (fann == null) {
                continue;
            }
            Object key;
            try {
                key = f.get(TKey.class);
            } catch (IllegalAccessException | IllegalArgumentException ex) {
                key = null;
            }
            if (key == null || (key instanceof TKey)) {
                continue;
            }

            Class<?> qualifierType;
            try {
                qualifierType = (Class<?>) f.get(Enum.class);
            } catch (IllegalAccessException | IllegalArgumentException e) {
                throw new RuntimeException("Field '" + f + "' cannot be accessed!", e);
// continue;
            }

            for (Field g : qualifierType.getDeclaredFields()) {
                FieldId eann = g.getAnnotation(FieldId.class);

                if (g.getName().contains("ENUM$VALUES")) {
                    continue;
                }

                String none = qualifierType.getName() + "@" + g.getName();

                if (eann != null) {
                    String second = f.getDeclaringClass().getName() + "@" + eann.value();

                    if (fieldIdEnum.containsKey(second)) {
                        throw new RuntimeException(
                                "Duplicate field id '" + second + "' is not allowed!");
                    }

                    try {
                        fieldIdEnum.put(second, none);
                        enumFieldId.put(none, second);
                    } catch (Exception e) {
                        throw new RuntimeException("Field '" + f + "' cannot be accessed!", e);
                    }
                }

                String first = fann.value() + "@" + g.getName();

                if (fieldIdEnum.containsKey(first)) {
                    throw new RuntimeException(
                            "Duplicate field id '" + first + "' is not allowed!");
                }

                try {
                    fieldIdEnum.put(first, none);
                    enumFieldId.put(none, first);
                } catch (Exception e) {
                    throw new RuntimeException("Field '" + f + "' cannot be accessed!", e);
                }

                if (eann != null) {
                    String full = fann.value() + "@" + eann.value();

                    if (fieldIdEnum.containsKey(full)) {
                        throw new RuntimeException(
                                "Duplicate field id '" + full + "' is not allowed!");
                    }

                    try {
                        fieldIdEnum.put(full, none);
                        enumFieldId.put(none, full);
                    } catch (Exception e) {
                        throw new RuntimeException("Field '" + f + "' cannot be accessed!", e);
                    }
                }
            }
        }
    }

    private void setupBaseTypeConverters() {
        baseTypeEncoders.put(String.class, new StringEncoder());
        baseTypeEncoders.put(Boolean.class, new BooleanEncoder());
        baseTypeEncoders.put(Date.class, new DateEncoder());
        baseTypeEncoders.put(Integer.class, new IntegerEncoder());
        baseTypeEncoders.put(Long.class, new LongEncoder());
        baseTypeEncoders.put(Short.class, new ShortEncoder());
        baseTypeEncoders.put(Double.class, new DoubleEncoder());
        baseTypeEncoders.put(Float.class, new FloatEncoder());
    }

    private void setupConverters() {
        for (TKey<?, ?> key : tkeyFieldId.keySet()) {
            if (!baseTypeEncoders.containsKey(key.getType())) {
                converters.put(key.getType(), new AnnotationBasedByteConverter(key.getType()));
            }
        }
    }

    @Override
    public Set<Class<?>> getSupportedClasses() {
        return converters.keySet();
    }

    @Override
    public Set<Class<?>> getSupportedBaseTypeEncoders() {
        return baseTypeEncoders.keySet();
    }

    @Override
    public <T> BaseTypeEncoder<T> getBaseTypeEncoder(Class<T> encodedClass) {
        return baseTypeEncoders.get(encodedClass);
    }

    @Override
    public <T> Converter<byte[], T> getConverter(Class<T> convertedClass) {
        return converters.get(convertedClass);
    }

    @Override
    public <NS, T> Integer getEncodedKey(TKey<NS, T> tKey) {
        return tkeyFieldId.get(tKey);
    }

    @Override
    public TKey<?, ?> getDecodedKey(Integer fieldId) {
        return fieldIdTkey.get(fieldId);
    }

    @Override
    public String getEncodedQualifier(String qualifier) {
        return enumFieldId.get(qualifier);
    }

    @Override
    public String getDecodedQualifier(String fieldId) {
        return fieldIdEnum.get(fieldId);
    }
}
