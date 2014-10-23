package org.theeuropeanlibrary.maia.converter.binary;

import org.theeuropeanlibrary.maia.common.definitions.Provider;
import org.theeuropeanlibrary.maia.converter.binary.factory.BinaryConverterFactory;

/**
 * This class converts pOroviders from and to byte arrays.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public class ProviderEntityBinaryConverter extends AbstractEntityBinaryConverter<Provider> {

    public ProviderEntityBinaryConverter(BinaryConverterFactory factory) {
        super(factory);
    }

    @Override
    protected Provider createEntity() {
        return new Provider();
    }

    @Override
    public Class<Provider> getDecodeType() {
        return Provider.class;
    }
}
