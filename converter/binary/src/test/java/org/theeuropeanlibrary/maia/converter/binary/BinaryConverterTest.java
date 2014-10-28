package org.theeuropeanlibrary.maia.converter.binary;

import java.util.List;
import junit.framework.Assert;
import org.junit.Test;
import org.theeuropeanlibrary.maia.common.converter.ConverterException;
import org.theeuropeanlibrary.maia.common.Entity.QualifiedValue;
import org.theeuropeanlibrary.maia.common.definitions.Record;
import org.theeuropeanlibrary.maia.common.registry.TestEntityConstants;
import org.theeuropeanlibrary.maia.common.registry.TestKey;
import org.theeuropeanlibrary.maia.common.registry.TestQualifier;
import org.theeuropeanlibrary.maia.converter.binary.factory.BaseBinaryConverterFactory;

/**
 * This class tests conversion from and to binary format.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public class BinaryConverterTest {

    @Test
    public void encodeDecodeRecordTest() throws ConverterException {
        String id = "1";
        String base = "test";
        TestKey complex = new TestKey("complex");

        Record<String> mdr = new Record<>();
        mdr.setId(id);
        mdr.addValue(TestEntityConstants.BASE, base, TestQualifier.TYPE_1);
        mdr.addValue(TestEntityConstants.COMPLEX, complex, TestQualifier.TYPE_1);

        RecordEntityBinaryConverter conv = new RecordEntityBinaryConverter(new BaseBinaryConverterFactory(TestEntityConstants.class));

        byte[] mdrEncoded = conv.encode(mdr);
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
