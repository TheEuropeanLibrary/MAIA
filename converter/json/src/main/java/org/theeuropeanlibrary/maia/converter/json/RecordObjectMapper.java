package org.theeuropeanlibrary.maia.converter.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.theeuropeanlibrary.maia.common.definitions.Record;

public class RecordObjectMapper extends ObjectMapper {

    public RecordObjectMapper(EntityJsonSerializer<Record> serializer, EntityJsonDeserializer<Record> deserializer) {
        SimpleModule module = new SimpleModule(Record.class.getSimpleName());
        module.addSerializer(Record.class, serializer);
        module.addDeserializer(Record.class, deserializer);
        this.registerModule(module);
    }

}
