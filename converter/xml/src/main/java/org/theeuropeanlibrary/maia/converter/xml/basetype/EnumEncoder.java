/* EnumFieldEncoder.java - created on 6 de Abr de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.xml.basetype;

import java.util.regex.Pattern;

/**
 * A field converter for Enum types
 * 
 * @author Nuno Freire (nfreire@gmail.com)
 * @date 6 de Abr de 2011
 */
@SuppressWarnings("rawtypes")
public class EnumEncoder implements BaseTypeEncoder<Enum<?>> {
    Pattern               SPACE_PREFFIX = Pattern.compile("^\\s+");
    Pattern               SPACE_SUFFIX  = Pattern.compile("\\s+$");

    /**
     * the type of enum
     */
    Class<? extends Enum> type;

    /**
     * Creates a new instance of this class.
     * 
     * @param type
     *            the type of enum
     */
    public EnumEncoder(Class<? extends Enum> type) {
        super();
        this.type = type;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Enum<?> decodeFromString(String value) throws Exception {
        String cleaned = SPACE_SUFFIX.matcher(SPACE_PREFFIX.matcher(value).replaceFirst("")).replaceFirst(
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

    @Override
    public String encodeToString(Enum<?> value) throws Exception {
        return value.toString();
    }

    /**
     * @return type of enum
     */
    public Class<? extends Enum> getType() {
        return type;
    }
}
