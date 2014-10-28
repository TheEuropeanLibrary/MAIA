package org.theeuropeanlibrary.maia.tel.model.provider;

import org.theeuropeanlibrary.maia.common.registry.AbstractEntityRegistry;
import static org.theeuropeanlibrary.maia.tel.model.provider.ProviderConstants.NAME;

/**
 * This class provides registration of keys for the provider in The European
 * Library domain model.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 28o.10.2014
 */
public final class ProviderRegistry extends AbstractEntityRegistry {

    public static ProviderRegistry INSTANCE = new ProviderRegistry();

    private ProviderRegistry() {
        keys.add(NAME);
    }
}
