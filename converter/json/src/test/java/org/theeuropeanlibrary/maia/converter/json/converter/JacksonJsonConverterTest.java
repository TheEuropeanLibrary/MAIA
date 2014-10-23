package org.theeuropeanlibrary.maia.converter.json.converter;

import org.junit.Test;
import org.theeuropeanlibrary.maia.common.model.DataProvider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonJsonConverterTest {

    @Test
	public void JacksonJsonConverterTest() throws JsonProcessingException {
    	
    	DataProvider p = new DataProvider("manosId");
    	p.setName("manos");
    	
    	ObjectMapper mapper = new ObjectMapper();
    	final String json = mapper.writeValueAsString(p);
    	System.out.println(json);
	}
}
