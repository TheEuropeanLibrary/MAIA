/* ConverterTest.java - created on Mar 22, 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.tel.model.provider;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import junit.framework.Assert;

import org.junit.Test;
import org.theeuropeanlibrary.maia.common.definitions.Provider;
import org.theeuropeanlibrary.maia.converter.xml.ProviderEntityXmlConverter;
import org.theeuropeanlibrary.maia.converter.xml.factory.BaseXmlFieldConverterFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import org.theeuropeanlibrary.maia.converter.xml.schema.EntityXsdGenerator;
import org.theeuropeanlibrary.maia.converter.xml.util.XmlUtil;
import org.w3c.dom.Element;

/**
 * Tests the schema creation of the provider model.
 *
 * @author Nuno Freire <nfreire@gmail.com>
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 28.10.2014
 */
public class ProviderXsdGenerationTest {

    private final Schema objModelSchema;

    /**
     * Creates a new instance of this class.
     *
     * @throws Exception
     */
    public ProviderXsdGenerationTest() throws Exception {
        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        Document schemaDom = EntityXsdGenerator.generateSchema(ProviderRegistry.INSTANCE, new BaseXmlFieldConverterFactory(ProviderRegistry.INSTANCE), Provider.class);

        // this writing and reading to/from string fixes some namespace issues on the generated dom
        String schema = XmlUtil.writeDomToString(schemaDom);
        System.out.println(schema);
        schemaDom = XmlUtil.parseDom(new StringReader(schema));
        objModelSchema = factory.newSchema(new DOMSource(schemaDom));
        Assert.assertNotNull(objModelSchema);
    }

    /**
     * Schema validation of test xml.
     *
     * @throws Exception
     */
    @Test
    public void testValidation() throws Exception {
        String id = "prov_0";
        String name = "TEL";

        Provider<String> provider = new Provider<>();
        provider.setId(id);
        provider.addValue(ProviderKeys.NAME, name);

        ProviderEntityXmlConverter conv = new ProviderEntityXmlConverter(new BaseXmlFieldConverterFactory(ProviderRegistry.INSTANCE));

        Element element = conv.encode(provider);
        String xml = XmlUtil.writeDomToString(element);
        System.out.println(xml);
        Document doc = XmlUtil.parseDom(new StringReader(xml));

        try {
            objModelSchema.newValidator().validate(new DOMSource(doc));
        } catch (SAXException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
