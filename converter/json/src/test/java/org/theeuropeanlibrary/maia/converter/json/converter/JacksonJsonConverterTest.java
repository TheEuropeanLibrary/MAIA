package org.theeuropeanlibrary.maia.converter.json.converter;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

public class JacksonJsonConverterTest {

    @Test
	public void JacksonJsonConverterTest() throws JsonProcessingException {

    	CustomObjectMapper mapper = new CustomObjectMapper();
    	
    	DataProviderDummy p = new DataProviderDummy("manosId");
    	p.setName("manos");
    	
    	final String json = mapper.writeValueAsString(p);
    	System.out.println(json);
	}
}
