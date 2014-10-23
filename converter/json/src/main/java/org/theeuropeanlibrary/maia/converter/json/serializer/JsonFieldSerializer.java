package org.theeuropeanlibrary.maia.converter.json.serializer;

import com.fasterxml.jackson.databind.JsonSerializer;
import java.lang.reflect.Method;

/**
 * Handles the serialization of fields using the reflection based serialization
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 22.10.2014
 */
public abstract class JsonFieldSerializer<T> extends JsonSerializer<T> {

    /**
     * Initializes the field serializer
     *
     * @param fieldGet the method to invoke in order to get, from the object,
     * the value to be encoded
     */
    public abstract void configure(Method fieldGet);
}
