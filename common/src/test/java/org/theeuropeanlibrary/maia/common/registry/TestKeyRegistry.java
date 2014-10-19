package org.theeuropeanlibrary.maia.common.registry;

import org.theeuropeanlibrary.maia.common.FieldId;
import org.theeuropeanlibrary.maia.common.TKey;

/**
 * This class provides registration of keys used for tests.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public final class TestKeyRegistry {

    @FieldId(1)
    public static final TKey<TestKeyRegistry, String> BASE = TKey.register(
            TestKeyRegistry.class,
            "base",
            String.class);

    @FieldId(2)
    public static final TKey<TestKeyRegistry, TestKey> COMPLEX = TKey.register(
            TestKeyRegistry.class,
            "complex",
            TestKey.class);
}
