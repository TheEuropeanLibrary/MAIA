package org.theeuropeanlibrary.maia.common.registry;

/**
 * Test qualifier.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public class TestKey {

    private String value;

    public TestKey() {
        // nothing to do
    }

    public TestKey(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
