package org.theeuropeanlibrary.maia.converter.json;

import org.theeuropeanlibrary.maia.common.definitions.Provider;
import org.theeuropeanlibrary.maia.converter.json.factory.JsonConverterFactory;

/**
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 23.10.2014
 */
public class ProviderEntityJsonDeserializer extends EntityJsonDeserializer<Provider> {

    public ProviderEntityJsonDeserializer(JsonConverterFactory factory) {
        super(factory);
    }

    @Override
    protected Provider createEntity() {
        return new Provider();
    }

}
