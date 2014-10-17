/* EnumFieldEncoder.java - created on 6 de Abr de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.xml.basetype;


/**
 * A <code>BaseTypeEncoder</code> for <code>Double</code>
 * 
 * @author Nuno Freire (nfreire@gmail.com)
 * @date 6 de Abr de 2011
 */
public class DoubleEncoder implements BaseTypeEncoder<Double> {
	@Override
	public Double decodeFromString(String value) throws Exception {
		return Double.valueOf(value);
	}

	@Override
	public String encodeToString(Double value) throws Exception {
		return Double.toString(value.doubleValue());
	}

}
