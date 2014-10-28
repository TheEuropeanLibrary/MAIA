package org.theeuropeanlibrary.maia.converter.json;

import org.theeuropeanlibrary.maia.common.definitions.Dataset;
import org.theeuropeanlibrary.maia.converter.json.factory.JsonConverterFactory;

/**
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 23.10.2014
 */
public class DatasetEntityJsonDeserializer extends EntityJsonDeserializer<Dataset> {

    public DatasetEntityJsonDeserializer(JsonConverterFactory factory) {
        super(factory);
    }

    @Override
    protected Dataset createEntity() {
        return new Dataset();
    }

}
