package org.theeuropeanlibrary.maia.common.definitions;

/**
 * Implements {@link Dataset} holding all information in memory.
 *
 * @param <ID> unique ID
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public class Dataset<ID> extends AbstractEntity<ID> {

    private Provider<ID> provider;

    /**
     * @return provider
     */
    public Provider<ID> getProvider() {
        return provider;
    }

    /**
     * @param provider
     */
    public void setProvider(Provider<ID> provider) {
        this.provider = provider;
    }
}
