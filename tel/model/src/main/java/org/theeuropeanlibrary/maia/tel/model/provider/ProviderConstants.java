package org.theeuropeanlibrary.maia.tel.model.provider;

import org.theeuropeanlibrary.maia.common.FieldId;
import org.theeuropeanlibrary.maia.common.TKey;

/**
 * This class holds all the keys and qualifiers defining an provider in The
 * European Library domain model
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 28.10.2014
 */
public class ProviderConstants {

    // Keys
    @FieldId(1)
    public static final TKey<ProviderConstants, String> NAME = TKey.register(
            ProviderConstants.class,
            "Name",
            String.class);

    // Qualifiers
}
