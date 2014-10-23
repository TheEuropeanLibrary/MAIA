package org.theeuropeanlibrary.maia.converter.binary;

import org.theeuropeanlibrary.maia.common.definitions.Record;
import org.theeuropeanlibrary.maia.converter.binary.factory.BinaryConverterFactory;

/**
 * This class converts records from and to byte arrays.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public class RecordEntityBinaryConverter extends AbstractEntityBinaryConverter<Record> {

    public RecordEntityBinaryConverter(BinaryConverterFactory factory) {
        super(factory);
    }

    @Override
    protected Record createEntity() {
        return new Record();
    }

    @Override
    public Class<Record> getDecodeType() {
        return Record.class;
    }
}
