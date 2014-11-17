package org.theeuropeanlibrary.maia.common.filter;

import java.util.HashMap;
import java.util.Map;
import org.theeuropeanlibrary.maia.common.AbstractEntity;

/**
 * Base class implementation for EntityFilterFactory.
 *
 * @param <T> generic ID
 * @param <I> generic entity
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 10.11.2014
 */
public class BaseEntityFilterFactory<T, I extends AbstractEntity<T>> implements EntityFilterFactory<T, I> {

    private final Map<String, EntityFilter<T, I>> registeredFilters;

    public BaseEntityFilterFactory() {
        registeredFilters = new HashMap<>();
    }

    @Override
    public void registerFilter(String name, EntityFilter<T, I> filter) {
        registeredFilters.put(name, filter);
    }

    @Override
    public void unregisterFilter(String name) {
        registeredFilters.remove(name);
    }

    @Override
    public EntityFilter<T, I> getFilterForName(String name) {
        return registeredFilters.get(name);
    }

}
