package org.theeuropeanlibrary.maia.common.registry;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.theeuropeanlibrary.maia.common.TKey;

/**
 * This abstract class provides the basic implementation for registration of
 * keys, qualifiers and relations.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public abstract class AbstractEntityRegistry implements EntityRegistry {

    protected final Set<TKey<?, ?>> keys = new HashSet<>();

    protected final Set<Class<? extends Enum<?>>> qualifiers = new HashSet<>();

    protected final Map<TKey<?, ?>, Set<Class<? extends Enum<?>>>> validQualifiers = new HashMap<>();

    protected final Map<TKey<?, ?>, Map<TKey<?, ?>, List<Class<? extends Enum<?>>>>> validRelations = new HashMap<>();

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
