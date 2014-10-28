package org.theeuropeanlibrary.maia.converter.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.theeuropeanlibrary.maia.common.definitions.Provider;

public class ProviderObjectMapper extends ObjectMapper {

    public ProviderObjectMapper(EntityJsonSerializer<Provider> serializer, EntityJsonDeserializer<Provider> deserializer) {
        SimpleModule module = new SimpleModule(Provider.class.getSimpleName());
        module.addSerializer(Provider.class, serializer);
        module.addDeserializer(Provider.class, deserializer);
        this.registerModule(module);
    }

}
