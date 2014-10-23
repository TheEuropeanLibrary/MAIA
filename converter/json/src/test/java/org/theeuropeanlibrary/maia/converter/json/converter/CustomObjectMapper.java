package org.theeuropeanlibrary.maia.converter.json.converter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class CustomObjectMapper extends ObjectMapper {
	
	public CustomObjectMapper() {
		
        SimpleModule module = new SimpleModule("ObjectIdmodule");
        module.addSerializer(DataProviderDummy.class, new DataProviderSerializer());
        this.registerModule(module);
    }
 
}
