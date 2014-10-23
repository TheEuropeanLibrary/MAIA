/* FieldBufferSetter.java - created on 5 de Abr de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.json.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
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
public class BaseTypeJsonFieldDeserializer extends JsonFieldDeserializer<Object> {

    private Method fieldSet;
    private final JsonDeserializer deserializer;

    /**
     * Creates a new instance of this class.
     *
     * @param theClass
     */
    public BaseTypeJsonFieldDeserializer(Class<?> theClass) {
        this.deserializer = BaseTypeJsonFactory.newFieldDeserializerFor(theClass);
    }

    /**
     * Creates a new instance of this class.
     *
     * @param deserializer
     */
    @SuppressWarnings("rawtypes")
    public BaseTypeJsonFieldDeserializer(JsonDeserializer deserializer) {
        this.deserializer = deserializer;
    }

    @Override
    public void configure(Method fieldSet) {
        this.fieldSet = fieldSet;
    }

    @Override
    public Object deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        return deserializer.deserialize(jp, dc);
    }
    
    @Override
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
