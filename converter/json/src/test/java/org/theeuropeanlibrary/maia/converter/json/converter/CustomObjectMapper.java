package org.theeuropeanlibrary.maia.converter.json.converter;
import org.theeuropeanlibrary.maia.common.model.DataProvider;
import org.theeuropeanlibrary.maia.common.model.DataProviderSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class CustomObjectMapper extends ObjectMapper {
	
	public CustomObjectMapper() {
		
        SimpleModule module = new SimpleModule("ObjectIdmodule");
        module.addKeySerializer(DataProvider.class, new DataProviderSerializer());
        this.registerModule(module);
    }
 
}
