package org.theeuropeanlibrary.maia.common;

import junit.framework.Assert;
import org.theeuropeanlibrary.maia.common.definitions.Record;
import org.theeuropeanlibrary.maia.common.registry.TestEntityConstants;
import org.theeuropeanlibrary.maia.common.registry.TestEntityRegistry;

/**
 * Basic tests for the record.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 20.10.2014
 */
public class RecordTest {

    /**
     * Tests reading and writing of simple key value pairs onto a record object.
     */
    public void readWriteTest() {
        String id = "ID";
        String base = "base";

        Record record = new Record();
        record.setId(id);
        record.addValue(TestEntityConstants.BASE, base);

        Assert.assertEquals(id, record.getId());
        Assert.assertEquals(base, record.getFirstValue(TestEntityConstants.BASE));
    }
}
