/* ConverterFactory.java - created on 4 de Nov de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.binary.common;

import java.util.Set;
import org.theeuropeanlibrary.maia.common.Converter;
import org.theeuropeanlibrary.maia.common.TKey;

/**
 * A factory that manages the implementations of converters for a set of classes
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @date 4 de Nov de 2011
 */
public interface ConverterFactory {

    /**
     * Get all classes for which the {@link ConverterFactory} has a converter
     *
     * @return classes supported for conversion
     */
    Set<Class<?>> getSupportedClasses();

    /**
     * Get all classes for which the {@link ConverterFactory} has a base type
     * encoder
     *
     * @return classes supported
     */
    Set<Class<?>> getSupportedBaseTypeEncoders();

    /**
     * Gets a {@link Converter} for a given Class
     *
     * @param <T> generic type of class
     * @param convertedClass
     * @return Converter
     */
    <T> Converter<byte[], T> getConverter(Class<T> convertedClass);

    /**
     * Gets a BaseTypeEncoder for a given Class
     *
     * @param <T> generic type of class
     * @param encodedClass
     * @return encoder
     */
    <T> BaseTypeEncoder<T> getBaseTypeEncoder(Class<T> encodedClass);

    /**
     * Gets a field ID for a given TKey
     *
     * @param <NS> name space
     * @param <T> generic type
     * @param tKey
     * @return integer
     */
    <NS, T> Integer getKeyId(TKey<NS, T> tKey);

    /**
     * Gets a TKey for a given field ID
     *
     * @param <NS> name space
     * @param <T> generic type
     * @param fieldId
     * @return key
     */
    <NS, T> TKey<NS, T> getKey(Integer fieldId);

    /**
     * Gets a field ID for a given TKey
     *
     * @param qualifier
     * @return integer
     */
    String getQualifierId(String qualifier);

    /**
     * Gets a TKey for a given field ID
     *
     * @param fieldId
     * @return key
     */
    String getQualifier(String fieldId);
}
