package org.theeuropeanlibrary.maia.common.registry;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.theeuropeanlibrary.maia.common.TKey;
import static org.theeuropeanlibrary.maia.common.registry.TestEntityConstants.BASE;
import static org.theeuropeanlibrary.maia.common.registry.TestEntityConstants.COMPLEX;
import static org.theeuropeanlibrary.maia.common.registry.TestEntityConstants.TEST_TYPE;

/**
 * This class provides registration of keys used for tests.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public final class TestEntityRegistry implements EntityRegistry {

    public static TestEntityRegistry INSTANCE = new TestEntityRegistry();

    private final Set<TKey<?, ?>> keys = new HashSet<>();

    private final Set<Class<? extends Enum<?>>> qualifiers = new HashSet<>();

    private final Map<TKey<?, ?>, Set<Class<? extends Enum<?>>>> validQualifiers = new HashMap<>();

    private final Map<TKey<?, ?>, Map<TKey<?, ?>, List<Class<? extends Enum<?>>>>> validRelations = new HashMap<>();

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

    @Override
    public Set<TKey<?, ?>> getAvailableKeys() {
        return Collections.unmodifiableSet(keys);
    }

    @Override
    public Set<Class<? extends Enum<?>>> getAvailableQualifiers() {
        return Collections.unmodifiableSet(qualifiers);
    }

    @Override
    public Set<Class<? extends Enum<?>>> getQualifiers(TKey<?, ?> key) {
        return validQualifiers.get(key);
    }

    @Override
    public Map<TKey<?, ?>, List<Class<? extends Enum<?>>>> getRelationTargets(TKey<?, ?> key) {
        return validRelations.get(key);
    }
}
