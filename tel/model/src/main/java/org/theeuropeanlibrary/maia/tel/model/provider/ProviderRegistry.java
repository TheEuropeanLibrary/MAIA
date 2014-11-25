package org.theeuropeanlibrary.maia.tel.model.provider;

import java.util.LinkedHashSet;
import java.util.Set;
import org.theeuropeanlibrary.maia.common.TKey;
import org.theeuropeanlibrary.maia.common.definitions.Provider;
import org.theeuropeanlibrary.maia.common.filter.BaseEntityFilter;
import org.theeuropeanlibrary.maia.common.filter.BaseEntityFilterFactory;
import org.theeuropeanlibrary.maia.common.filter.EntityFilter;
import org.theeuropeanlibrary.maia.common.filter.EntityFilterFactory;
import org.theeuropeanlibrary.maia.common.registry.AbstractEntityRegistry;

/**
 * This class provides registration of keys for the provider in The European
 * Library domain model.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 28.10.2014
 */
public final class ProviderRegistry extends AbstractEntityRegistry {

    private static ProviderRegistry INSTANCE;

    private final EntityFilterFactory<String, Provider<String>> filterFactory;

    private ProviderRegistry() {
        this(new BaseEntityFilterFactory());
    }

    private ProviderRegistry(EntityFilterFactory<String, Provider<String>> filterFactory) {
        INSTANCE = this;

        this.filterFactory = filterFactory;

        Set<TKey<?, ?>> generalKeys = setupGeneralKeys();
        EntityFilter generalFilter = new BaseEntityFilter(generalKeys);
        filterFactory.registerFilter("general", generalFilter);

        Set<TKey<?, ?>> additionalKeys = setupAdditionalKeys();
        EntityFilter additionalFilter = new BaseEntityFilter(additionalKeys);
        filterFactory.registerFilter("additional", additionalFilter);

        Set<TKey<?, ?>> membershipKeys = setupMembershipKeys();
        EntityFilter membershipFilter = new BaseEntityFilter(membershipKeys);
        filterFactory.registerFilter("membership", membershipFilter);

        Set<TKey<?, ?>> portalKeys = setupPortalKeys();
        EntityFilter portalFilter = new BaseEntityFilter(portalKeys);
        filterFactory.registerFilter("portal", portalFilter);

        Set<TKey<?, ?>> relationKeys = setupRelationshipKeys();
        EntityFilter relationFilter = new BaseEntityFilter(relationKeys);
        filterFactory.registerFilter("relation", relationFilter);

        keys.addAll(generalKeys);
        keys.addAll(additionalKeys);
        keys.addAll(membershipKeys);
        keys.addAll(portalKeys);
        keys.addAll(relationKeys);

        Set<TKey<?, ?>> providerUserKeys = new LinkedHashSet<>();
        providerUserKeys.addAll(generalKeys);
        providerUserKeys.addAll(additionalKeys);
        providerUserKeys.addAll(membershipKeys);
        providerUserKeys.addAll(portalKeys);
        providerUserKeys.addAll(relationKeys);
        EntityFilter providerUserFilter = new BaseEntityFilter(providerUserKeys);
        filterFactory.registerFilter("provider", providerUserFilter);

        Set<TKey<?, ?>> officeUserKeys = new LinkedHashSet<>();
        officeUserKeys.addAll(generalKeys);
        officeUserKeys.addAll(additionalKeys);
        officeUserKeys.addAll(membershipKeys);
        officeUserKeys.addAll(portalKeys);
        officeUserKeys.addAll(relationKeys);
        EntityFilter officeUserFilter = new BaseEntityFilter(officeUserKeys);
        filterFactory.registerFilter("office", officeUserFilter);
    }

    private Set<TKey<?, ?>> setupGeneralKeys() {
        Set<TKey<?, ?>> generalKeys = new LinkedHashSet<>();

        generalKeys.add(ProviderKeys.ORGANIZATION_TYPE);
        generalKeys.add(ProviderKeys.COUNTRY);
        generalKeys.add(ProviderKeys.IDENTIFIER);
        generalKeys.add(ProviderKeys.LANGUAGE);
        generalKeys.add(ProviderKeys.NAME);

        validQualifiers.put(ProviderKeys.NAME, new LinkedHashSet<Class<? extends Enum<?>>>() {
            {
                add(ProviderQualifiers.NAME_TYPE);
                add(ProviderQualifiers.LANGUAGE);
            }
        });

        qualifiers.add(ProviderQualifiers.NAME_TYPE);
        qualifiers.add(ProviderQualifiers.LANGUAGE);

        uniqueKeys.add(ProviderKeys.ORGANIZATION_TYPE);
        uniqueKeys.add(ProviderKeys.COUNTRY);
        uniqueKeys.add(ProviderKeys.IDENTIFIER);
        
        return generalKeys;
    }

    private Set<TKey<?, ?>> setupAdditionalKeys() {
        Set<TKey<?, ?>> additionalKeys = new LinkedHashSet<>();

        additionalKeys.add(ProviderKeys.PHONE);
        additionalKeys.add(ProviderKeys.FAX);
        additionalKeys.add(ProviderKeys.EMAIL);
        additionalKeys.add(ProviderKeys.DEA);
        additionalKeys.add(ProviderKeys.EOD);
        additionalKeys.add(ProviderKeys.LIBRARY_ORGANIZATION);
        additionalKeys.add(ProviderKeys.CONSORTIUM_TYPE);
        additionalKeys.add(ProviderKeys.NOTE);

        uniqueKeys.add(ProviderKeys.DEA);
        uniqueKeys.add(ProviderKeys.EOD);
        uniqueKeys.add(ProviderKeys.CONSORTIUM_TYPE);

        return additionalKeys;
    }

    private Set<TKey<?, ?>> setupMembershipKeys() {
        Set<TKey<?, ?>> membershipKeys = new LinkedHashSet<>();

        membershipKeys.add(ProviderKeys.MEMBER);
        membershipKeys.add(ProviderKeys.MEMBERSHIP_TYPE);

        uniqueKeys.add(ProviderKeys.MEMBER);
        uniqueKeys.add(ProviderKeys.MEMBERSHIP_TYPE);

        return membershipKeys;
    }

    private Set<TKey<?, ?>> setupPortalKeys() {
        Set<TKey<?, ?>> portalKeys = new LinkedHashSet<>();

        portalKeys.add(ProviderKeys.PORTAL_STATUS);
        portalKeys.add(ProviderKeys.COORDINATE);
        portalKeys.add(ProviderKeys.LINK);

        validQualifiers.put(ProviderKeys.LINK, new LinkedHashSet<Class<? extends Enum<?>>>() {
            {
                add(ProviderQualifiers.LINK_TYPE);
            }
        });

        qualifiers.add(ProviderQualifiers.LINK_TYPE);

        uniqueKeys.add(ProviderKeys.PORTAL_STATUS);
        uniqueKeys.add(ProviderKeys.COORDINATE);
        
        return portalKeys;
    }

    private Set<TKey<?, ?>> setupRelationshipKeys() {
        Set<TKey<?, ?>> relationKeys = new LinkedHashSet<>();

        relationKeys.add(ProviderKeys.PROVIDER);
        relationKeys.add(ProviderKeys.DATASET);
        relationKeys.add(ProviderKeys.CONTACT);
        relationKeys.add(ProviderKeys.PROJECT);
        relationKeys.add(ProviderKeys.CASE);
        relationKeys.add(ProviderKeys.TICKET);
        relationKeys.add(ProviderKeys.TASK);
        
        validQualifiers.put(ProviderKeys.PROVIDER, new LinkedHashSet<Class<? extends Enum<?>>>() {
            {
                add(ProviderQualifiers.PROVIDER_RELATIONSHIP_TYPE);
            }
        });
        
        qualifiers.add(ProviderQualifiers.PROVIDER_RELATIONSHIP_TYPE);
        
        return relationKeys;
    }

    public EntityFilterFactory<String, Provider<String>> getFilterFactory() {
        return filterFactory;
    }

    public static synchronized ProviderRegistry getInstance() {
        if (INSTANCE == null) {
            new ProviderRegistry();
        }
        return INSTANCE;
    }
}
