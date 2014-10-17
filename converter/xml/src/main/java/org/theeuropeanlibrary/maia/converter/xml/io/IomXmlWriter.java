/* IomReader.java - created on 23 de Jan de 2013, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.xml.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import org.theeuropeanlibrary.maia.common.ConverterException;
import org.theeuropeanlibrary.maia.common.definitions.Record;

import org.w3c.dom.Document;

import org.theeuropeanlibrary.maia.converter.xml.RecordEntityXmlConverter;
import org.theeuropeanlibrary.maia.converter.xml.util.XmlUtil;

/**
 * Writes MetadataRecords in XML to an ouput stream, in UTF-8
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @since 23 de Jan de 2013
 */
public class IomXmlWriter {

    private BufferedWriter writer = null;

    private final RecordEntityXmlConverter xmlSerializer = new RecordEntityXmlConverter(null);

    /**
     * Creates a new instance of this class.
     *
     * @param output
     * @throws FileNotFoundException
     */
    public IomXmlWriter(File output) throws FileNotFoundException {
        this(new FileOutputStream(output));
    }

    /**
     * Creates a new instance of this class.
     *
     * @param input
     */
    public IomXmlWriter(OutputStream input) {
        try {
            writer = new BufferedWriter(new OutputStreamWriter(input, "UTF8"));
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<Metadata>\n");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * @param mdr
     */
    public void write(Record<String> mdr) {
        try {
            Document newDocument = XmlUtil.newDocument();
            newDocument.appendChild(newDocument.createElement("Record"));
            xmlSerializer.encode(mdr, newDocument.getDocumentElement());
            writer.write(XmlUtil.writeDomToString(newDocument).replaceFirst("^[^>]+>[\\\n\\\r]?", ""));
            writer.write("\n");
        } catch (IOException | ConverterException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Close the writer
     */
    public void close() {
        try {
            writer.write("</Metadata>");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     *
     */
    public void flush() {
        try {
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
