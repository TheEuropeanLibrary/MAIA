package org.theeuropeanlibrary.maia.converter.xml;

import org.theeuropeanlibrary.maia.common.converter.ConverterException;
import org.theeuropeanlibrary.maia.common.definitions.Dataset;
import org.theeuropeanlibrary.maia.common.definitions.Record;
import org.theeuropeanlibrary.maia.converter.xml.factory.XmlFieldConverterFactory;
import org.theeuropeanlibrary.maia.converter.xml.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This class converts records from and to xml.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public class RecordEntityXmlConverter extends AbstractEntityXmlConverter<Record> {

    public RecordEntityXmlConverter(XmlFieldConverterFactory factory) {
        super(factory);
    }

    @Override
    protected Record createEntity() {
        return new Record();
    }

    @Override
    public Class<Record> getDecodeType() {
        return Record.class;
    }
    
    @Override
    public Element encode(Record record)  throws ConverterException {
        Document newDocument = XmlUtil.newDocument();
        newDocument.appendChild(newDocument.createElement("Record"));
        encode(record, newDocument.getDocumentElement());
        return newDocument.getDocumentElement();
    }
}
