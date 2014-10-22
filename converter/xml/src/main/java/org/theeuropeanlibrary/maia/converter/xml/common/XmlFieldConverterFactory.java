package org.theeuropeanlibrary.maia.converter.xml.common;

import java.util.Set;
import org.theeuropeanlibrary.maia.common.TKey;

/**
 * A factory that manages the implementations of converters for a set of classes
 *
 * @author Nuno Freire (nuno.freire@theeuropeanlibrary.org)
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public interface XmlFieldConverterFactory {

    /**
     * @return name space of this xml
     */
//    String getNamespace();
    
    /**
     * Get all classes for which the {@link XmlFieldConverterFactory} has a
     * converter
     *
     * @return classes supported for conversion
     */
    Set<Class<?>> getSupportedClasses();

    /**
     * Get all classes for which the {@link XmlFieldConverterFactory} has a base
     * type encoder
     *
     * @return classes supported
     */
    Set<Class<?>> getSupportedBaseTypeEncoders();

    /**
     * Gets a {@link XmlFieldConverter} for a given Class
     *
     * @param <T> generic type of class
     * @param convertedClass
     * @return Converter
     */
    <T> XmlFieldConverter<T> getConverter(Class<T> convertedClass);

    /**
     * Gets a {@link XmlFieldConverter} for a given primitive Class
     *
     * @param <T> generic type of class
     * @param encodedClass
     * @return encoder
     */
    <T> XmlFieldConverter<T> getBaseTypeConverter(Class<T> encodedClass);

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
