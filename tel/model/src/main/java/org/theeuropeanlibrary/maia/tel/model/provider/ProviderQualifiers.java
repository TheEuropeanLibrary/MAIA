package org.theeuropeanlibrary.maia.tel.model.provider;

import org.theeuropeanlibrary.maia.tel.model.provider.definitions.ProviderRelationType;
import org.theeuropeanlibrary.maia.common.FieldId;
import org.theeuropeanlibrary.maia.tel.model.common.qualifier.Language;
import org.theeuropeanlibrary.maia.tel.model.provider.definitions.ProviderIdentifierType;
import org.theeuropeanlibrary.maia.tel.model.provider.definitions.ProviderLinkType;
import org.theeuropeanlibrary.maia.tel.model.provider.definitions.ProviderNameType;

/**
 * This class holds all the keys and qualifiers defining an provider in The
 * European Library domain model
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 28.10.2014
 */
public final class ProviderQualifiers {

    private ProviderQualifiers() {
        // Constants class
    }

    @FieldId(1)
    public static final Class<Language> LANGUAGE = Language.class;

    @FieldId(2)
    public static final Class<ProviderIdentifierType> IDENTIFIER_TYPE = ProviderIdentifierType.class;

    @FieldId(3)
    public static final Class<ProviderNameType> NAME_TYPE = ProviderNameType.class;

    @FieldId(4)
    public static final Class<ProviderLinkType> LINK_TYPE = ProviderLinkType.class;

    @FieldId(5)
    public static final Class<ProviderRelationType> PROVIDER_RELATIONSHIP_TYPE = ProviderRelationType.class;
}
