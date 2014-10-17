/* EnumFieldEncoder.java - created on 6 de Abr de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.xml.basetype;



/**
 * A <code>BaseTypeEncoder</code> for <code>Short</code>
 * 
 * @author Nuno Freire (nfreire@gmail.com)
 * @date 6 de Abr de 2011
 */
public class ShortEncoder implements BaseTypeEncoder<Short> {


	@Override
	public Short decodeFromString(String value) throws Exception {
		return Short.valueOf(value);
	}

	@Override
	public String encodeToString(Short value) throws Exception {
		return value.toString();
	}
}
