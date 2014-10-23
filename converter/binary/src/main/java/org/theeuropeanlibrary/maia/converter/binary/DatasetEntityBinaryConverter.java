package org.theeuropeanlibrary.maia.converter.binary;

import org.theeuropeanlibrary.maia.common.definitions.Dataset;
import org.theeuropeanlibrary.maia.converter.binary.factory.BinaryConverterFactory;

/**
 * This class converts records from and to byte arrays.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public class DatasetEntityBinaryConverter extends AbstractEntityBinaryConverter<Dataset> {

    public DatasetEntityBinaryConverter(BinaryConverterFactory factory) {
        super(factory);
    }

    @Override
    protected Dataset createEntity() {
        return new Dataset();
    }

    @Override
    public Class<Dataset> getDecodeType() {
        return Dataset.class;
    }
}
