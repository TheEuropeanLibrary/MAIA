package org.theeuropeanlibrary.maia.tel.model.provider;

import java.util.HashSet;
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

        Set<TKey<?, ?>> providerUserKeys = new HashSet<>();
        providerUserKeys.addAll(generalKeys);
        providerUserKeys.addAll(additionalKeys);
        providerUserKeys.addAll(membershipKeys);
        providerUserKeys.addAll(portalKeys);
        providerUserKeys.addAll(relationKeys);
        EntityFilter providerUserFilter = new BaseEntityFilter(providerUserKeys);
        filterFactory.registerFilter("provider", providerUserFilter);

        Set<TKey<?, ?>> officeUserKeys = new HashSet<>();
        officeUserKeys.addAll(generalKeys);
        officeUserKeys.addAll(additionalKeys);
        officeUserKeys.addAll(membershipKeys);
        officeUserKeys.addAll(portalKeys);
        officeUserKeys.addAll(relationKeys);
        EntityFilter officeUserFilter = new BaseEntityFilter(officeUserKeys);
        filterFactory.registerFilter("office", officeUserFilter);
    }

    private Set<TKey<?, ?>> setupGeneralKeys() {
        Set<TKey<?, ?>> generalKeys = new HashSet<>();
        generalKeys.add(ProviderKeys.NAME);
        generalKeys.add(ProviderKeys.IDENTIFIER);
        generalKeys.add(ProviderKeys.LANGUAGE);
        generalKeys.add(ProviderKeys.COUNTRY);
        validQualifiers.put(ProviderKeys.NAME, new HashSet<Class<? extends Enum<?>>>() {
            {
                add(ProviderQualifiers.NAME_TYPE);
                add(ProviderQualifiers.LANGUAGE);
            }
        });
        validQualifiers.put(ProviderKeys.IDENTIFIER, new HashSet<Class<? extends Enum<?>>>() {
            {
                add(ProviderQualifiers.IDENTIFIER_TYPE);
            }
        });
        return generalKeys;
    }

    private Set<TKey<?, ?>> setupAdditionalKeys() {
        Set<TKey<?, ?>> additionalKeys = new HashSet<>();
        additionalKeys.add(ProviderKeys.PHONE);
        additionalKeys.add(ProviderKeys.FAX);
        additionalKeys.add(ProviderKeys.EMAIL);
        additionalKeys.add(ProviderKeys.DEA);
        additionalKeys.add(ProviderKeys.EOD);
        additionalKeys.add(ProviderKeys.LIBRARY_ORGANIZATION);
        additionalKeys.add(ProviderKeys.CONSORTIUM_TYPE);
        additionalKeys.add(ProviderKeys.NOTE);
        return additionalKeys;
    }

    private Set<TKey<?, ?>> setupMembershipKeys() {
        Set<TKey<?, ?>> membershipKeys = new HashSet<>();
        membershipKeys.add(ProviderKeys.MEMBER);
//        membershipKeys.add(ProviderKeys.MEMBERSHIP_TYPE);
        return membershipKeys;
    }

    private Set<TKey<?, ?>> setupPortalKeys() {
        Set<TKey<?, ?>> portalKeys = new HashSet<>();
        portalKeys.add(ProviderKeys.PORTAL_STATUS);
        portalKeys.add(ProviderKeys.LINK);
        portalKeys.add(ProviderKeys.COORDINATE);
        validQualifiers.put(ProviderKeys.LINK, new HashSet<Class<? extends Enum<?>>>() {
            {
                add(ProviderQualifiers.LINK_TYPE);
            }
        });
        return portalKeys;
    }

    private Set<TKey<?, ?>> setupRelationshipKeys() {
        Set<TKey<?, ?>> relationKeys = new HashSet<>();
        relationKeys.add(ProviderKeys.PROVIDER);
        relationKeys.add(ProviderKeys.CONTACT);
        relationKeys.add(ProviderKeys.PROJECT);
        relationKeys.add(ProviderKeys.CASE);
        relationKeys.add(ProviderKeys.TICKET);
        relationKeys.add(ProviderKeys.TASK);
        validQualifiers.put(ProviderKeys.PROVIDER, new HashSet<Class<? extends Enum<?>>>() {
            {
                add(ProviderQualifiers.PROVIDER_RELATIONSHIP_TYPE);
            }
        });
        return relationKeys;
    }

    public EntityFilterFactory<String, Provider<String>> getFilterFactory() {
        return filterFactory;
    }

    public static synchronized ProviderRegistry getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProviderRegistry();
        }
        return INSTANCE;
    }
}
