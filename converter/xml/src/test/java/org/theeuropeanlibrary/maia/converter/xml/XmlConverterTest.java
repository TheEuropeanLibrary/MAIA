package org.theeuropeanlibrary.maia.converter.xml;

import java.util.List;
import junit.framework.Assert;
import org.junit.Test;
import org.theeuropeanlibrary.maia.common.Entity;
import org.theeuropeanlibrary.maia.common.converter.ConverterException;
import org.theeuropeanlibrary.maia.common.definitions.Record;
import org.theeuropeanlibrary.maia.common.registry.TestEntityConstants;
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
        String id = "1";
        String test = "test";

        Record<String> mdr = new Record<>();
        mdr.setId(id);
        mdr.addValue(TestEntityConstants.BASE, test, TestQualifier.TYPE_1);

        RecordEntityXmlConverter conv = new RecordEntityXmlConverter(new TestXmlFieldConverterFactory());

        Element mdrEncoded = conv.encode(mdr);
        Record<String> mdrDecoded = conv.decode(mdrEncoded);

        List<Entity.QualifiedValue<String>> field = mdrDecoded.getQualifiedValues(TestEntityConstants.BASE);
        Entity.QualifiedValue<String> decodedTest = field.get(0);

        Assert.assertEquals(id, mdr.getId());
        Assert.assertEquals(test, decodedTest.getValue());
    }
}
