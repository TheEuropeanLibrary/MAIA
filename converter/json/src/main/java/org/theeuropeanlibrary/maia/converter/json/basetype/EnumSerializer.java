/* StringSerializer.java - created on Jun 14, 2013, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.json.basetype;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * A <code>BaseTypeEncoder</code> for <code>Field</code>
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 22.10.2014
 */
public class EnumSerializer extends JsonSerializer<Enum> implements SchemaAware {

    /**
     * type of enum
     */
    private final Class<? extends Enum> type;

    /**
     * Creates a new instance of this class.
     *
     * @param type the type of enum
     */
    public EnumSerializer(Class<? extends Enum> type) {
        super();
        this.type = type;
    }

    @Override
    public void serialize(Enum t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        jg.writeString(t.toString());
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
//        node.put("type", "string");
        ArrayNode vals = JsonNodeFactory.instance.arrayNode();
        node.put("enum", vals);
        for (Enum e : type.getEnumConstants()) {
            vals.add(e.toString());
        }
        return node;
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint, boolean isOptional) throws JsonMappingException {
        ObjectNode schema = (ObjectNode) getSchema(provider, typeHint);
        if (!isOptional) {
            schema.put("required", !isOptional);
        }
        return schema;
    }
}
