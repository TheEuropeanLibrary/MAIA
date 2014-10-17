package org.theeuropeanlibrary.maia.converter.binary;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;
import org.theeuropeanlibrary.maia.common.Converter;

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
public abstract class AbstractBinaryConverter<E, D> implements Converter<E, D> {

    /**
     * private logging variable
     */
    private final static Logger log = Logger.getLogger(AbstractBinaryConverter.class.getName());

    /**
     * @param data object that should be encoded in bytes
     * @return the byte array of the object (object stream to bytes)
     * @throws IOException
     */
    public byte[] encodeToByteArray(E data) throws IOException {
        log.warning("Fallback serialization of object to byte[]");

        if (data == null) {
            return new byte[0];
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(data);
        return bos.toByteArray();
    }

    /**
     * @param data byte array to be decoded to an object
     * @return the object read from the bytes (object stream from bytes)
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public D decodeFromByteArray(byte[] data) throws IOException {
        log.warning("Fallback deserialization from a objectstream");

        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(bis);
            Object obj = ois.readObject();
            return (D) obj;
        } catch (ClassNotFoundException e) {
            throw new IOException(e.getMessage());
        }
    }
}
