/* ConverterFactory.java - created on 4 de Nov de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.binary.factory;

import java.util.Set;
import org.theeuropeanlibrary.maia.common.converter.Converter;
import org.theeuropeanlibrary.maia.common.TKey;
import org.theeuropeanlibrary.maia.converter.binary.basetype.BaseTypeEncoder;

/**
 * A factory that manages the implementations of converters for a set of classes
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @date 4 de Nov de 2011
 */
public interface BinaryConverterFactory {

    /**
     * Get all classes for which the {@link BinaryConverterFactory} has a
     * converter
     *
     * @return classes supported for conversion
     */
    Set<Class<?>> getSupportedClasses();

    /**
     * Get all classes for which the {@link BinaryConverterFactory} has a base
     * type encoder
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
    <NS, T> Integer getEncodedKey(TKey<NS, T> tKey);

    /**
     * Gets a TKey for a given field ID
     *
     * @param <NS> name space
     * @param <T> generic type
     * @param encodedKey
     * @return key
     */
    <NS, T> TKey<NS, T> getDecodedKey(Integer encodedKey);

    /**
     * Gets a field ID for a given TKey
     *
     * @param qualifier
     * @return integer
     */
    String getEncodedQualifier(String qualifier);

    /**
     * Gets a TKey for a given field ID
     *
     * @param encodedQualifier
     * @return key
     */
    String getDecodedQualifier(String encodedQualifier);
}
