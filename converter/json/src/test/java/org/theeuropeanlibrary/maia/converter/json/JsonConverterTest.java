package org.theeuropeanlibrary.maia.converter.json;

import org.junit.Test;
import org.theeuropeanlibrary.maia.common.converter.ConverterException;
import org.theeuropeanlibrary.maia.common.definitions.Record;
import org.theeuropeanlibrary.maia.common.registry.TestEntityConstants;
import org.theeuropeanlibrary.maia.common.registry.TestKey;
import org.theeuropeanlibrary.maia.common.registry.TestQualifier;

/**
 * This class tests conversion from and to xml representations of a simple test
 * model.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 20.10.2014
 */
public class JsonConverterTest {

    @Test
    public void encodeDecodeRecordTest() throws ConverterException {
        String id = "1";
        String base = "test";
        TestKey complex = new TestKey("complex");

        Record<String> mdr = new Record<>();
        mdr.setId(id);
        mdr.addValue(TestEntityConstants.BASE, base, TestQualifier.TYPE_1);
        mdr.addValue(TestEntityConstants.COMPLEX, complex, TestQualifier.TYPE_1);

        EntityJsonSerializer<Record> serializer = new EntityJsonSerializer<Record>(TestJsonConverterFactory.INSTANCE);
        EntityJsonDeserializer<Record> deserializer = new EntityJsonDeserializer<Record>(TestJsonConverterFactory.INSTANCE);
        RecordObjectMapper mapper = new RecordObjectMapper(
                serializer, deserializer);
        RecordEntityJsonConverter converter = new RecordEntityJsonConverter(mapper);

        String enc = converter.encode(mdr);
        System.out.println(enc);
    }
}
