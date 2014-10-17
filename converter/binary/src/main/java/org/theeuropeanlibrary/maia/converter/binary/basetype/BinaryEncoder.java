/* BinaryEncoder.java - created on Jun 14, 2013, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.binary.basetype;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import org.theeuropeanlibrary.maia.converter.binary.common.BaseTypeEncoder;

/**
 * A <code>BaseTypeEncoder</code> for <code>Field</code>
 *
 * @author Markus Muhr (markus.muhr@kb.nl)
 * @since Jun 14, 2013
 */
public class BinaryEncoder implements BaseTypeEncoder<byte[]> {

    @Override
    public byte[] decode(CodedInputStream input) throws Exception {
        return input.readBytes().toByteArray();
    }

    @Override
    public void encode(int fieldId, byte[] value, CodedOutputStream output) throws Exception {
        output.writeBytes(fieldId, ByteString.copyFrom(value));
    }
}
