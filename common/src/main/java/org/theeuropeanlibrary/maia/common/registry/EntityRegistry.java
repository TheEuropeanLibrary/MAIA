package org.theeuropeanlibrary.maia.common.registry;

import java.util.List;
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
     * @param <NS> name space
     * @param <T> generic type
     * @return keys
     */
    <NS, T> Set<TKey<NS, T>> getAvailableKeys();

    /**
     * Retrieves all available qualifiers for a given key!
     *
     * @param <NS> name space
     * @param <T> generic type
     * @param key
     * @return qualifier
     */
    <NS, T> List<Class<? extends Enum<?>>> getQualifiersForKey(TKey<NS, T> key);
}
