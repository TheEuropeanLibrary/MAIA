package org.theeuropeanlibrary.maia.converter.json.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Handles the serialization of fields using the reflection based serialization
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 22.10.2014
 */
public class JsonFieldDeserializer extends JsonDeserializer {

    private final JsonDeserializer deserializer;
    private Method fieldSet;

    /**
     * Creates a new instance of this class.
     *
     * @param deserializer
     */
    @SuppressWarnings("rawtypes")
    public JsonFieldDeserializer(JsonDeserializer deserializer) {
        this.deserializer = deserializer;
    }

    public void configure(Method fieldSet) {
        this.fieldSet = fieldSet;
    }

    @Override
    public Object deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        return deserializer.deserialize(jp, dc);
    }

    public void deserialize(Object bean, JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        Object val = deserializer.deserialize(jp, dc);
        try {
            fieldSet.invoke(bean, val);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException("Exception during serialization", e);
        }
    }

    /**
     * @return encoder
     */
    @SuppressWarnings("rawtypes")
    public JsonDeserializer getDeserializer() {
        return deserializer;
    }
}
