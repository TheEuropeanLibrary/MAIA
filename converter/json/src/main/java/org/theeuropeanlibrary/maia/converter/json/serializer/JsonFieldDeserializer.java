package org.theeuropeanlibrary.maia.converter.json.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Handles the serialization of fields using the reflection based serialization
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 22.10.2014
 */
public abstract class JsonFieldDeserializer<T> extends JsonDeserializer<T> {

    /**
     * Initializes the field serializer
     *
     * @param fieldSet the method to invoke in order to set the decoded value
     * into the object
     */
    public abstract void configure(Method fieldSet);

    public abstract void deserialize(Object bean, JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException;

}
