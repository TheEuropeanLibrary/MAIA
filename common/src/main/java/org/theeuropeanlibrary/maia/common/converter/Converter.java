package org.theeuropeanlibrary.maia.common.converter;

/**
 * Base abstract class for converters. It provides abstract methods for encoding
 * and decoding of object typed by generics. Furthermore, encoding and decoding
 * to byte array is available for all sub classes.
 *
 * @param <E> the format in which the content is stored in the backend (Object,
 * String, byte[], ...)
 * @param <D> the source format in which the content comes from the front end
 * (MyContent, String, ...)
 *
 * @author Andreas Juffinger <andreas.juffinger@kb.nl>
 * @author Markus Muhr (markus.muhr@kb.nl)
 * @since Feb 18, 2011
 */
public interface Converter<E, D> {

    /**
     * @return encode data type
     */
    Class<E> getEncodeType();

    /**
     * @return decode data type
     */
    Class<D> getDecodeType();

    /**
     * @param data
     * @return the encoded data
     * @throws ConverterException
     */
    E encode(D data) throws ConverterException;

    /**
     * @param data
     * @return the decoded data
     * @throws ConverterException
     */
    D decode(E data) throws ConverterException;
}
