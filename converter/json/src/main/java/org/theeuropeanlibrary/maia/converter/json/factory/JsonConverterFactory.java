package org.theeuropeanlibrary.maia.converter.json.factory;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import java.util.Set;
import org.theeuropeanlibrary.maia.common.TKey;

/**
 * A factory that manages the implementations of converters for a set of classes
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 22.10.2014
 */
public interface JsonConverterFactory {

    /**
     * Get all classes for which the {@link JsonConverterFactory} has a
     * converter
     *
     * @return classes supported for conversion
     */
    Set<Class<?>> getSupportedClasses();

    /**
     * Get all classes for which the {@link JsonConverterFactory} has a
     * base type encoder
     *
     * @return classes supported
     */
    Set<Class<?>> getSupportedBaseTypeEncoders();

    /**
     * Gets a {@link JsonSerializer} for a given Class
     *
     * @param <T> generic type of class
     * @param convertedClass
     * @return Converter
     */
    <T> JsonSerializer<T> getSerializer(Class<T> convertedClass);

    /**
     * Gets a {@link JsonSerializer} for a given primitive Class
     *
     * @param <T> generic type of class
     * @param encodedClass
     * @return encoder
     */
    <T> JsonSerializer<T> getBaseTypeSerializer(Class<T> encodedClass);

    /**
     * Gets a {@link JsonDeserializer} for a given Class
     *
     * @param <T> generic type of class
     * @param convertedClass
     * @return Converter
     */
    <T> JsonDeserializer<T> getDeserializer(Class<T> convertedClass);

    /**
     * Gets a {@link JsonDeserializer} for a given primitive Class
     *
     * @param <T> generic type of class
     * @param encodedClass
     * @return encoder
     */
    <T> JsonDeserializer<T> getBaseTypeDeserializer(Class<T> encodedClass);

    /**
     * Gets a element name for a given TKey
     *
     * @param <NS> name space
     * @param <T> generic type
     * @param tKey
     * @return integer
     */
    <NS, T> String getElementName(TKey<NS, T> tKey);

    /**
     * Gets a TKey for a given element name
     *
     * @param elemName
     * @return key
     */
    TKey<?, ?> getKey(String elemName);

    /**
     * Gets a field ID for a given TKey
     *
     * @param qualifier
     * @return integer
     */
    String getAttributeName(Class<? extends Enum<?>> qualifier);

    /**
     * Gets a TKey for a given field ID
     *
     * @param attrName
     * @return key
     */
    Class<? extends Enum<?>> getQualifier(String attrName);
}
