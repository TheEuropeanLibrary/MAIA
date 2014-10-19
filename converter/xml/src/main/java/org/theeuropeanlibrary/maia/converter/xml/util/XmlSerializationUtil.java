package org.theeuropeanlibrary.maia.converter.xml.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.theeuropeanlibrary.maia.common.converter.ConverterException;
import org.theeuropeanlibrary.maia.converter.xml.common.XmlFieldConverter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Utility methods for processing DOM objects
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @date 9 de Mai de 2011
 */
public class XmlSerializationUtil {

    private static final DocumentBuilderFactory dbfac = DocumentBuilderFactory
            .newInstance();

    /**
     * Convenient function to encode a stand-alone object to a complete xml
     * document.
     *
     * @param <T> The class to encode
     *
     * @param bean
     * @param serializer
     * @return xml document
     * @throws org.theeuropeanlibrary.maia.common.ConverterException
     */
    public static <T> Document encodeToXML(T bean,
            XmlFieldConverter<T> serializer) throws ConverterException {
        DocumentBuilder docBuilder;
        try {
            docBuilder = dbfac.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        Document doc = docBuilder.newDocument();
        Element rootEl = doc.createElement("MetadataRecord");
        doc.appendChild(rootEl);

        serializer.encode(bean, rootEl);
        return doc;
    }
}
