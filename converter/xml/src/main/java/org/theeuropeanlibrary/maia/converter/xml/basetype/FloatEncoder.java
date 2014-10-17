/* EnumFieldEncoder.java - created on 6 de Abr de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.xml.basetype;



/**
 * A <code>BaseTypeEncoder</code> for <code>Float</code>
 * 
 * @author Nuno Freire (nfreire@gmail.com)
 * @date 6 de Abr de 2011
 */
public class FloatEncoder implements BaseTypeEncoder<Float> {


	@Override
	public Float decodeFromString(String value) throws Exception {
		return Float.valueOf(value);
	}

	@Override
	public String encodeToString(Float value) throws Exception {
		return Float.toString(value.floatValue());
	}
}
