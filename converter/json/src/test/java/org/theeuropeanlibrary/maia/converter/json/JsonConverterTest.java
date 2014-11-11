package org.theeuropeanlibrary.maia.converter.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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

/**
 * This class tests conversion from and to xml representations of a simple test
 * model.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 20.10.2014
 */
public class JsonConverterTest {

    @Test
    public void encodeDecodeRecordTest() throws ConverterException, JsonMappingException, JsonProcessingException {
        String id = "1";
        String base = "test";
        TestKey complex = new TestKey("complex");

        Record<String> record = new Record<>();
        record.setId(id);
        record.addValue(TestEntityConstants.BASE, base, TestQualifier.TYPE_1);
        record.addValue(TestEntityConstants.COMPLEX, complex, TestQualifier.TYPE_1);

        EntityObjectMapper mapper = new EntityObjectMapper(null, null, TestEntityRegistry.INSTANCE);
        RecordEntityJsonConverter converter = new RecordEntityJsonConverter(mapper);

//        JsonSchema jsonSchema = mapper.generateJsonSchema(Record.class);
//        String schemaStr = jsonSchema.toString();
//        System.out.println(schemaStr);

        String enc = converter.encode(record);
//        System.out.println(enc);
        Record<String> mdrDecoded = converter.decode(enc);

        List<QualifiedValue<String>> baseField = mdrDecoded.getQualifiedValues(TestEntityConstants.BASE);
        QualifiedValue<String> decodedBase = baseField.get(0);

        List<QualifiedValue<TestKey>> complexField = mdrDecoded.getQualifiedValues(TestEntityConstants.COMPLEX);
        QualifiedValue<TestKey> decodedComplex = complexField.get(0);

        Assert.assertEquals(id, record.getId());
        Assert.assertEquals(base, decodedBase.getValue());
        Assert.assertEquals(complex, decodedComplex.getValue());
    }
}
