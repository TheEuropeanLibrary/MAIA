package org.theeuropeanlibrary.maia.common.registry;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.theeuropeanlibrary.maia.common.TKey;

/**
 * A registry holding keys and qualifiers for specific model of an entity.
 *
 * @author Nuno Freire (nuno.freire@theeuropeanlibrary.org)
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public interface EntityRegistry {

    /**
     * Retrieves all available keys!
     *
     * @return keys
     */
    Set<TKey<?, ?>> getAvailableKeys();

    /**
     * Retrieves all available keys!
     *
     * @return keys
     */
    Set<Class<? extends Enum<?>>> getAvailableQualifiers();

    /**
     * Returns valid enums for the given key or null. Note null is supposed to
     * happen only for dynamic keys and not for the ones defined in this map, as
     * dynamic keys do not enforce only specific qualifiers.
     *
     * @param key typed key for which known qualifiers should be retrieved
     * @return valid enums for the given key or null
     */
    Set<Class<? extends Enum<?>>> getQualifiers(TKey<?, ?> key);

    /**
     * Returns valid relation targets for the given key or null. Note null is
     * supposed to happen only for dynamic keys and not for the ones defined in
     * this map, as dynamic keys do not enforce only specific relations.
     *
     * @param key typed key for which known relation targets should be retrieved
     * @return valid keys with enums for the given key or null
     */
    Map<TKey<?, ?>, List<Class<? extends Enum<?>>>> getRelationTargets(TKey<?, ?> key);
}
