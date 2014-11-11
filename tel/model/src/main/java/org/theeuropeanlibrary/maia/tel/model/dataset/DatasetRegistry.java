package org.theeuropeanlibrary.maia.tel.model.dataset;

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
public final class DatasetRegistry extends AbstractEntityRegistry {

    public static DatasetRegistry INSTANCE = new DatasetRegistry();

    private final EntityFilter generalFilter;
    private final EntityFilter additionalFilter;
    private final EntityFilter membershipFilter;
    private final EntityFilter portalFilter;
    private final EntityFilter relationFilter;
    private final EntityFilter providerUserFilter;
    private final EntityFilter officeUserFilter;

    private DatasetRegistry() {
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
        generalKeys.add(DatasetKeys.NAME);
//        generalKeys.add(DatasetKeys.IDENTIFIER);
//        generalKeys.add(DatasetKeys.LANGUAGE);
//        generalKeys.add(DatasetKeys.COUNTRY);
//        validQualifiers.put(DatasetKeys.NAME, new HashSet<Class<? extends Enum<?>>>() {
//            {
//                add(DatasetQualifiers.NAME_TYPE);
//                add(DatasetQualifiers.LANGUAGE);
//            }
//        });
//        validQualifiers.put(DatasetKeys.IDENTIFIER, new HashSet<Class<? extends Enum<?>>>() {
//            {
//                add(DatasetQualifiers.IDENTIFIER_TYPE);
//            }
//        });
        return generalKeys;
    }

    private Set<TKey<?, ?>> setupAdditionalKeys() {
        Set<TKey<?, ?>> additionalKeys = new HashSet<>();
//        additionalKeys.add(DatasetKeys.PHONE);
//        additionalKeys.add(DatasetKeys.FAX);
//        additionalKeys.add(DatasetKeys.EMAIL);
//        additionalKeys.add(DatasetKeys.DEA);
//        additionalKeys.add(DatasetKeys.EOD);
//        additionalKeys.add(DatasetKeys.LIBRARY_ORGANIZATION);
//        additionalKeys.add(DatasetKeys.CONSORTIUM_TYPE);
//        additionalKeys.add(DatasetKeys.NOTE);
        return additionalKeys;
    }

    private Set<TKey<?, ?>> setupMembershipKeys() {
        Set<TKey<?, ?>> membershipKeys = new HashSet<>();
//        membershipKeys.add(DatasetKeys.MEMBER);
//        membershipKeys.add(DatasetKeys.MEMBERSHIP_TYPE);
        return membershipKeys;
    }

    private Set<TKey<?, ?>> setupPortalKeys() {
        Set<TKey<?, ?>> portalKeys = new HashSet<>();
//        portalKeys.add(DatasetKeys.PORTAL_STATUS);
//        portalKeys.add(DatasetKeys.LINK);
//        portalKeys.add(DatasetKeys.COORDINATE);
//        validQualifiers.put(DatasetKeys.LINK, new HashSet<Class<? extends Enum<?>>>() {
//            {
//                add(DatasetQualifiers.LINK_TYPE);
//            }
//        });
        return portalKeys;
    }

    private Set<TKey<?, ?>> setupRelationshipKeys() {
        Set<TKey<?, ?>> relationKeys = new HashSet<>();
//        relationKeys.add(DatasetKeys.PROVIDER);
//        relationKeys.add(DatasetKeys.CONTACT);
//        relationKeys.add(DatasetKeys.PROJECT);
//        relationKeys.add(DatasetKeys.CASE);
//        relationKeys.add(DatasetKeys.TICKET);
//        relationKeys.add(DatasetKeys.TASK);
//        validQualifiers.put(DatasetKeys.PROVIDER, new HashSet<Class<? extends Enum<?>>>() {
//            {
//                add(DatasetQualifiers.PROVIDER_RELATIONSHIP_TYPE);
//            }
//        });
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
