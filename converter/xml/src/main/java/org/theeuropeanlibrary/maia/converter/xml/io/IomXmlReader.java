/* IomReader.java - created on 23 de Jan de 2013, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.xml.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.theeuropeanlibrary.maia.common.converter.ConverterException;
import org.theeuropeanlibrary.maia.common.definitions.Record;
import org.theeuropeanlibrary.maia.converter.xml.RecordEntityXmlConverter;

import org.theeuropeanlibrary.maia.converter.xml.util.XmlUtil;

/**
 * Reads MetadataRecords in XML from an input stream, without loading the
 * complete file into a DOM.
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @since 23 de Jan de 2013
 */
public class IomXmlReader implements Iterable<Record<String>>, Iterator<Record<String>> {

    private static final Pattern START_TAG_PATTERN = Pattern.compile("<([^:]+:)?MetadataRecord[\\s>]");
    private static final Pattern END_TAG_PATTERN = Pattern.compile("</([^:]+:)?MetadataRecord\\s*>");

    private BufferedReader reader = null;
    private String currentLine = null;

    private Record<String> nextRecord = null;

    /**
     * Creates a new instance of this class.
     *
     * @param input
     * @throws FileNotFoundException
     */
    public IomXmlReader(File input) throws FileNotFoundException {
        this(new FileInputStream(input));
    }

    /**
     * Creates a new instance of this class.
     *
     * @param input
     */
    public IomXmlReader(InputStream input) {
        try {
            reader = new BufferedReader(new InputStreamReader(input, "UTF8"));
            readNext();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public boolean hasNext() {
        return nextRecord != null;
    }

    @Override
    public Record<String> next() {
        Record<String> ret = nextRecord;
        readNext();
        return ret;
    }

    @Override
    public Iterator<Record<String>> iterator() {
        if (reader != null) {
            return this;
        }
        throw new UnsupportedOperationException("Sorry, multiple iterations not implemented.");
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Sorry, not implemented.");
    }

    private void readNext() {
        try {
            if (currentLine == null || currentLine.isEmpty()) {
                currentLine = reader.readLine();
            }

            StringBuilder recordXmlString = new StringBuilder();

            int recordStartCharIdx = -1;
            while (recordStartCharIdx < 0) {
                Matcher m = START_TAG_PATTERN.matcher(currentLine);
                if (!m.find()) {
                    currentLine = reader.readLine();
                    if (currentLine == null) {
                        reader.close();
                        reader = null;
                        nextRecord = null;
                        return;
                    }
                } else {
                    recordStartCharIdx = m.start();
                }
            }
            if (recordStartCharIdx > 0) {
                currentLine = currentLine.substring(recordStartCharIdx);
            }
            recordStartCharIdx = 0;
            int recordEndCharIdx = -1;
            while (recordEndCharIdx < 0) {
                Matcher m = END_TAG_PATTERN.matcher(currentLine);
                if (!m.find()) {
                    recordXmlString.append(currentLine);
                    currentLine = reader.readLine();
                    if (currentLine == null) {
                        reader.close();
                        reader = null;
                        nextRecord = null;
                        return;
                    }
                } else {
                    recordEndCharIdx = m.end();
                }
            }
            recordXmlString.append(currentLine.subSequence(0, recordEndCharIdx));
            currentLine = currentLine.substring(recordEndCharIdx);

            nextRecord = new RecordEntityXmlConverter(null).decode(XmlUtil.parseDom(new StringReader(recordXmlString.toString())).getDocumentElement());
        } catch (IOException | ConverterException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
