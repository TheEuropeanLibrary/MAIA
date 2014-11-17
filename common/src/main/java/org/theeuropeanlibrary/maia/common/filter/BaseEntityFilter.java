package org.theeuropeanlibrary.maia.common.filter;

import java.util.Set;
import org.theeuropeanlibrary.maia.common.AbstractEntity;
import org.theeuropeanlibrary.maia.common.TKey;

/**
 * Base class implementation using AbstractEntity as entity to be filtered.
 *
 * @param <T> generic ID
 * @param <I> generic entity
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 10.11.2014
 */
public class BaseEntityFilter<T, I extends AbstractEntity<T>> implements EntityFilter<T, I> {

    private final Set<TKey<?, ?>> filterKeys;

    public BaseEntityFilter(Set<TKey<?, ?>> filterKeys) {
        this.filterKeys = filterKeys;
    }

    @Override
    public I filter(I entity) {
        Set<TKey<?, ?>> keys = entity.getAvailableKeys();
        for (TKey<?, ?> key : keys) {
            if (!filterKeys.contains(key)) {
                entity.deleteValues(key);
            }
        }
        return entity;
    }

}
