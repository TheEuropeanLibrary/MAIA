package org.theeuropeanlibrary.maia.converter.xml.schema;

import java.io.File;
import java.util.HashSet;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import org.theeuropeanlibrary.maia.common.registry.EntityRegistry;
import org.theeuropeanlibrary.maia.converter.xml.AbstractEntityXmlConverter;
import org.theeuropeanlibrary.maia.converter.xml.factory.XmlFieldConverterFactory;

import org.theeuropeanlibrary.maia.converter.xml.util.XmlUtil;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Generates the XML schema for the Object Model
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @date 2 de Mai de 2011
 */
public final class EntityXsdGenerator {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Document doc = generateSchema((EntityRegistry) Class.forName(args[0]).newInstance(), (XmlFieldConverterFactory) Class.forName(args[1]).newInstance(), Class.forName(args[2]));

        Properties xmlOutputProperties = new Properties();
        xmlOutputProperties.setProperty(OutputKeys.INDENT, "yes");

        System.out.println(XmlUtil.writeDomToString(doc, xmlOutputProperties));

        XmlUtil.writeDomToFile(doc, new File("target/object-model.xsd"));
    }

    /**
     * Generates the XML schema for the Object Model
     *
     * @param registry
     * @param factory
     * @param entity
     * @return A DOM with the XML schema
     */
    public static Document generateSchema(EntityRegistry registry, XmlFieldConverterFactory factory, Class<?> entity) {
        try {
            DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element schemaEl = doc.createElement("xs:schema");
            doc.appendChild(schemaEl);
            schemaEl.setAttribute("xmlns", AbstractEntityXmlConverter.XML_NAMESPACE);
            schemaEl.setAttribute("targetNamespace", AbstractEntityXmlConverter.XML_NAMESPACE);
            schemaEl.setAttribute("xmlns:xs", "http://www.w3.org/2001/XMLSchema");
            schemaEl.setAttribute("elementFormDefault", "qualified");
            schemaEl.setAttribute("attributeFormDefault", "unqualified");
            new EntityXmlSchemaGenerator(registry, factory, entity).toXmlSchema(schemaEl, entity.getSimpleName(), 1, 1,
                    new HashSet<Class<?>>());
            return doc;
        } catch (DOMException | ParserConfigurationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
