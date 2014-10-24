package org.theeuropeanlibrary.maia.converter.binary.complex;

import org.theeuropeanlibrary.maia.converter.binary.factory.BaseTypeFieldConverterFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.WireFormat;
import java.util.Collection;
import java.util.List;
import org.theeuropeanlibrary.central.convert.CollectionFieldConverter;
import org.theeuropeanlibrary.maia.common.converter.Converter;
import org.theeuropeanlibrary.maia.common.FieldId;
import org.theeuropeanlibrary.maia.common.converter.ConverterException;

/**
 * A <code>Converter</code> that converts objects according to annotations added
 * to the serializable fields on the converted classes. The field ids are
 * specified by the <code>FieldId</code> annotations. For inner Objects (besides
 * base types), converters are specified on the constructor.
 *
 * @param <T> The Class that this Converter converts
 * @author Nuno Freire (nuno.freire@theeuropeanlibrary.org)
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 06.04.2011
 */
public class AnnotationBasedByteConverter<T> implements Converter<byte[], T> {

    private final Class<T> converteClass;
    private final List<FieldConverter> idIndexedFieldArray;

    /**
     * Creates a new instance of this class.
     *
     * @param converteClass converted class
     */
    public AnnotationBasedByteConverter(Class<T> converteClass) {
        this(converteClass, null);
    }

    /**
     * Creates a new instance of this class.
     *
     * @param converteClass converted class
     * @param customEncoders a map specifying converters to be used for specific
     * fields containing complex Objects
     */
    public AnnotationBasedByteConverter(Class<T> converteClass,
            Map<Integer, FieldConverter> customEncoders) {
        this.converteClass = converteClass;

        HashMap<Integer, FieldConverter> idToFieldMap = new HashMap<>();

        int maxFieldId = initFieldsFromClass(converteClass, customEncoders,
                idToFieldMap);

        idIndexedFieldArray = new ArrayList<>(maxFieldId + 1);
        for (int i = 0; i <= maxFieldId; i++) {
            idIndexedFieldArray.add(idToFieldMap.get(i));
        }
    }

    private int initFieldsFromClass(Class<?> initClass,
            Map<Integer, FieldConverter> customEncoders,
            HashMap<Integer, FieldConverter> idToFieldMap) {
        int maxFieldId = 0;

        if (!initClass.getSuperclass().equals(Object.class)) {
            maxFieldId = initFieldsFromClass(initClass.getSuperclass(), customEncoders, idToFieldMap);
        }

        for (Field f : initClass.getDeclaredFields()) {
            f.setAccessible(true);
            FieldId ann = f.getAnnotation(FieldId.class);
            if (ann != null) {
                FieldConverter fldConv = null;
                maxFieldId = Math.max(maxFieldId, ann.value());
                if (customEncoders != null) {
                    fldConv = customEncoders.get(ann.value());
                }
                if (fldConv == null) {
                    fldConv = BaseTypeFieldConverterFactory.newInstanceFor(f.getType());
                }
                if (fldConv == null) {
                    if (Collection.class.isAssignableFrom(f.getClass())) {
                        fldConv = new CollectionFieldConverter(new AnnotationBasedByteConverter(f.getClass()));
                    } else {
                        fldConv = new DynamicFieldConverter(new AnnotationBasedByteConverter(f.getClass()));
                    }
                }
                fldConv.configure(f, ann.value());
                idToFieldMap.put(ann.value(), fldConv);
            }
        }

        return maxFieldId;
    }

    @Override
    public T decode(byte[] data) throws ConverterException {
        ByteArrayInputStream bin = new ByteArrayInputStream(data);
        try {
            Constructor<T> defaultConstructor = converteClass.getDeclaredConstructor();
            defaultConstructor.setAccessible(true);
            T obj = defaultConstructor.newInstance();
            CodedInputStream input = CodedInputStream.newInstance(bin);
            int tag;
            while ((tag = input.readTag()) != 0) {
                tag = WireFormat.getTagFieldNumber(tag);
                FieldConverter fieldBufferSetter = idIndexedFieldArray.get(tag);
                fieldBufferSetter.decode(obj, input);
            }
            return obj;
        } catch (InstantiationException e) {
            throw new ConverterException("Class default contructor thrown exception "
                    + converteClass.getName(), e);
        } catch (IllegalAccessException e) {
            throw new ConverterException("Class does not provide an accesible default contructor "
                    + converteClass.getName(), e);
        } catch (IOException e) {
            throw new ConverterException("Could not read from byte array! " + converteClass.getName(), e);
        } catch (SecurityException e) {
            throw new ConverterException("Could not access constructor " + converteClass.getName(), e);
        } catch (NoSuchMethodException e) {
            throw new ConverterException("Default constructor does not exist in class "
                    + converteClass.getName(), e);
        } catch (InvocationTargetException e) {
            throw new ConverterException("Constructor thrown exception " + converteClass.getName(), e);
        } finally {
            try {
                bin.close();
            } catch (IOException e) {
                throw new ConverterException("Could not close input stream!", e);
            }
        }
    }

    @Override
    public byte[] encode(T data) throws ConverterException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        CodedOutputStream output = CodedOutputStream.newInstance(bout);
        try {
            for (FieldConverter conv : idIndexedFieldArray) {
                if (conv != null) {
                    conv.encode(data, output);
                }
            }
            output.flush();
        } catch (IOException e) {
            throw new ConverterException("Could not write ProviderBean '" + data.toString()
                    + "' to byte array!", e);
        } finally {
            try {
                bout.close();
            } catch (IOException e) {
                throw new ConverterException("Could not close output stream!", e);
            }
        }
        return bout.toByteArray();
    }

    @Override
    public Class<T> getDecodeType() {
        return converteClass;
    }

    @Override
    public Class<byte[]> getEncodeType() {
        return byte[].class;
    }
}
