package org.theeuropeanlibrary.maia.common.filter;

import java.util.Set;
import org.theeuropeanlibrary.maia.common.AbstractEntity;
import org.theeuropeanlibrary.maia.common.TKey;

/**
 *
 * @param <T> generic ID
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 10.11.2014
 */
public class BaseEntityFilter<T> implements EntityFilter<T, AbstractEntity<T>> {

    private final Set<TKey<?, ?>> filterKeys;

    public BaseEntityFilter(Set<TKey<?, ?>> filterKeys) {
        this.filterKeys = filterKeys;
    }

    @Override
    public AbstractEntity<T> filter(AbstractEntity<T> entity) {
        Set<TKey<?, ?>> keys = entity.getAvailableKeys();
        for (TKey<?, ?> key : keys) {
            if (!filterKeys.contains(key)) {
                entity.deleteValues(key);
            }
        }
        return entity;
    }

}
