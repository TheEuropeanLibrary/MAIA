/* BinarySerializer.java - created on Jun 14, 2013, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.json.basetype;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * A <code>BaseTypeEncoder</code> for <code>Field</code>
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 22.10.2014
 */
public class EnumDeserializer extends JsonDeserializer<Enum> {

    private static final Pattern SPACE_PREFFIX = Pattern.compile("^\\s+");
    private static final Pattern SPACE_SUFFIX = Pattern.compile("\\s+$");

    /**
     * type of enum
     */
    private final Class<? extends Enum> type;

    /**
     * Creates a new instance of this class.
     *
     * @param type the type of enum
     */
    public EnumDeserializer(Class<? extends Enum> type) {
        super();
        this.type = type;
    }

    @Override
    public Enum deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        String cleaned = SPACE_SUFFIX.matcher(SPACE_PREFFIX.matcher(jp.getText()).replaceFirst("")).replaceFirst(
                "");
        Enum en;
        try {
            en = Enum.valueOf(type, cleaned);
        } catch (Exception e) {
            // FIXME: Hack!!!!
//            if (type.equals(Language.class)) {
//                en = Language.lookupLanguage(cleaned);
//                if (en == null) {
//                    throw e;
//                }
//            } else {
            throw e;
//            }
        }
        return en;
    }
}
