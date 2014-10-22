/* ObjectToXmlFieldSerializer.java - created on 2 de Mai de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.xml.serializer;

import java.io.StringReader;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import org.theeuropeanlibrary.maia.common.converter.ConverterException;
import org.theeuropeanlibrary.maia.converter.xml.util.XmlUtil;

import org.w3c.dom.CharacterData;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Converts a Metadata object in to the XMl serialization
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @date 2 de Mai de 2011
 */
public class MetadataXmlFieldSerializer implements XmlFieldConverter<String> {

    private DocumentBuilderFactory domfactory;
    private DocumentBuilder documentBuilder;

    /**
     * Creates a new instance of this class.
     */
    public MetadataXmlFieldSerializer() {
        super();

        domfactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = domfactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String decode(Element xmlElement) {
// Element localXmlElement = (Element) xmlElement.getFirstChild();
        Document doc = documentBuilder.newDocument();

        Node first = null;
        NodeList nodeList = xmlElement.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (!(nodeList.item(i) instanceof CharacterData)) {
                first = nodeList.item(i);
                break;
            }
        }

        Node newOneToMove = doc.importNode(first, true);
        doc.appendChild(newOneToMove);

        Properties xmlOutputProperties = new Properties();
        xmlOutputProperties.setProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        String ret = XmlUtil.writeDomToString(doc, xmlOutputProperties);
        String format = xmlElement.getAttribute("Format");
        return ret;
    }

    @Override
    public void encode(String bean, Element xmlParentElement) throws ConverterException {
        try {
            if (bean != null) {
// Element fieldElement = xmlParentElement.getOwnerDocument()
// .createElementNS(ObjectModelRegistry.XML_NAMESPACE,
// "Metadata");
//                xmlParentElement.setAttribute("Format", bean.getFormat());

                String recordInXml = bean;

                Document metadataDom = XmlUtil.parseDom(new StringReader(recordInXml));
                Node newOneToMove = xmlParentElement.getOwnerDocument().importNode(
                        metadataDom.getDocumentElement(), true);
                xmlParentElement.appendChild(newOneToMove);
// fieldElement.setTextContent("<![CDATA[" + recordInXml + "]]>");
// xmlParentElement.appendChild(fieldElement);
            }
        } catch (DOMException e) {
            throw new ConverterException("Exception during serialization", e);
        }
    }
}
