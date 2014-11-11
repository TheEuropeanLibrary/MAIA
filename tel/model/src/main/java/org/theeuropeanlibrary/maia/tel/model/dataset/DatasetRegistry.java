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
    private final EntityFilter ingestionFilter;
    private final EntityFilter controllingFilter;
    private final EntityFilter redistributeFilter;
    private final EntityFilter aggregationFilter;
    private final EntityFilter portalFilter;
    private final EntityFilter relationFilter;
    private final EntityFilter providerUserFilter;
    private final EntityFilter officeUserFilter;

    private DatasetRegistry() {
        Set<TKey<?, ?>> generalKeys = setupGeneralKeys();
        generalFilter = new BaseEntityFilter(generalKeys);

        Set<TKey<?, ?>> additionalKeys = setupAdditionalKeys();
        additionalFilter = new BaseEntityFilter(additionalKeys);

        Set<TKey<?, ?>> ingestionKeys = setupIngestionKeys();
        ingestionFilter = new BaseEntityFilter(ingestionKeys);

        Set<TKey<?, ?>> controllingKeys = setupControllingKeys();
        controllingFilter = new BaseEntityFilter(controllingKeys);

        Set<TKey<?, ?>> redistributeKeys = setupRedistributeKeys();
        redistributeFilter = new BaseEntityFilter(redistributeKeys);

        Set<TKey<?, ?>> aggregationKeys = setupAggregationKeys();
        aggregationFilter = new BaseEntityFilter(aggregationKeys);

        Set<TKey<?, ?>> portalKeys = setupPortalKeys();
        portalFilter = new BaseEntityFilter(portalKeys);

        Set<TKey<?, ?>> relationKeys = setupRelationshipKeys();
        relationFilter = new BaseEntityFilter(relationKeys);

        keys.addAll(generalKeys);
        keys.addAll(additionalKeys);
        keys.addAll(ingestionKeys);
        keys.addAll(controllingKeys);
        keys.addAll(redistributeKeys);
        keys.addAll(aggregationKeys);
        keys.addAll(portalKeys);
        keys.addAll(relationKeys);

        Set<TKey<?, ?>> providerUserKeys = new HashSet<>();
        providerUserKeys.addAll(generalKeys);
        providerUserKeys.addAll(additionalKeys);
        providerUserKeys.addAll(ingestionKeys);
        providerUserKeys.addAll(controllingKeys);
        providerUserKeys.addAll(redistributeKeys);
        providerUserKeys.addAll(aggregationKeys);
        providerUserKeys.addAll(portalKeys);
        providerUserKeys.addAll(relationKeys);
        providerUserFilter = new BaseEntityFilter(providerUserKeys);

        Set<TKey<?, ?>> officeUserKeys = new HashSet<>();
        officeUserKeys.addAll(generalKeys);
        officeUserKeys.addAll(additionalKeys);
        officeUserKeys.addAll(ingestionKeys);
        officeUserKeys.addAll(controllingKeys);
        officeUserKeys.addAll(redistributeKeys);
        officeUserKeys.addAll(aggregationKeys);
        officeUserKeys.addAll(portalKeys);
        officeUserKeys.addAll(relationKeys);
        officeUserFilter = new BaseEntityFilter(officeUserKeys);
    }

    private Set<TKey<?, ?>> setupGeneralKeys() {
        Set<TKey<?, ?>> generalKeys = new HashSet<>();
        generalKeys.add(DatasetKeys.NAME);
        generalKeys.add(DatasetKeys.IDENTIFIER);
        generalKeys.add(DatasetKeys.LANGUAGE);
        generalKeys.add(DatasetKeys.COUNTRY);
        generalKeys.add(DatasetKeys.STATUS);
        generalKeys.add(DatasetKeys.DATASET_TYPE);
        validQualifiers.put(DatasetKeys.NAME, new HashSet<Class<? extends Enum<?>>>() {
            {
                add(DatasetQualifiers.NAME_TYPE);
                add(DatasetQualifiers.LANGUAGE);
            }
        });
        validQualifiers.put(DatasetKeys.IDENTIFIER, new HashSet<Class<? extends Enum<?>>>() {
            {
                add(DatasetQualifiers.IDENTIFIER_TYPE);
            }
        });
        return generalKeys;
    }

    private Set<TKey<?, ?>> setupAdditionalKeys() {
        Set<TKey<?, ?>> additionalKeys = new HashSet<>();
        additionalKeys.add(DatasetKeys.STATUS_INFO);
        additionalKeys.add(DatasetKeys.DIGITISED_TYPE);
        additionalKeys.add(DatasetKeys.DISCIPLINE);
        additionalKeys.add(DatasetKeys.NOTE);
        additionalKeys.add(DatasetKeys.LINK);
        validQualifiers.put(DatasetKeys.NOTE, new HashSet<Class<? extends Enum<?>>>() {
            {
                add(DatasetQualifiers.NOTE_TYPE);
                add(DatasetQualifiers.LANGUAGE);
            }
        });
        validQualifiers.put(DatasetKeys.LINK, new HashSet<Class<? extends Enum<?>>>() {
            {
                add(DatasetQualifiers.LINK_TYPE);
            }
        });
        return additionalKeys;
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

    private Set<TKey<?, ?>> setupIngestionKeys() {
        Set<TKey<?, ?>> ingestionKeys = new HashSet<>();
//        ingestionKeys.add(DatasetKeys.MEMBER);
//        ingestionKeys.add(DatasetKeys.MEMBERSHIP_TYPE);
        return ingestionKeys;
    }

    private Set<TKey<?, ?>> setupControllingKeys() {
        Set<TKey<?, ?>> controllingKeys = new HashSet<>();
//        ingestionKeys.add(DatasetKeys.MEMBER);
//        ingestionKeys.add(DatasetKeys.MEMBERSHIP_TYPE);
        return controllingKeys;
    }

    private Set<TKey<?, ?>> setupRedistributeKeys() {
        Set<TKey<?, ?>> redistributeKeys = new HashSet<>();
//        ingestionKeys.add(DatasetKeys.MEMBER);
//        ingestionKeys.add(DatasetKeys.MEMBERSHIP_TYPE);
        return redistributeKeys;
    }

    private Set<TKey<?, ?>> setupAggregationKeys() {
        Set<TKey<?, ?>> aggregationKeys = new HashSet<>();
//        ingestionKeys.add(DatasetKeys.MEMBER);
//        ingestionKeys.add(DatasetKeys.MEMBERSHIP_TYPE);
        return aggregationKeys;
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

    public EntityFilter getIngestionFilter() {
        return ingestionFilter;
    }

    public EntityFilter getControllingFilter() {
        return controllingFilter;
    }

    public EntityFilter getRedistributeFilter() {
        return redistributeFilter;
    }

    public EntityFilter getAggregationFilter() {
        return aggregationFilter;
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
