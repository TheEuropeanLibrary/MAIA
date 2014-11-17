package org.theeuropeanlibrary.maia.common.filter;

import org.theeuropeanlibrary.maia.common.Entity;

/**
 * Factory to get a certain entity for a name.
 *
 * @param <T> generic ID
 * @param <I> generic entity
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 10.11.2014
 */
public interface EntityFilterFactory<T, I extends Entity<T>> {

    void registerFilter(String name, EntityFilter<T, I> filter);

    void unregisterFilter(String name);

    EntityFilter<T, I> getFilterForName(String name);
}
