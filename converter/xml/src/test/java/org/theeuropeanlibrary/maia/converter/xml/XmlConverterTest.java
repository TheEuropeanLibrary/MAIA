package org.theeuropeanlibrary.maia.converter.xml;

import java.util.List;
import junit.framework.Assert;
import org.junit.Test;
import org.theeuropeanlibrary.maia.common.Entity.QualifiedValue;
import org.theeuropeanlibrary.maia.common.converter.ConverterException;
import org.theeuropeanlibrary.maia.common.definitions.Record;
import org.theeuropeanlibrary.maia.common.registry.TestEntityConstants;
import org.theeuropeanlibrary.maia.common.registry.TestEntityRegistry;
import org.theeuropeanlibrary.maia.common.registry.TestKey;
import org.theeuropeanlibrary.maia.common.registry.TestQualifier;
import org.theeuropeanlibrary.maia.converter.xml.factory.BaseXmlFieldConverterFactory;
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
        String base = "test";
        TestKey complex = new TestKey("complex");

        Record<String> mdr = new Record<>();
        mdr.setId(id);
        mdr.addValue(TestEntityConstants.BASE, base, TestQualifier.TYPE_1);
        mdr.addValue(TestEntityConstants.COMPLEX, complex, TestQualifier.TYPE_1);

        RecordEntityXmlConverter conv = new RecordEntityXmlConverter(new BaseXmlFieldConverterFactory(TestEntityRegistry.INSTANCE));

        Element mdrEncoded = conv.encode(mdr);
//        System.out.println(XmlUtil.writeDomToString(mdrEncoded));
        Record<String> mdrDecoded = conv.decode(mdrEncoded);

        List<QualifiedValue<String>> baseField = mdrDecoded.getQualifiedValues(TestEntityConstants.BASE);
        QualifiedValue<String> decodedBase = baseField.get(0);

        List<QualifiedValue<TestKey>> complexField = mdrDecoded.getQualifiedValues(TestEntityConstants.COMPLEX);
        QualifiedValue<TestKey> decodedComplex = complexField.get(0);

        Assert.assertEquals(id, mdr.getId());
        Assert.assertEquals(base, decodedBase.getValue());
        Assert.assertEquals(complex, decodedComplex.getValue());
    }
}
