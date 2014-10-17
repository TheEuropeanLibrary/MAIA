package org.theeuropeanlibrary.maia.converter.binary.definitions;

import org.theeuropeanlibrary.maia.common.FieldId;

/**
 * This class provides registration of qualifiers used for tests.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public final class TestQualifierRegistry {

    @FieldId(1)
    public static final Class<TestType> TEST_TYPE = TestType.class;
}
