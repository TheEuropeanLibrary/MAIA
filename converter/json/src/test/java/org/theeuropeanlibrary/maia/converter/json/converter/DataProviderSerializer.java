package org.theeuropeanlibrary.maia.converter.json.converter;

import java.io.IOException;

import org.theeuropeanlibrary.maia.common.model.DataProvider;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DataProviderSerializer  extends JsonSerializer<DataProvider> {
	
	@Override
	public void serialize(DataProvider p, JsonGenerator j,
			SerializerProvider sP) throws IOException,
			JsonProcessingException {

        j.writeStartObject();
        
		j.writeStringField("Custom JSON serializer with Jackson", "manos");
		j.writeStringField("id", p.getId());
		j.writeStringField("name", p.getName());
		
		j.writeFieldName("CustomBinaryField");
		j.writeBinary(getBytesFromCustomMarkusSerializer());
		
		j.writeObjectField("MarkusBytesObjectField", getBytesFromCustomMarkusSerializer());
		
        j.writeEndObject();
	}
	
	private byte[] getBytesFromCustomMarkusSerializer() {
		
		return "MarkusCustomSerializer".getBytes();
	}
}
