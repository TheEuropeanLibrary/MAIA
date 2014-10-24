package org.theeuropeanlibrary.maia.converter.binary;

import org.theeuropeanlibrary.maia.converter.binary.basetype.BaseTypeEncoder;
import org.theeuropeanlibrary.maia.converter.binary.factory.BinaryConverterFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.WireFormat;
import org.theeuropeanlibrary.maia.common.converter.Converter;
import org.theeuropeanlibrary.maia.common.converter.ConverterException;
import org.theeuropeanlibrary.maia.common.Entity.QualifiedRelation;
import org.theeuropeanlibrary.maia.common.Entity.QualifiedValue;
import org.theeuropeanlibrary.maia.common.TKey;
import org.theeuropeanlibrary.maia.common.AbstractEntity;

/**
 * Converts between entities and byte array.
 *
 * @param <T> generic type of entity
 * @author Andreas Juffinger <andreas.juffinger@kb.nl>
 * @author Markus Muhr (markus.muhr@kb.nl)
 * @author Nuno Freire <nfreire@gmail.com>
 * @since Feb 18, 2011
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractEntityBinaryConverter<T extends AbstractEntity> extends AbstractBinaryConverter<byte[], T> {

    private static final int ID = 1;
    private static final int FIELD = 2;
    private static final int RELATION = 3;

    private final BinaryConverterFactory converterFactory;

    public AbstractEntityBinaryConverter(BinaryConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public Class<byte[]> getEncodeType() {
        return byte[].class;
    }

    protected abstract T createEntity();

    @SuppressWarnings("unchecked")
    @Override
    public byte[] encode(T bean)  throws ConverterException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        CodedOutputStream output = CodedOutputStream.newInstance(bout);
        try {
            output.writeString(ID, (String) bean.getId());

            Map<QualifiedValue<?>, TKey<?, ?>> reverseLookup = new HashMap<>();

            Set<TKey<?, ?>> keys = bean.getAvailableKeys();
            if (keys != null && keys.size() > 0) {
                for (TKey<?, ?> key : keys) {
                    List<QualifiedValue<?>> values = bean.getQualifiedValues(key);
                    byte[] bytes = writeField(key, values);
                    ByteString b = ByteString.copyFrom(bytes);
                    output.writeBytes(FIELD, b);

                    for (QualifiedValue<?> value : values) {
                        reverseLookup.put(value, key);
                    }
                }
            }

            Set<QualifiedRelation<?, ?>> relations = bean.getAvailableRelations();
            for (QualifiedRelation<?, ?> relation : relations) {
                byte[] bytes = writeRelation(relation.getSource(), relation.getTarget(),
                        relation.getQualifiers(), reverseLookup);
                ByteString b = ByteString.copyFrom(bytes);
                output.writeBytes(RELATION, b);
            }

            output.flush();
        } catch (IOException e) {
            throw new ConverterException("Could not write entity '" + bean.getId()
                    + "' to byte array!", e);
        } finally {
            try {
                bout.close();
            } catch (IOException e) {
                throw new RuntimeException("Could not close output stream!", e);
            }
        }

        byte[] uncompressed = bout.toByteArray();
        byte[] compressed = compress(uncompressed);
        return compressed;
    }

    @Override
    public T decode(byte[] data) throws ConverterException {
        byte[] decompressedData;
        if (data[0] == 120) {
            decompressedData = decompress(data);
        } else {
            decompressedData = data;
        }

        Map<Integer, QualifiedValue<?>> idxLookup = new HashMap<>();
        T entity = createEntity();
        ByteArrayInputStream bin = new ByteArrayInputStream(decompressedData);
        CodedInputStream input = CodedInputStream.newInstance(bin);
        int tag;
        try {
            while ((tag = input.readTag()) != 0) {
                int field = WireFormat.getTagFieldNumber(tag);
                switch (field) {
                    case ID:
                        entity.setId(input.readString());
                        break;
                    case FIELD:
                        ByteString bf = input.readBytes();
                        readField(entity, bf.toByteArray(), idxLookup);
                        break;
                    case RELATION:
                        ByteString br = input.readBytes();
                        readRelation(entity, br.toByteArray(), idxLookup);
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            throw new ConverterException("Could not read entity from byte array!", e);
        } finally {
            try {
                bin.close();
            } catch (IOException e) {
                throw new RuntimeException("Could not close input stream!", e);
            }
        }
        return entity;
    }

    private byte[] compress(byte[] uncompressed) {
        Deflater compressor = new Deflater();
        compressor.setLevel(Deflater.BEST_COMPRESSION);

        compressor.setInput(uncompressed);
        compressor.finish();

        ByteArrayOutputStream bos = new ByteArrayOutputStream(uncompressed.length);

        byte[] buf = new byte[1024];
        while (!compressor.finished()) {
            int count = compressor.deflate(buf);
            bos.write(buf, 0, count);
        }
        try {
            bos.close();
        } catch (IOException e) {
            // ignore
        }

        byte[] compressed = bos.toByteArray();
        return compressed;
    }

    private byte[] decompress(byte[] data) {
        Inflater decompressor = new Inflater();
        decompressor.setInput(data);

        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);

        byte[] buf = new byte[1024];
        while (!decompressor.finished()) {
            try {
                int count = decompressor.inflate(buf);
                bos.write(buf, 0, count);
            } catch (DataFormatException e) {
            }
        }
        try {
            bos.close();
        } catch (IOException e) {
        }

        byte[] decompressedData = bos.toByteArray();
        return decompressedData;
    }

    private static final int FIELD_KEY = 1;
    private static final int FIELD_ENTRY = 2;

    private byte[] writeField(TKey<?, ?> key, List<QualifiedValue<?>> values) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        CodedOutputStream output = CodedOutputStream.newInstance(bout);
        try {
            Integer id = converterFactory.getEncodedKey(key);
            if (id != null) {
                output.writeString(FIELD_KEY, id.toString());
            } else {
                output.writeString(FIELD_KEY, key.toString());
            }

            for (QualifiedValue<?> value : values) {
                byte[] bytes = writeEntry(value);
                ByteString b = ByteString.copyFrom(bytes);
                output.writeBytes(FIELD_ENTRY, b);
            }
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException("Could not write key '" + key.getFullName()
                    + "' to byte array!", e);
        } finally {
            try {
                bout.close();
            } catch (IOException e) {
                throw new RuntimeException("Could not close output stream!", e);
            }
        }
        return bout.toByteArray();
    }

    @SuppressWarnings("unchecked")
    private <NS, T> void readField(AbstractEntity<Long> bean, byte[] data,
            Map<Integer, QualifiedValue<?>> idxLookup) {
        ByteArrayInputStream bin = new ByteArrayInputStream(data);
        CodedInputStream input = CodedInputStream.newInstance(bin);
        int tag;
        try {
            TKey<NS, T> key = null;
            List<byte[]> encodedValues = new ArrayList<>();
            while ((tag = input.readTag()) != 0) {
                int field = WireFormat.getTagFieldNumber(tag);
                switch (field) {
                    case FIELD_KEY:
                        if (key != null) {
                            throw new RuntimeException(
                                    "Only one key is allowed per field!");
                        }
                        String k = input.readString();
                        try {
                            int id = Integer.parseInt(k);
                            key = (TKey<NS, T>) converterFactory.getDecodedKey(id);
                        } catch (NumberFormatException t) {
                            key = (TKey<NS, T>) TKey.fromString(k);
                        }
                        break;
                    case FIELD_ENTRY:
                        ByteString b = input.readBytes();
                        encodedValues.add(b.toByteArray());
                        break;
                    default:
                        break;
                }
            }

            List<QualifiedValue<T>> values = new ArrayList<>(encodedValues.size());
            for (byte[] encodedValue : encodedValues) {
                readEntry(key, values, encodedValue);
            }
            if (key != null) {
                bean.setValue(key, values);
                for (QualifiedValue<T> value : values) {
                    idxLookup.put(value.getOrderIndex(), value);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read field from byte array!", e);
        } finally {
            try {
                bin.close();
            } catch (IOException e) {
                throw new RuntimeException("Could not close input stream!", e);
            }
        }
    }

    private static final int RELATION_ENTRY_SOURCE_IDX = 1;
    private static final int RELATION_ENTRY_TARGET_IDX = 2;
    private static final int RELATION_ENTRY_QUALIFIER = 3;

    private byte[] writeRelation(QualifiedValue<?> source, QualifiedValue<?> target,
            Set<Enum<?>> qualifiers, Map<QualifiedValue<?>, TKey<?, ?>> reverseLookup) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        CodedOutputStream output = CodedOutputStream.newInstance(bout);
        try {
            output.writeInt32(RELATION_ENTRY_SOURCE_IDX, source.getOrderIndex());
            output.writeInt32(RELATION_ENTRY_TARGET_IDX, target.getOrderIndex());
            for (Enum<?> qualifier : qualifiers) {
                String qualifierEncoded = qualifier.getClass().getName() + "@" + qualifier.name();
                output.writeString(RELATION_ENTRY_QUALIFIER, qualifierEncoded);
            }
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException("Could not write relation from '" + source
                    + "' to target '" + target.getOrderIndex()
                    + "' to byte array!", e);
        } finally {
            try {
                bout.close();
            } catch (IOException e) {
                throw new RuntimeException("Could not close output stream!", e);
            }
        }
        return bout.toByteArray();
    }

    @SuppressWarnings("unchecked")
    private void readRelation(AbstractEntity<Long> bean, byte[] data,
            Map<Integer, QualifiedValue<?>> idxLookup) {
        ByteArrayInputStream bin = new ByteArrayInputStream(data);
        CodedInputStream input = CodedInputStream.newInstance(bin);
        int tag;
        try {
            QualifiedValue<?> source = null;
            QualifiedValue<?> target = null;
            Set<Enum<?>> qualifiers = new HashSet<>();

            while ((tag = input.readTag()) != 0) {
                int field = WireFormat.getTagFieldNumber(tag);
                switch (field) {
                    case RELATION_ENTRY_SOURCE_IDX:
                        if (source != null) {
                            throw new RuntimeException(
                                    "Only one source is allowed per field!");
                        }
                        int sourceIdx = input.readInt32();
                        source = idxLookup.get(sourceIdx);
                        break;
                    case RELATION_ENTRY_TARGET_IDX:
                        if (target != null) {
                            throw new RuntimeException(
                                    "Only one target is allowed per field!");
                        }
                        int targetIdx = input.readInt32();
                        target = idxLookup.get(targetIdx);
                        break;
                    case RELATION_ENTRY_QUALIFIER:
                        String encoded = input.readString();
                        String[] split = encoded.split("@");
                        Class<? extends Enum> type;
                        try {
                            type = (Class<? extends Enum>) Class.forName(split[0]);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException("Could not convert encoded enum '" + encoded
                                    + "'!", e);
                        }
                        Enum<?> enumValue = Enum.valueOf(type, split[1]);
                        qualifiers.add(enumValue);
                        break;
                    default:
                        break;
                }
            }

            if (source != null && target != null) {
                bean.addRelation(source, target, qualifiers.toArray(new Enum<?>[qualifiers.size()]));
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read field from byte array!", e);
        } finally {
            try {
                bin.close();
            } catch (IOException e) {
                throw new RuntimeException("Could not close input stream!", e);
            }
        }
    }

    private static final int FIELD_ENTRY_ORDER = 1;
    private static final int FIELD_ENTRY_VALUE = 2;
    private static final int FIELD_ENTRY_QUALIFIER = 3;

    @SuppressWarnings("unchecked")
    private byte[] writeEntry(QualifiedValue<?> value) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        CodedOutputStream output = CodedOutputStream.newInstance(bout);
        try {
            output.writeInt32(FIELD_ENTRY_ORDER, value.getOrderIndex());
            Converter converter = converterFactory.getConverter(value.getValue().getClass());
            if (converter != null) {
                ByteString b = ByteString.copyFrom((byte[]) converter.encode(value.getValue()));
                output.writeBytes(FIELD_ENTRY_VALUE, b);
            } else {
                BaseTypeEncoder encoder = converterFactory.getBaseTypeEncoder(value.getValue().getClass());
                if (encoder != null) {
                    encoder.encode(FIELD_ENTRY_VALUE, value.getValue(), output);
                } else {
                    // No encoder or converter, the value is not persisted
                }
            }
            Set<Enum<?>> qualifiers = value.getQualifiers();
            for (Enum<?> qualifier : qualifiers) {
                String qualifierEncoded = qualifier.getClass().getName() + "@" + qualifier.name();

                String id = converterFactory.getEncodedQualifier(qualifierEncoded);
                if (id != null) {
                    qualifierEncoded = id;
                }

                output.writeString(FIELD_ENTRY_QUALIFIER, qualifierEncoded);
            }
            output.flush();
        } catch (Exception e) {
            throw new RuntimeException("Could not write qualified value to byte array!", e);
        } finally {
            try {
                bout.close();
            } catch (IOException e) {
                throw new RuntimeException("Could not close output stream!", e);
            }
        }
        return bout.toByteArray();
    }

    @SuppressWarnings("unchecked")
    private <NS, T> void readEntry(TKey<NS, T> key, List<QualifiedValue<T>> values, byte[] data) {
        ByteArrayInputStream bin = new ByteArrayInputStream(data);
        CodedInputStream input = CodedInputStream.newInstance(bin);
        int tag;
        try {
            int order = -1;
            T value = null;
            Set<Enum<?>> qualifiers = new HashSet<>();
            while ((tag = input.readTag()) != 0) {
                int field = WireFormat.getTagFieldNumber(tag);
                switch (field) {
                    case FIELD_ENTRY_ORDER:
                        order = input.readInt32();
                        break;
                    case FIELD_ENTRY_VALUE:
                        Converter converter = converterFactory.getConverter(key.getType());
                        if (converter != null) {
                            try {
                                ByteString b = input.readBytes();
                                value = (T) converter.decode(b.toByteArray());
                            } catch (IOException e) {
                                throw new ConverterException("Error decoding a "
                                        + key.getType().getName(), e);
                            }
                        } else {
                            BaseTypeEncoder encoder = converterFactory.getBaseTypeEncoder(key.getType());
                            if (encoder == null) {
                                throw new RuntimeException("Unsupported class for metadata record: "
                                        + key.getType().getName());
                            }
                            value = (T) encoder.decode(input);
                        }
                        break;
                    case FIELD_ENTRY_QUALIFIER:
                        String encoded = input.readString();

                        String val = converterFactory.getDecodedQualifier(encoded);
                        if (val != null) {
                            encoded = converterFactory.getDecodedQualifier(encoded);
                        }

                        String[] split = encoded.split("@");
                        Class<? extends Enum> type;
                        try {
                            type = (Class<? extends Enum>) Class.forName(split[0]);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException("Could not convert encoded enum '" + encoded
                                    + "'!", e);
                        }
                        Enum<?> enumValue = Enum.valueOf(type, split[1]);
                        qualifiers.add(enumValue);
                        break;
                    default:
                        break;
                }
            }
            if (value != null) {
                if (order < 0) {
                    throw new RuntimeException("No valued index read");
                }
                values.add(new QualifiedValue<>(value, qualifiers, order));
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not read field entry ("
                    + key.getType().getSimpleName() + ") from byte array ("
                    + data.length + " bytes)!", e);
        } finally {
            try {
                bin.close();
            } catch (IOException e) {
                throw new RuntimeException("Could not close input stream!", e);
            }
        }
    }

    /**
     * Indicates if it is able to convert a given class
     *
     * @param cls
     * @return can or cannot
     */
    public boolean canConvert(Class<?> cls) {
        Converter converter = converterFactory.getConverter(cls);
        if (converter != null) {
            return true;
        } else {
            BaseTypeEncoder encoder = converterFactory.getBaseTypeEncoder(cls);
            return encoder != null;
        }
    }
}
