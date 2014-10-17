/* EnumFieldEncoder.java - created on 6 de Abr de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.xml.basetype;



/**
 * A <code>BaseTypeEncoder</code> for <code>Integer</code>
 * 
 * @author Nuno Freire (nfreire@gmail.com)
 * @date 6 de Abr de 2011
 */
public class IntegerEncoder implements BaseTypeEncoder<Integer> {


	@Override
	public Integer decodeFromString(String value) throws Exception {
		return Integer.valueOf(value);
	}

	@Override
	public String encodeToString(Integer value) throws Exception {
		return value.toString();
	}
}
