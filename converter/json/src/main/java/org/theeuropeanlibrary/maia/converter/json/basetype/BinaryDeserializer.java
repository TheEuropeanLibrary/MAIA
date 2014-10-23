/* BinarySerializer.java - created on Jun 14, 2013, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.json.basetype;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

/**
 * A <code>BaseTypeEncoder</code> for <code>Field</code>
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 22.10.2014
 */
public class BinaryDeserializer extends JsonDeserializer<byte[]> {

    @Override
    public byte[] deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        return jp.getBinaryValue();
    }
}
