package org.theeuropeanlibrary.maia.tel.model.provider;

import java.util.HashSet;
import java.util.Set;
import org.theeuropeanlibrary.maia.common.TKey;
import org.theeuropeanlibrary.maia.common.filter.BaseEntityFilter;
import org.theeuropeanlibrary.maia.common.filter.EntityFilter;
import org.theeuropeanlibrary.maia.common.registry.AbstractEntityRegistry;

/**
 * This class provides registration of keys for the provider in The European
 * Library domain model.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 28.10.2014
 */
public final class ProviderRegistry extends AbstractEntityRegistry {

    public static ProviderRegistry INSTANCE = new ProviderRegistry();

    private final EntityFilter generalFilter;
    private final EntityFilter additionalFilter;
    private final EntityFilter membershipFilter;
    private final EntityFilter portalFilter;
    private final EntityFilter relationFilter;
    private final EntityFilter providerUserFilter;
    private final EntityFilter officeUserFilter;

    private ProviderRegistry() {
        Set<TKey<?, ?>> generalKeys = setupGeneralKeys();
        generalFilter = new BaseEntityFilter(generalKeys);

        Set<TKey<?, ?>> additionalKeys = setupAdditionalKeys();
        additionalFilter = new BaseEntityFilter(additionalKeys);

        Set<TKey<?, ?>> membershipKeys = setupMembershipKeys();
        membershipFilter = new BaseEntityFilter(membershipKeys);

        Set<TKey<?, ?>> portalKeys = setupPortalKeys();
        portalFilter = new BaseEntityFilter(portalKeys);

        Set<TKey<?, ?>> relationKeys = setupRelationshipKeys();
        relationFilter = new BaseEntityFilter(relationKeys);

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
        providerUserFilter = new BaseEntityFilter(providerUserKeys);

        Set<TKey<?, ?>> officeUserKeys = new HashSet<>();
        officeUserKeys.addAll(generalKeys);
        officeUserKeys.addAll(additionalKeys);
        officeUserKeys.addAll(membershipKeys);
        officeUserKeys.addAll(portalKeys);
        officeUserKeys.addAll(relationKeys);
        officeUserFilter = new BaseEntityFilter(officeUserKeys);
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

    public EntityFilter getGeneralFilter() {
        return generalFilter;
    }

    public EntityFilter getAdditionalFilter() {
        return additionalFilter;
    }

    public EntityFilter getMembershipFilter() {
        return membershipFilter;
    }

    public EntityFilter getPortalFilter() {
        return portalFilter;
    }

    public EntityFilter getRelationFilter() {
        return relationFilter;
    }

    public EntityFilter getProviderUserFilter() {
        return providerUserFilter;
    }

    public EntityFilter getOfficeUserFilter() {
        return officeUserFilter;
    }

}
