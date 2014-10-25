package org.theeuropeanlibrary.maia.converter.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.theeuropeanlibrary.maia.common.AbstractEntity;
import org.theeuropeanlibrary.maia.common.Entity.QualifiedValue;
import org.theeuropeanlibrary.maia.common.TKey;
import org.theeuropeanlibrary.maia.converter.json.factory.JsonConverterFactory;

/**
 * Handles the json serialization of Entity to JSON..
 *
 * @param <T> generic type of entity
 * @author Nuno Freire (nuno.freire@theeuropeanlibrary.org)
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 22.10.2014
 */
public class EntityJsonSerializer<T extends AbstractEntity> extends JsonSerializer<T> {

    private final JsonConverterFactory factory;

    public EntityJsonSerializer(JsonConverterFactory factory) {
        this.factory = factory;
    }

    @Override
    public void serialize(T t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        jg.writeStartObject();
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
}
