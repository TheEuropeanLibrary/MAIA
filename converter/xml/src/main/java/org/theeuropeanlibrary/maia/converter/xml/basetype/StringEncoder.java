package org.theeuropeanlibrary.maia.converter.xml.basetype;


/**
 * A <code>BaseTypeEncoder</code> for <code>String</code>
 * 
 * @author Nuno Freire (nfreire@gmail.com)
 * @date 6 de Abr de 2011
 */
public class StringEncoder implements BaseTypeEncoder<String> {
    @Override
    public String decodeFromString(String value) throws Exception {
        return value.trim();
    }

    @Override
    public String encodeToString(String value) throws Exception {
        return value;
    }
}
