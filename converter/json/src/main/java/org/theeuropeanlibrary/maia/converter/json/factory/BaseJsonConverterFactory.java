package org.theeuropeanlibrary.maia.converter.json.factory;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.theeuropeanlibrary.maia.common.TKey;
import org.theeuropeanlibrary.maia.common.registry.EntityRegistry;
import org.theeuropeanlibrary.maia.converter.json.basetype.EnumDeserializer;
import org.theeuropeanlibrary.maia.converter.json.basetype.EnumSerializer;
import org.theeuropeanlibrary.maia.converter.json.basetype.StringDeserializer;
import org.theeuropeanlibrary.maia.converter.json.basetype.StringSerializer;
import org.theeuropeanlibrary.maia.converter.json.serializer.AnnotationBasedJsonDeserializer;
import org.theeuropeanlibrary.maia.converter.json.serializer.AnnotationBasedJsonSerializer;

/**
 * This class implements a basic json converter. It uses the name of the TKey
 * and the simple name of the qualifiers to represent element names and
 * attribute names.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 22.10.2014
 */
public class BaseJsonConverterFactory implements JsonConverterFactory {

    protected final EntityRegistry registry;

    protected final Map<Class<?>, JsonSerializer> serializers = new HashMap<>();
    protected final Map<Class<?>, JsonDeserializer> deserializers = new HashMap<>();

    protected final Map<Class<?>, JsonSerializer> baseTypeSerializers = new HashMap<>();
    protected final Map<Class<?>, JsonDeserializer> baseTypeDeserializers = new HashMap<>();

    protected final Map<String, TKey<?, ?>> elementNames = new HashMap<>();

    protected final Map<String, Class<? extends Enum<?>>> attributeNames = new HashMap<>();

    public BaseJsonConverterFactory(EntityRegistry registry) {
        this.registry = registry;
        setupBaseTypes();
        setupKeys();
        setupQualifiers();
    }

    private void setupBaseTypes() {
        baseTypeSerializers.put(String.class, new StringSerializer());
        baseTypeDeserializers.put(String.class, new StringDeserializer());
    }

    private void setupKeys() {
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
//                if (!baseTypeSerializers.containsKey(key.getType())) {
//                    serializers.put(key.getType(), new AnnotationBasedJsonSerializer(key.getType()));
//                    deserializers.put(key.getType(), new AnnotationBasedJsonDeserializer(key.getType()));
//                }
//            }
//        }

        Set<TKey<?, ?>> keys = registry.getAvailableKeys();
        for (TKey<?, ?> key : keys) {
            elementNames.put(key.getName(), key);
            if (!baseTypeSerializers.containsKey(key.getType())) {
                if (Enum.class.isAssignableFrom(key.getType())) {
                    baseTypeSerializers.put(key.getType(), new EnumSerializer((Class<? extends Enum>) key.getType()));
                    baseTypeDeserializers.put(key.getType(), new EnumDeserializer((Class<? extends Enum>) key.getType()));
                } else {
                    serializers.put(key.getType(), new AnnotationBasedJsonSerializer(key.getType()));
                    deserializers.put(key.getType(), new AnnotationBasedJsonDeserializer(key.getType()));
                }
            }
        }
    }

    private void setupQualifiers() {
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

    @Override
    public EntityRegistry getRegistry() {
        return registry;
    }

}
