package org.theeuropeanlibrary.maia.converter.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.theeuropeanlibrary.maia.common.AbstractEntity;
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
public abstract class EntityJsonDeserializer<T extends AbstractEntity> extends JsonDeserializer<T> {

    private final JsonConverterFactory factory;

    public EntityJsonDeserializer(JsonConverterFactory factory) {
        this.factory = factory;
    }

    protected abstract T createEntity();

    @Override
    public T deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        T entity = createEntity();
        while (true) {
            JsonToken token = jp.nextValue();
            if (token == null) {
                break;
            }
            if (token.equals(JsonToken.END_OBJECT)) {
                continue;
            }

            String keyName = jp.getCurrentName();
            if (keyName.equals("id")) {
                String id = jp.getText();
                entity.setId(id);
                continue;
            }

            Set<Enum<?>> qualifiers = new HashSet<>(3);
            if (jp.getText() == null || jp.getText().equals("{")) {
                while (true) {
                    token = jp.nextToken();
                    String qualifierName = jp.getCurrentName();
                    Class qualClass = factory.getQualifier(qualifierName);
                    if (qualClass != null) {
                        token = jp.nextToken();
                        Enum enumVal = Enum.valueOf(qualClass, jp.getText());
                        qualifiers.add(enumVal);
                    } else {
                        break;
                    }
                }
            }

            TKey<?, ?> key = factory.getKey(keyName);
            JsonDeserializer<?> deserializer = factory.getDeserializer(key.getType());
            Object value;
            if (deserializer != null) {
                value = deserializer.deserialize(jp, dc);
            } else {
                deserializer = factory.getBaseTypeDeserializer(key.getType());
                if (jp.getText() == null || jp.getText().equals("{") || jp.getText().equals(jp.getCurrentName())) {
                    token = jp.nextToken();
                }
                value = deserializer.deserialize(jp, dc);
            }

            entity.addValue(key, value, qualifiers.toArray(new Enum[qualifiers.size()]));
        }
        return entity;
    }

}
