package org.theeuropeanlibrary.maia.common.registry;

import java.util.HashSet;
import static org.theeuropeanlibrary.maia.common.registry.TestEntityConstants.BASE;
import static org.theeuropeanlibrary.maia.common.registry.TestEntityConstants.COMPLEX;
import static org.theeuropeanlibrary.maia.common.registry.TestEntityConstants.TEST_TYPE;

/**
 * This class provides registration of keys used for tests.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public final class TestEntityRegistry extends AbstractEntityRegistry {

    public static TestEntityRegistry INSTANCE = new TestEntityRegistry();

    private TestEntityRegistry() {
        keys.add(BASE);
        keys.add(COMPLEX);

        qualifiers.add(TEST_TYPE);

        validQualifiers.put(BASE, new HashSet<Class<? extends Enum<?>>>() {
            {
                add(TEST_TYPE);
            }
        });

        validQualifiers.put(COMPLEX, new HashSet<Class<? extends Enum<?>>>() {
            {
                add(TEST_TYPE);
            }
        });
    }
}
