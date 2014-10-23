/* FieldBufferSetter.java - created on 5 de Abr de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.theeuropeanlibrary.maia.converter.json.basetype.BaseTypeJsonFactory;

/**
 * /** A <code>FieldConverterInterface</code> implementation for base types
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 22.10.2014
 */
public class BaseTypeJsonFieldSerializer extends JsonFieldSerializer<Object> {

    private Method fieldGet;
    private final JsonSerializer serializer;

    /**
     * Creates a new instance of this class.
     *
     * @param theClass
     */
    public BaseTypeJsonFieldSerializer(Class<?> theClass) {
        this.serializer = BaseTypeJsonFactory.newFieldSerializerFor(theClass);
    }

    /**
     * Creates a new instance of this class.
     *
     * @param serializer
     */
    @SuppressWarnings("rawtypes")
    public BaseTypeJsonFieldSerializer(JsonSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public void configure(Method fieldGet) {
        this.fieldGet = fieldGet;
    }

    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("rawtypes")
    public JsonSerializer getSerializer() {
        return serializer;
    }
}
