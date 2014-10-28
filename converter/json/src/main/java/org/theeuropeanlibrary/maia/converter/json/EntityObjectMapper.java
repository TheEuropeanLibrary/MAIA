package org.theeuropeanlibrary.maia.converter.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.theeuropeanlibrary.maia.common.Entity;
import org.theeuropeanlibrary.maia.common.definitions.Dataset;
import org.theeuropeanlibrary.maia.common.definitions.Provider;
import org.theeuropeanlibrary.maia.common.definitions.Record;
import org.theeuropeanlibrary.maia.common.registry.EntityRegistry;
import org.theeuropeanlibrary.maia.converter.json.factory.BaseJsonConverterFactory;

public class EntityObjectMapper extends ObjectMapper {

    public EntityObjectMapper(EntityRegistry providerRegistry, EntityRegistry datasetRegistry, EntityRegistry recordRegistry) {
        SimpleModule module = new SimpleModule(Entity.class.getSimpleName());
        if (providerRegistry != null) {
            module.addSerializer(Provider.class, new EntityJsonSerializer<>(new BaseJsonConverterFactory(providerRegistry)));
            module.addDeserializer(Provider.class, new ProviderEntityJsonDeserializer(new BaseJsonConverterFactory(providerRegistry)));
        }
        if (datasetRegistry != null) {
            module.addSerializer(Dataset.class, new EntityJsonSerializer<>(new BaseJsonConverterFactory(datasetRegistry)));
            module.addDeserializer(Dataset.class, new DatasetEntityJsonDeserializer(new BaseJsonConverterFactory(datasetRegistry)));
        }
        if (recordRegistry != null) {
            module.addSerializer(Record.class, new EntityJsonSerializer<>(new BaseJsonConverterFactory(recordRegistry)));
            module.addDeserializer(Record.class, new RecordEntityJsonDeserializer(new BaseJsonConverterFactory(recordRegistry)));
        }
        this.registerModule(module);
    }

}
