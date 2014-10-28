package org.theeuropeanlibrary.maia.converter.xml.factory;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.theeuropeanlibrary.maia.common.FieldId;
import org.theeuropeanlibrary.maia.common.TKey;
import org.theeuropeanlibrary.maia.common.registry.EntityRegistry;
import org.theeuropeanlibrary.maia.converter.xml.serializer.BaseTypeEncoderBasedXmlSerializer;
import org.theeuropeanlibrary.maia.converter.xml.basetype.BooleanEncoder;
import org.theeuropeanlibrary.maia.converter.xml.basetype.DateEncoder;
import org.theeuropeanlibrary.maia.converter.xml.basetype.DoubleEncoder;
import org.theeuropeanlibrary.maia.converter.xml.basetype.FloatEncoder;
import org.theeuropeanlibrary.maia.converter.xml.basetype.IntegerEncoder;
import org.theeuropeanlibrary.maia.converter.xml.basetype.LongEncoder;
import org.theeuropeanlibrary.maia.converter.xml.basetype.ShortEncoder;
import org.theeuropeanlibrary.maia.converter.xml.basetype.StringEncoder;
import org.theeuropeanlibrary.maia.converter.xml.serializer.AnnotationBasedXmlConverter;
import org.theeuropeanlibrary.maia.converter.xml.serializer.XmlFieldConverter;

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
        setupBaseTypes();
        setupKeys(registry);
        setupQualifiers(registry);
    }

    private void setupBaseTypes() {
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
    }

    private void setupKeys(EntityRegistry registry) {
//        for (Field f : keyRegistry.getDeclaredFields()) {
//            FieldId ann = f.getAnnotation(FieldId.class);
//            if (ann != null) {
//                Object okey;
//                try {
//                    okey = f.get(TKey.class);
//                } catch (IllegalAccessException | IllegalArgumentException ex) {
//                    okey = null;
//                }
//                if (okey == null || !(okey instanceof TKey)) {
//                    continue;
//                }
//
//                TKey key = (TKey) okey;
//
//                elementNames.put(key.getName(), key);
//                if (!baseTypeConverters.containsKey(key.getType()) && !converters.containsKey(key.getType())) {
//                    converters.put(key.getType(), new AnnotationBasedXmlConverter(key.getType()));
//                }
//            }
//        }

        Set<TKey<?, ?>> keys = registry.getAvailableKeys();
        for (TKey<?, ?> key : keys) {
            elementNames.put(key.getName(), key);
            if (!baseTypeConverters.containsKey(key.getType()) && !converters.containsKey(key.getType())) {
                converters.put(key.getType(), new AnnotationBasedXmlConverter(key.getType()));
            }
        }
    }

    private void setupQualifiers(EntityRegistry registry) {
//        for (Field f : qualifierRegistry.getDeclaredFields()) {
//            FieldId fann = f.getAnnotation(FieldId.class);
//            if (fann == null) {
//                continue;
//            }
//            Object key;
//            try {
//                key = f.get(TKey.class);
//            } catch (IllegalAccessException | IllegalArgumentException ex) {
//                key = null;
//            }
//            if (key == null || (key instanceof TKey)) {
//                continue;
//            }
//
//            Class<? extends Enum<?>> qualifier;
//            try {
//                qualifier = (Class<? extends Enum<?>>) f.get(Enum.class);
//            } catch (IllegalAccessException | IllegalArgumentException e) {
//                throw new RuntimeException("Field '" + f + "' cannot be accessed!", e);
//// continue;
//            }
//
//            attributeNames.put(qualifier.getSimpleName(), qualifier);
//        }
        
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
