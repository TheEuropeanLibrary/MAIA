/* BinarySerializer.java - created on Jun 14, 2013, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.json.basetype;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

/**
 * A <code>BaseTypeEncoder</code> for <code>Field</code>
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 22.10.2014
 */
public class BinarySerializer extends JsonSerializer<byte[]> {

    @Override
    public void serialize(byte[] t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        jg.writeBinary(t);
    }
}
