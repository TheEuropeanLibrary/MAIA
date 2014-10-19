package org.theeuropeanlibrary.maia.converter.xml;

import org.theeuropeanlibrary.maia.common.converter.ConverterException;
import org.theeuropeanlibrary.maia.common.definitions.Dataset;
import org.theeuropeanlibrary.maia.converter.xml.common.XmlFieldConverterFactory;
import org.theeuropeanlibrary.maia.converter.xml.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This class converts records from and to xml.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public class DatasetEntityXmlConverter extends AbstractEntityXmlConverter<Dataset> {

    public DatasetEntityXmlConverter(XmlFieldConverterFactory factory) {
        super(factory);
    }

    @Override
    protected Dataset createEntity() {
        return new Dataset();
    }

    @Override
    public Class<Dataset> getDecodeType() {
        return Dataset.class;
    }

    @Override
    public Element encode(Dataset dataset) throws ConverterException {
        Document newDocument = XmlUtil.newDocument();
        newDocument.appendChild(newDocument.createElement("Dataset"));
        encode(dataset, newDocument.getDocumentElement());
        return newDocument.getDocumentElement();
    }
}
