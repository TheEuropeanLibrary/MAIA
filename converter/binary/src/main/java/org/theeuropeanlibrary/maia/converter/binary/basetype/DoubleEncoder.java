/* EnumFieldEncoder.java - created on 6 de Abr de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.binary.basetype;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import org.theeuropeanlibrary.maia.converter.binary.basetype.BaseTypeEncoder;

/**
 * A <code>BaseTypeEncoder</code> for <code>Double</code>
 * 
 * @author Nuno Freire (nfreire@gmail.com)
 * @date 6 de Abr de 2011
 */
public class DoubleEncoder implements BaseTypeEncoder<Double> {

	@Override
	public Double decode(CodedInputStream input) throws Exception {
		return input.readDouble();
	}

	@Override
	public void encode(int fieldId, Double value, CodedOutputStream output)
			throws Exception {
		output.writeDouble(fieldId, value);
	}


}
