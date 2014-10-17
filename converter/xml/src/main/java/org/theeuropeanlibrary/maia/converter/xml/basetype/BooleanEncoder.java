/* EnumFieldEncoder.java - created on 6 de Abr de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.xml.basetype;



/**
 * A <code>BaseTypeEncoder</code> for Boolean
 * 
 * @author Nuno Freire (nfreire@gmail.com)
 * @date 6 de Abr de 2011
 */
public class BooleanEncoder implements BaseTypeEncoder<Boolean> {

	@Override
	public Boolean decodeFromString(String value) throws Exception {
		return Boolean.valueOf(value);
	}

	@Override
	public String encodeToString(Boolean value) throws Exception {
		return value.toString();
	}
}
