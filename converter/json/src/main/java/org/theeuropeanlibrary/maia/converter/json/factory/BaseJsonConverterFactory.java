package org.theeuropeanlibrary.maia.converter.json.factory;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.theeuropeanlibrary.maia.common.TKey;
import org.theeuropeanlibrary.maia.common.registry.EntityRegistry;
import org.theeuropeanlibrary.maia.converter.json.basetype.StringDeserializer;
import org.theeuropeanlibrary.maia.converter.json.basetype.StringSerializer;

/**
 * This class implements a basic json converter. It uses the name of the TKey
 * and the simple name of the qualifiers to represent element names and
 * attribute names.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 22.10.2014
 */
public class BaseJsonConverterFactory implements JsonConverterFactory {

    protected final Map<Class<?>, JsonSerializer> serializers = new HashMap<>();
    protected final Map<Class<?>, JsonDeserializer> deserializers = new HashMap<>();

    protected final Map<Class<?>, JsonSerializer> baseTypeSerializers = new HashMap<>();
    protected final Map<Class<?>, JsonDeserializer> baseTypeDeserializers = new HashMap<>();

    protected final Map<String, TKey<?, ?>> elementNames = new HashMap<>();

    protected final Map<String, Class<? extends Enum<?>>> attributeNames = new HashMap<>();

    public BaseJsonConverterFactory(EntityRegistry registry) {
        baseTypeSerializers.put(String.class, new StringSerializer());
        baseTypeDeserializers.put(String.class, new StringDeserializer());

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
        return serializers.keySet();
    }

    @Override
    public Set<Class<?>> getSupportedBaseTypeEncoders() {
        return baseTypeSerializers.keySet();
    }

    @Override
    public <T> JsonSerializer<T> getSerializer(Class<T> convertedClass) {
        return serializers.get(convertedClass);
    }

    @Override
    public <T> JsonSerializer<T> getBaseTypeSerializer(Class<T> encodedClass) {
        return baseTypeSerializers.get(encodedClass);
    }

    @Override
    public <T> JsonDeserializer<T> getDeserializer(Class<T> convertedClass) {
        return deserializers.get(convertedClass);
    }

    @Override
    public <T> JsonDeserializer<T> getBaseTypeDeserializer(Class<T> encodedClass) {
        return baseTypeDeserializers.get(encodedClass);
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
