package org.theeuropeanlibrary.maia.converter.json;

import org.theeuropeanlibrary.maia.common.definitions.Record;
import org.theeuropeanlibrary.maia.converter.json.factory.JsonConverterFactory;

/**
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 23.10.2014
 */
public class RecordEntityJsonDeserializer extends EntityJsonDeserializer<Record> {

    public RecordEntityJsonDeserializer(JsonConverterFactory factory) {
        super(factory);
    }

    @Override
    protected Record createEntity() {
        return new Record();
    }

}
