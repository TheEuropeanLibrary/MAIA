package org.theeuropeanlibrary.maia.converter.binary;

import org.theeuropeanlibrary.maia.common.definitions.Provider;
import org.theeuropeanlibrary.maia.converter.binary.common.ConverterFactory;

/**
 * This class converts pOroviders from and to byte arrays.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public class ProviderEntityBytesConverter extends AbstractEntityBytesConverter<Provider> {

    public ProviderEntityBytesConverter(ConverterFactory factory) {
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
