package org.theeuropeanlibrary.maia.converter.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import org.theeuropeanlibrary.maia.common.AbstractEntity;
import org.theeuropeanlibrary.maia.converter.json.factory.JsonConverterFactory;

/**
 * Handles the json serialization of Entity to xml and back.
 *
 * @param <T> generic type of entity
 * @author Nuno Freire (nuno.freire@theeuropeanlibrary.org)
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 22.10.2014
 */
public class EntityJsonDeserializer<T extends AbstractEntity> extends JsonDeserializer<T> {

    private final JsonConverterFactory factory;

    public EntityJsonDeserializer(JsonConverterFactory factory) {
        this.factory = factory;
    }

    @Override
    public T deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
