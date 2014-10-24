package org.theeuropeanlibrary.maia.converter.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Handles the serialization of fields using the reflection based serialization
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 22.10.2014
 */
public class JsonFieldSerializer extends JsonSerializer {

    private final JsonSerializer serializer;
    private Method fieldGet;

    /**
     * Creates a new instance of this class.
     *
     * @param serializer
     */
    public JsonFieldSerializer(JsonSerializer serializer) {
        this.serializer = serializer;
    }

    public void configure(Method fieldGet) {
        this.fieldGet = fieldGet;
    }

    @Override
    public void serialize(Object bean, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        try {
            Object value = fieldGet.invoke(bean);
            if (value != null) {
                serializer.serialize(value, jg, sp);
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | IOException e) {
            throw new RuntimeException("Cannot retrieve value from object with reflections!", e);
        }
    }

    /**
     * @return encoder
     */
    public JsonSerializer getSerializer() {
        return serializer;
    }
}
