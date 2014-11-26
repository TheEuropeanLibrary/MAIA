package org.theeuropeanlibrary.maia.common.filter;

import java.util.List;
import java.util.Set;
import org.theeuropeanlibrary.maia.common.AbstractEntity;
import org.theeuropeanlibrary.maia.common.Entity.QualifiedValue;
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
        AbstractEntity instance = entity.createInstance();
        instance.setId(entity.getId());
        Set<TKey<?, ?>> keys = entity.getAvailableKeys();
        for (TKey<?, ?> key : keys) {
            if (filterKeys.contains(key)) {
                List<QualifiedValue<?>> qvs = (List<QualifiedValue<?>>) entity.getQualifiedValues(key);
                for (QualifiedValue<?> qv : qvs) {
                    instance.addValue(key, qv.getValue(), qv.getQualifiers().toArray(new Enum[qv.getQualifiers().size()]));
                }
            }
        }
        return (I) instance;
    }

}
