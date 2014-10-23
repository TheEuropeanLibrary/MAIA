package org.theeuropeanlibrary.maia.common.registry;

import org.theeuropeanlibrary.maia.common.FieldId;
import org.theeuropeanlibrary.maia.common.TKey;

/**
 * This class holds all the keys and qualifiers.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 20.10.2014
 */
public class TestEntityConstants {

    // Keys
    @FieldId(1)
    public static final TKey<AbstractEntityRegistry, String> BASE = TKey.register(
            AbstractEntityRegistry.class,
            "Base",
            String.class);

    @FieldId(2)
    public static final TKey<AbstractEntityRegistry, TestKey> COMPLEX = TKey.register(
            AbstractEntityRegistry.class,
            "Complex",
            TestKey.class);

    // Qualifiers
    @FieldId(1)
    public static final Class<TestQualifier> TEST_TYPE = TestQualifier.class;
}
