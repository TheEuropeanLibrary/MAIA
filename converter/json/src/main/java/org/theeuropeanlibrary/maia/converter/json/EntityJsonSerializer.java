package org.theeuropeanlibrary.maia.converter.json;

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
import java.util.List;
import java.util.Set;
import org.theeuropeanlibrary.maia.common.AbstractEntity;
import org.theeuropeanlibrary.maia.common.Entity.QualifiedValue;
import org.theeuropeanlibrary.maia.common.TKey;
import org.theeuropeanlibrary.maia.converter.json.factory.JsonConverterFactory;

/**
 * Handles the json serialization of Entity to xml and back.
 *
 * @param <T> generic type of entity
 * @author Nuno Freire (nuno.freire@theeuropeanlibrary.org)
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 22.10.2014
 */
public class EntityJsonSerializer<T extends AbstractEntity> extends JsonSerializer<T> implements SchemaAware {

    private final JsonConverterFactory factory;

    public EntityJsonSerializer(JsonConverterFactory factory) {
        this.factory = factory;
    }

    @Override
    public void serialize(T t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        jg.writeStartObject();
        jg.writeStringField("id", t.getId().toString());
        Set<TKey<?, ?>> keys = t.getAvailableKeys();
        for (TKey<?, ?> key : keys) {
            JsonSerializer serializer = factory.getSerializer(key.getType());
            if (serializer != null) {
                List<QualifiedValue<?>> values = t.getQualifiedValues(key);
                for (QualifiedValue<?> value : values) {
                    jg.writeFieldName(factory.getElementName(key));
                    jg.writeStartObject();
                    Set<Enum<?>> qualifiers = value.getQualifiers();
                    for (Enum<?> q : qualifiers) {
                        jg.writeStringField(q.getClass().getSimpleName(), q.toString());
                    }
                    serializer.serialize(value.getValue(), jg, sp);
                    jg.writeEndObject();
                }
            } else {
                serializer = factory.getBaseTypeSerializer(key.getType());
                if (serializer != null) {
                    List<QualifiedValue<?>> values = t.getQualifiedValues(key);
                    for (QualifiedValue<?> value : values) {
                        Set<Enum<?>> qualifiers = value.getQualifiers();
                        if (qualifiers.isEmpty()) {
                            jg.writeFieldName(factory.getElementName(key));
                            serializer.serialize(value.getValue(), jg, sp);
                        } else {
                            jg.writeFieldName(factory.getElementName(key));
                            jg.writeStartObject();
                            for (Enum<?> q : qualifiers) {
                                jg.writeStringField(q.getClass().getSimpleName(), q.toString());
                            }
                            jg.writeFieldName("Value");
                            serializer.serialize(value.getValue(), jg, sp);
                            jg.writeEndObject();
                        }
                    }
                }
            }

        }
        jg.writeEndObject();
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        ObjectNode root = JsonNodeFactory.instance.objectNode();
        root.put("type", "object");

        ObjectNode properties = JsonNodeFactory.instance.objectNode();
        root.put("properties", properties);

        ObjectNode id = JsonNodeFactory.instance.objectNode();
        id.put("type", "string");

        properties.put("id", id);

        Set<TKey<?, ?>> keys = factory.getRegistry().getAvailableKeys();
        for (TKey<?, ?> key : keys) {
            JsonSerializer baseSerializer = factory.getBaseTypeSerializer(key.getType());
            Set<Class<? extends Enum<?>>> qualifiers = factory.getRegistry().getQualifiers(key);

            if (baseSerializer != null && (qualifiers == null || qualifiers.size() == 0)) {
                SchemaAware schemaSerializer = (SchemaAware) baseSerializer;
                JsonNode node = schemaSerializer.getSchema(provider, typeHint);
                properties.put(factory.getElementName(key), node);
            } else {
                ObjectNode node = JsonNodeFactory.instance.objectNode();
                node.put("type", "object");

                ObjectNode props;
                if (baseSerializer != null) {
                    props = JsonNodeFactory.instance.objectNode();
                } else {
                    SchemaAware schemaSerializer = (SchemaAware) factory.getSerializer(key.getType());
                    props = (ObjectNode) schemaSerializer.getSchema(provider, typeHint);
                }
                node.put("properties", props);

                if (qualifiers != null && qualifiers.size() > 0) {
                    addQualifierSchemaNodes(qualifiers, props);
                }

                if (baseSerializer != null) {
                    SchemaAware schemaSerializer = (SchemaAware) baseSerializer;
                    JsonNode val = schemaSerializer.getSchema(provider, typeHint);
                    props.put("value", val);
                }

                properties.put(factory.getElementName(key), node);
            }
        }

        return root;
    }

    private void addQualifierSchemaNodes(Set<Class<? extends Enum<?>>> qualifiers, ObjectNode props) {
        for (Class<? extends Enum<?>> qualifier : qualifiers) {
            ObjectNode inner = JsonNodeFactory.instance.objectNode();
            inner.put("type", "string");

            props.put(factory.getAttributeName(qualifier), inner);

            ArrayNode array = JsonNodeFactory.instance.arrayNode();
            inner.put("enum", array);

            Object[] vals = qualifier.getEnumConstants();
            for (Object val : vals) {
                array.add(val.toString());
            }
        }
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
