/* ConverterTest.java - created on Mar 22, 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.xml;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import junit.framework.Assert;

import org.junit.Test;
import org.theeuropeanlibrary.maia.common.definitions.Record;
import org.theeuropeanlibrary.maia.common.registry.TestEntityConstants;
import org.theeuropeanlibrary.maia.common.registry.TestEntityRegistry;
import org.theeuropeanlibrary.maia.common.registry.TestKey;
import org.theeuropeanlibrary.maia.common.registry.TestQualifier;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import org.theeuropeanlibrary.maia.converter.xml.schema.EntityXsdGenerator;
import org.theeuropeanlibrary.maia.converter.xml.util.XmlUtil;
import org.w3c.dom.Element;

/**
 * Tests the schema creation of the test model.
 *
 * @author Nuno Freire <nfreire@gmail.com>
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 20.10.2014
 */
public class XsdTest {

    private final Schema objModelSchema;

    /**
     * Creates a new instance of this class.
     *
     * @throws Exception
     */
    public XsdTest() throws Exception {
        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        Document schemaDom = EntityXsdGenerator.generateSchema(TestEntityRegistry.INSTANCE, TestXmlFieldConverterFactory.INSTANCE, "Test");

        // this writing and reading to/from string fixes some namespace issues on the generated dom
        String schema = XmlUtil.writeDomToString(schemaDom);
//        System.out.println(schema);
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
        String id = "1";
        String base = "test";
        TestKey complex = new TestKey("complex");

        Record<String> record = new Record<>();
        record.setId(id);
        record.addValue(TestEntityConstants.BASE, base, TestQualifier.TYPE_1);
        record.addValue(TestEntityConstants.COMPLEX, complex, TestQualifier.TYPE_1);

        RecordEntityXmlConverter conv = new RecordEntityXmlConverter(TestXmlFieldConverterFactory.INSTANCE);

        Element element = conv.encode(record);
        String xml = XmlUtil.writeDomToString(element);
//        System.out.println(xml);
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
