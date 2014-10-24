package org.theeuropeanlibrary.central.convert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import org.theeuropeanlibrary.maia.common.converter.Converter;
import org.theeuropeanlibrary.maia.common.converter.ConverterException;
import org.theeuropeanlibrary.maia.converter.binary.complex.FieldConverter;

/**
 * A converter that converts collections using a generic inner loop converter.
 *
 * @author Nuno Freire (nuno.freire@theeuropeanlibrary.org)
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 06.04.2011
 */
public class CollectionFieldConverter implements FieldConverter {

    private final Converter itemConverter;

    private Field field;
    private int id;

    /**
     * Creates a new instance of this class.
     *
     * @param itemConverter the converter for the items in the collection
     */
    @SuppressWarnings("rawtypes")
    public CollectionFieldConverter(Converter itemConverter) {
        this.itemConverter = itemConverter;
    }

    @Override
    public void configure(Field field, int fieldId) {
        this.field = field;
        this.id = fieldId;
    }

    @Override
    public void decode(Object bean, CodedInputStream input) throws ConverterException {
        try {
            Collection collection = (Collection) field.get(bean);
            CodedInputStream inputCollection = CodedInputStream.newInstance(input.readBytes().toByteArray());
            while ((inputCollection.readTag()) != 0) {
                byte[] itemBytes = inputCollection.readBytes().toByteArray();
                Object obj = itemConverter.decode(itemBytes);
                collection.add(obj);
            }
        } catch (IllegalArgumentException | IllegalAccessException | IOException e) {
            throw new ConverterException("Exception during serialization", e);
        }
    }

    @Override
    public void encode(Object bean, CodedOutputStream output) throws ConverterException {
        try {
            Collection collection = (Collection) field.get(bean);
            for (Object obj : collection) {
                if (obj != null) {
                    ByteArrayOutputStream bout = new ByteArrayOutputStream();
                    CodedOutputStream innerOutput = CodedOutputStream.newInstance(bout);
                    try {
                        byte[] bytes = (byte[]) itemConverter.encode(obj);
                        innerOutput.writeBytes(1, ByteString.copyFrom(bytes));
                        innerOutput.flush();
                        output.writeBytes(id, ByteString.copyFrom(bout.toByteArray()));
                    } catch (IOException e) {
                        throw new ConverterException("Could not write collection '"
                                + field.getName() + "' to byte array!", e);
                    } finally {
                        try {
                            bout.close();
                        } catch (IOException e) {
                            throw new RuntimeException("Could not close output stream!", e);
                        }
                    }
                }
            }
        } catch (IllegalAccessException | RuntimeException e) {
            throw new ConverterException("Exception during serialization", e);
        }
    }
}
