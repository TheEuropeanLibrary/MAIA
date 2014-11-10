package org.theeuropeanlibrary.maia.common.filter;

import org.theeuropeanlibrary.maia.common.Entity;

/**
 * Filters out information for a given entity to provide a view on the data.
 *
 * @param <T> generic ID
 * @param <I> generic entity
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 10.11.2014
 */
public interface EntityFilter<T, I extends Entity<T>> {

    /**
     * @param entity original entity
     * @return entity with filtered content
     */
    I filter(I entity);
}
