package org.theeuropeanlibrary.maia.converter.xml.factory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.theeuropeanlibrary.maia.common.TKey;
import org.theeuropeanlibrary.maia.common.registry.EntityRegistry;
import org.theeuropeanlibrary.maia.converter.xml.base.BaseTypeEncoderBasedXmlSerializer;
import org.theeuropeanlibrary.maia.converter.xml.basetype.BooleanEncoder;
import org.theeuropeanlibrary.maia.converter.xml.basetype.DateEncoder;
import org.theeuropeanlibrary.maia.converter.xml.basetype.DoubleEncoder;
import org.theeuropeanlibrary.maia.converter.xml.basetype.FloatEncoder;
import org.theeuropeanlibrary.maia.converter.xml.basetype.IntegerEncoder;
import org.theeuropeanlibrary.maia.converter.xml.basetype.LongEncoder;
import org.theeuropeanlibrary.maia.converter.xml.basetype.ShortEncoder;
import org.theeuropeanlibrary.maia.converter.xml.basetype.StringEncoder;
import org.theeuropeanlibrary.maia.converter.xml.common.XmlFieldConverter;
import org.theeuropeanlibrary.maia.converter.xml.common.XmlFieldConverterFactory;

/**
 * This class implements a basic xml converter. It uses the name of the TKey and
 * the simple name of the qualifiers to represent element names and attribute
 * names.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 20.10.2014
 */
public class BaseXmlFieldConverterFactory implements XmlFieldConverterFactory {

    protected final Map<Class<?>, XmlFieldConverter> converters = new HashMap<>();

    protected final Map<Class<?>, XmlFieldConverter> baseTypeConverters = new HashMap<>();

    protected final Map<String, TKey<?, ?>> elementNames = new HashMap<>();

    protected final Map<String, Class<? extends Enum<?>>> attributeNames = new HashMap<>();

    public BaseXmlFieldConverterFactory(EntityRegistry registry) {
        baseTypeConverters.put(Boolean.class, new BaseTypeEncoderBasedXmlSerializer<Boolean>(
                new BooleanEncoder()));
        baseTypeConverters.put(Date.class, new BaseTypeEncoderBasedXmlSerializer<Date>(
                new DateEncoder()));
        baseTypeConverters.put(Double.class, new BaseTypeEncoderBasedXmlSerializer<Double>(
                new DoubleEncoder()));
        baseTypeConverters.put(Float.class, new BaseTypeEncoderBasedXmlSerializer<Float>(
                new FloatEncoder()));
        baseTypeConverters.put(Integer.class, new BaseTypeEncoderBasedXmlSerializer<Integer>(
                new IntegerEncoder()));
        baseTypeConverters.put(Long.class, new BaseTypeEncoderBasedXmlSerializer<Long>(
                new LongEncoder()));
        baseTypeConverters.put(Short.class, new BaseTypeEncoderBasedXmlSerializer<Short>(
                new ShortEncoder()));
        baseTypeConverters.put(String.class, new BaseTypeEncoderBasedXmlSerializer<String>(
                new StringEncoder()));

        Set<TKey<?, ?>> keys = registry.getAvailableKeys();
        for (TKey<?, ?> key : keys) {
            elementNames.put(key.getName(), key);
        }

        Set<Class<? extends Enum<?>>> qualifiers = registry.getAvailableQualifiers();
        for (Class<? extends Enum<?>> qualifier : qualifiers) {
            attributeNames.put(qualifier.getSimpleName(), qualifier);
        }
    }

    @Override
    public Set<Class<?>> getSupportedClasses() {
        return converters.keySet();
    }

    @Override
    public Set<Class<?>> getSupportedBaseTypeEncoders() {
        return baseTypeConverters.keySet();
    }

    @Override
    public <T> XmlFieldConverter<T> getConverter(Class<T> convertedClass) {
        return converters.get(convertedClass);
    }

    @Override
    public <T> XmlFieldConverter<T> getBaseTypeConverter(Class<T> encodedClass) {
        return baseTypeConverters.get(encodedClass);
    }

    @Override
    public <NS, T> String getElementName(TKey<NS, T> tKey) {
        return tKey.getName();
    }

    @Override
    public TKey<?, ?> getKey(String elementName) {
        return elementNames.get(elementName);
    }

    @Override
    public String getAttributeName(Class<? extends Enum<?>> qualifier) {
        return qualifier.getSimpleName();
    }

    @Override
    public Class<? extends Enum<?>> getQualifier(String attributeName) {
        return attributeNames.get(attributeName);
    }

}
