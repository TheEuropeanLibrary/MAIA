package org.theeuropeanlibrary.maia.common.definitions;

import java.util.HashSet;
import java.util.Set;

/**
 * Implements {@link Provider} holding all information in memory.
 *
 * @param <ID> unique ID
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public class Provider<ID> extends AbstractEntity<ID> {

    private final Set<Provider<ID>> relatedOut = new HashSet<>();
    private final Set<Provider<ID>> relatedIn = new HashSet<>();

    /**
     * @return retrieve providers to which this provider seems to be dependent
     * on
     */
    public Set<Provider<ID>> getRelatedOut() {
        return relatedOut;
    }

    public Set<Provider<ID>> getRelatedIn() {
        return relatedIn;
    }

}
