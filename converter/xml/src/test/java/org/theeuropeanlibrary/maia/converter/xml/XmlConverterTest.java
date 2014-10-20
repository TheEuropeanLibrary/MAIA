package org.theeuropeanlibrary.maia.converter.xml;

import java.util.List;
import junit.framework.Assert;
import org.junit.Test;
import org.theeuropeanlibrary.maia.common.Entity;
import org.theeuropeanlibrary.maia.common.converter.ConverterException;
import org.theeuropeanlibrary.maia.common.definitions.Record;
import org.theeuropeanlibrary.maia.common.registry.TestEntityRegistry;
import org.theeuropeanlibrary.maia.common.registry.TestQualifier;
import org.w3c.dom.Element;

/**
 * This class tests conversion from and to xml representations of a simple test
 * model.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 20.10.2014
 */
public class XmlConverterTest {

    @Test
    public void encodeDecodeRecordTest() throws ConverterException {
        RecordEntityXmlConverter conv = new RecordEntityXmlConverter(new TestXmlFieldConverterFactory());

        String id = "1";
        String test = "test";

        Record<String> mdr = new Record<>();
        mdr.setId(id);
        mdr.addValue(TestEntityRegistry.BASE, test, TestQualifier.TYPE_1);

        Element mdrEncoded = conv.encode(mdr);
        Record<String> mdrDecoded = conv.decode(mdrEncoded);

        List<Entity.QualifiedValue<String>> field = mdrDecoded.getQualifiedValues(TestEntityRegistry.BASE);
        Entity.QualifiedValue<String> decodedTest = field.get(0);

        Assert.assertEquals(id, mdr.getId());
        Assert.assertEquals(test, decodedTest.getValue());
    }
}
