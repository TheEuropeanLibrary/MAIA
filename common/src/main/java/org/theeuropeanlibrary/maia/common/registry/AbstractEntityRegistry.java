package org.theeuropeanlibrary.maia.common.registry;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
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

    protected final Set<TKey<?, ?>> keys = new LinkedHashSet<>();

    protected final Set<Class<? extends Enum<?>>> qualifiers = new LinkedHashSet<>();

    protected final Map<TKey<?, ?>, Set<Class<? extends Enum<?>>>> validQualifiers = new LinkedHashMap<>();

    protected final Map<TKey<?, ?>, Map<TKey<?, ?>, List<Class<? extends Enum<?>>>>> validRelations = new LinkedHashMap<>();

    protected final Set<TKey<?, ?>> uniqueKeys = new LinkedHashSet<>();

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

    @Override
    public boolean isUniqueValueKey(TKey<?, ?> key) {
        return uniqueKeys.contains(key);
    }
}
