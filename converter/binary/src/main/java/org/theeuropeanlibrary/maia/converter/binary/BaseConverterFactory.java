/* BaseConverterFactory.java - created on 7 de Jun de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.binary;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.theeuropeanlibrary.central.convert.DoubleEncoder;
import org.theeuropeanlibrary.central.convert.ShortEncoder;
import org.theeuropeanlibrary.maia.common.Converter;
import org.theeuropeanlibrary.maia.common.TKey;
import org.theeuropeanlibrary.maia.converter.binary.basetype.BooleanEncoder;
import org.theeuropeanlibrary.maia.converter.binary.basetype.DateEncoder;
import org.theeuropeanlibrary.maia.converter.binary.basetype.FloatEncoder;
import org.theeuropeanlibrary.maia.converter.binary.basetype.IntegerEncoder;
import org.theeuropeanlibrary.maia.converter.binary.basetype.LongEncoder;
import org.theeuropeanlibrary.maia.converter.binary.basetype.StringEncoder;
import org.theeuropeanlibrary.maia.converter.binary.common.BaseTypeEncoder;
import org.theeuropeanlibrary.maia.converter.binary.common.ConverterFactory;

/**
 * This class defines generic functionality to get specific converters for given
 * and base types.
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @since 7 de Jun de 2011
 */
@SuppressWarnings({"rawtypes", "cast", "unchecked"})
public class BaseConverterFactory implements ConverterFactory {

    protected final Map<Class<?>, Converter> converters = new HashMap<>();
    protected final Map<Class<?>, BaseTypeEncoder> baseTypeEncoders = new HashMap<>();

    protected final Map<TKey<?, ?>, Integer> tkeyFieldId = new HashMap<>();
    protected final Map<Integer, TKey<?, ?>> fieldIdTkey = new HashMap<>();

    protected final Map<String, String> enumFieldId = new HashMap<>();
    protected final Map<String, String> fieldIdEnum = new HashMap<>();

    public BaseConverterFactory() {
        baseTypeEncoders.put(String.class, new StringEncoder());
        baseTypeEncoders.put(Boolean.class, new BooleanEncoder());
        baseTypeEncoders.put(Date.class, new DateEncoder());
        baseTypeEncoders.put(Integer.class, new IntegerEncoder());
        baseTypeEncoders.put(Long.class, new LongEncoder());
        baseTypeEncoders.put(Short.class, new ShortEncoder());
        baseTypeEncoders.put(Double.class, new DoubleEncoder());
        baseTypeEncoders.put(Float.class, new FloatEncoder());
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
    public <NS, T> Integer getKeyId(TKey<NS, T> tKey) {
        return tkeyFieldId.get(tKey);
    }

    @Override
    public TKey<?, ?> getKey(Integer fieldId) {
        return fieldIdTkey.get(fieldId);
    }

    @Override
    public String getQualifierId(String qualifier) {
        return enumFieldId.get(qualifier);
    }

    @Override
    public String getQualifier(String fieldId) {
        return fieldIdEnum.get(fieldId);
    }
}
