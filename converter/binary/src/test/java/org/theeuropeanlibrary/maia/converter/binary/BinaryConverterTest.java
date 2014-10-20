package org.theeuropeanlibrary.maia.converter.binary;

import java.util.List;
import junit.framework.Assert;
import org.junit.Test;
import org.theeuropeanlibrary.maia.common.converter.ConverterException;
import org.theeuropeanlibrary.maia.common.Entity.QualifiedValue;
import org.theeuropeanlibrary.maia.common.definitions.Record;
import org.theeuropeanlibrary.maia.common.registry.TestEntityConstants;
import org.theeuropeanlibrary.maia.common.registry.TestQualifier;

/**
 * This class tests conversion from and to binary format.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public class BinaryConverterTest {

    @Test
    public void encodeDecodeRecordTest() throws ConverterException {
        RecordEntityBinaryConverter conv = new RecordEntityBinaryConverter(new TestBinaryConverterFactory());

        String id = "1";
        String test = "test";

        Record<String> mdr = new Record<>();
        mdr.setId(id);
        mdr.addValue(TestEntityConstants.BASE, test, TestQualifier.TYPE_1);

        byte[] mdrEncoded = conv.encode(mdr);
        Record<String> mdrDecoded = conv.decode(mdrEncoded);

        List<QualifiedValue<String>> field = mdrDecoded.getQualifiedValues(TestEntityConstants.BASE);
        QualifiedValue<String> decodedTest = field.get(0);

        Assert.assertEquals(id, mdr.getId());
        Assert.assertEquals(test, decodedTest.getValue());
    }
}