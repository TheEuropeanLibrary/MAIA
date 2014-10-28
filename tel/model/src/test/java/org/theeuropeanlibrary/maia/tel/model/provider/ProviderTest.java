package org.theeuropeanlibrary.maia.tel.model.provider;

import junit.framework.Assert;
import org.junit.Test;
import org.theeuropeanlibrary.maia.common.definitions.Provider;

/**
 * Basic tests for the provider based on The European Library domain model.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 28.10.2014
 */
public class ProviderTest {

    /**
     * Tests reading and writing of simple key value pairs onto a record object.
     */
    @Test
    public void readWriteTest() {
        String id = "prov_0";
        String name = "TEL";

        Provider<String> record = new Provider();
        record.setId(id);
        record.addValue(ProviderConstants.NAME, name);

        Assert.assertEquals(id, record.getId());
        Assert.assertEquals(name, record.getFirstValue(ProviderConstants.NAME));
    }
}
