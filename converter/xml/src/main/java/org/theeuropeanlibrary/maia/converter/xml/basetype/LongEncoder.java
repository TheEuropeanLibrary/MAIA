/* EnumFieldEncoder.java - created on 6 de Abr de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.xml.basetype;



/**
 * A <code>BaseTypeEncoder</code> for <code>Field</code>
 * 
 * @author Nuno Freire (nfreire@gmail.com)
 * @date 6 de Abr de 2011
 */
public class LongEncoder implements BaseTypeEncoder<Long> {

	@Override
	public Long decodeFromString(String value) throws Exception {
		return Long.valueOf(value);
	}

	@Override
	public String encodeToString(Long value) throws Exception {
		return value.toString();
	}
}
