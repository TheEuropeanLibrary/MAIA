package org.theeuropeanlibrary.maia.converter.binary.complex;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.WireFormat;
import org.theeuropeanlibrary.maia.common.converter.Converter;
import org.theeuropeanlibrary.maia.common.converter.ConverterException;

/**
 * A converter that checks at conversion time, the class of the object to be
 * converted and uses the appropriate converter.
 *
 * @author Nuno Freire (nuno.freire@theeuropeanlibrary.org)
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 06.04.2011
 */
public class MultiClassFieldConverter implements FieldConverter {

    private static final int CLASS_NAME = 1;
    private static final int OBJECT = 2;

    private final Map<Class, Converter> converters = new HashMap<>();

    private Field field;
    private int id;

    /**
     * Creates a new instance of this class.
     */
    public MultiClassFieldConverter() {
    }

    @Override
    public void configure(Field field, int fieldId) {
        this.field = field;
        this.id = fieldId;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes", "null"})
    public void decode(Object bean, CodedInputStream input) throws ConverterException {
        try {
            CodedInputStream inputItem = CodedInputStream.newInstance(input
                    .readBytes().toByteArray());

            Converter converter = null;
            int tag;
            while ((tag = inputItem.readTag()) != 0) {
                int fieldId = WireFormat.getTagFieldNumber(tag);
                switch (fieldId) {
                    case CLASS_NAME:
                        Class<?> classOfValue = Class.forName(inputItem
                                .readString());
                        converter = converters.get(classOfValue);
                        if (converter == null) {
                            throw new RuntimeException("No converter provided for "
                                    + classOfValue.getName());
                        }
                        break;
                    case OBJECT:
                        byte[] itemBytes = inputItem.readBytes().toByteArray();
                        field.set(bean, converter.decode(itemBytes));
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException | RuntimeException | IllegalAccessException e) {
            throw new ConverterException("Exception during serialization", e);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void encode(Object bean, CodedOutputStream output) throws ConverterException {
        try {
            Object obj = field.get(bean);
            if (obj != null) {
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                CodedOutputStream innerOutput = CodedOutputStream
                        .newInstance(bout);
                try {
                    Converter itemConverter = converters.get(obj.getClass());
                    if (itemConverter == null) {
                        throw new RuntimeException("No converter provided for "
                                + bean.getClass().getName());
                    }
                    byte[] bytes = (byte[]) itemConverter.encode(obj);
                    innerOutput.writeString(CLASS_NAME, obj.getClass()
                            .getName());
                    innerOutput.writeBytes(OBJECT, ByteString.copyFrom(bytes));
                    innerOutput.flush();
                    output.writeBytes(id,
                            ByteString.copyFrom(bout.toByteArray()));
                } catch (IOException | RuntimeException e) {
                    throw new ConverterException("Could not write '"
                            + field.getName() + "' to byte array!", e);
                } finally {
                    try {
                        bout.close();
                    } catch (IOException e) {
                        throw new RuntimeException(
                                "Could not close output stream!", e);
                    }
                }
            }
        } catch (IllegalAccessException | RuntimeException e) {
            throw new ConverterException("Exception during serialization", e);
        }
    }

    /**
     * Adds a supported Class for convertion, and the corresponding converter
     *
     * @param convertedClass
     * @param converter
     */
    @SuppressWarnings("rawtypes")
    public void add(Class convertedClass, Converter converter) {
        converters.put(convertedClass, converter);
    }
}
