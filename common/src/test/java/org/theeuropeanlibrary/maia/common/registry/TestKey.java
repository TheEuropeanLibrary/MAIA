package org.theeuropeanlibrary.maia.common.registry;

import java.util.Objects;
import org.theeuropeanlibrary.maia.common.FieldId;

/**
 * Test qualifier.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public class TestKey {

    @FieldId(1)
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

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TestKey other = (TestKey) obj;
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TestKey{" + "value=" + value + '}';
    }

}
