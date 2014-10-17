/* BinaryEncoder.java - created on Jun 14, 2013, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.xml.basetype;

/**
 * A <code>BaseTypeEncoder</code> for <code>byte[]</code>
 * 
 * @author Markus Muhr (markus.muhr@kb.nl)
 * @since Jun 14, 2013
 */
public class BinaryEncoder implements BaseTypeEncoder<byte[]> {
    @Override
    public byte[] decodeFromString(String value) throws Exception {
        return value.getBytes();
    }

    @Override
    public String encodeToString(byte[] value) throws Exception {
        return new String(value);
    }
}