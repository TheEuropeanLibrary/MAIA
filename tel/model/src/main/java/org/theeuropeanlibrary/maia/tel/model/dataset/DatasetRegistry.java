package org.theeuropeanlibrary.maia.tel.model.dataset;

import java.util.HashSet;
import java.util.Set;
import org.theeuropeanlibrary.maia.common.TKey;
import org.theeuropeanlibrary.maia.common.definitions.Dataset;
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
public final class DatasetRegistry extends AbstractEntityRegistry {

    private static DatasetRegistry INSTANCE;

    private final EntityFilterFactory<String, Dataset<String>> filterFactory;

    private DatasetRegistry() {
        this(new BaseEntityFilterFactory());
    }

    private DatasetRegistry(EntityFilterFactory<String, Dataset<String>> filterFactory) {
        INSTANCE = this;

        this.filterFactory = filterFactory;

        Set<TKey<?, ?>> generalKeys = setupGeneralKeys();
        EntityFilter generalFilter = new BaseEntityFilter(generalKeys);
        filterFactory.registerFilter("general", generalFilter);

        Set<TKey<?, ?>> redistributeKeys = setupRedistributeKeys();
        EntityFilter redistributeFilter = new BaseEntityFilter(redistributeKeys);
        filterFactory.registerFilter("redistribute", redistributeFilter);

        Set<TKey<?, ?>> dataKeys = setupDataKeys();
        EntityFilter dataFilter = new BaseEntityFilter(dataKeys);
        filterFactory.registerFilter("data", dataFilter);

        Set<TKey<?, ?>> portalKeys = setupPortalKeys();
        EntityFilter portalFilter = new BaseEntityFilter(portalKeys);
        filterFactory.registerFilter("portal", portalFilter);

        Set<TKey<?, ?>> descriptionKeys = setupDescriptionKeys();
        EntityFilter descriptionFilter = new BaseEntityFilter(descriptionKeys);
        filterFactory.registerFilter("description", descriptionFilter);

        Set<TKey<?, ?>> ingestionKeys = setupIngestionKeys();
        EntityFilter ingestionFilter = new BaseEntityFilter(ingestionKeys);

        Set<TKey<?, ?>> controllingKeys = setupControllingKeys();
        EntityFilter controllingFilter = new BaseEntityFilter(controllingKeys);

        Set<TKey<?, ?>> aggregationKeys = setupAggregationKeys();
        EntityFilter aggregationFilter = new BaseEntityFilter(aggregationKeys);

        Set<TKey<?, ?>> relationKeys = setupRelationshipKeys();
        EntityFilter relationFilter = new BaseEntityFilter(relationKeys);

        keys.addAll(generalKeys);
        keys.addAll(redistributeKeys);
        keys.addAll(dataKeys);
        keys.addAll(portalKeys);
        keys.addAll(descriptionKeys);
        keys.addAll(ingestionKeys);
        keys.addAll(controllingKeys);
        keys.addAll(redistributeKeys);
        keys.addAll(aggregationKeys);
        keys.addAll(relationKeys);

        Set<TKey<?, ?>> providerUserKeys = new HashSet<>();
        providerUserKeys.addAll(generalKeys);
        providerUserKeys.addAll(dataKeys);
        providerUserKeys.addAll(redistributeKeys);
        providerUserKeys.addAll(portalKeys);
        providerUserKeys.addAll(descriptionKeys);
        providerUserKeys.addAll(ingestionKeys);
        providerUserKeys.addAll(controllingKeys);
        providerUserKeys.addAll(aggregationKeys);
        providerUserKeys.addAll(relationKeys);
        EntityFilter providerUserFilter = new BaseEntityFilter(providerUserKeys);

        Set<TKey<?, ?>> officeUserKeys = new HashSet<>();
        officeUserKeys.addAll(generalKeys);
        officeUserKeys.addAll(dataKeys);
        officeUserKeys.addAll(redistributeKeys);
        officeUserKeys.addAll(portalKeys);
        officeUserKeys.addAll(descriptionKeys);
        officeUserKeys.addAll(ingestionKeys);
        officeUserKeys.addAll(controllingKeys);
        officeUserKeys.addAll(aggregationKeys);
        officeUserKeys.addAll(relationKeys);
        EntityFilter officeUserFilter = new BaseEntityFilter(officeUserKeys);

        filterFactory.registerFilter("simple", new EntityFilter<String, Dataset<String>>() {

            @Override
            public Dataset<String> filter(Dataset<String> entity) {
                Dataset<String> simpleDataset = (Dataset<String>) entity.createInstance();
                simpleDataset.setId(entity.getId());
                String name = entity.getFirstValue(DatasetKeys.NAME);
                if (name != null) {
                    simpleDataset.addValue(DatasetKeys.NAME, name);
                }
                String identifier = entity.getFirstValue(DatasetKeys.IDENTIFIER);
                if (identifier != null) {
                    simpleDataset.addValue(DatasetKeys.IDENTIFIER, identifier);
                }
                return simpleDataset;
            }

            @Override
            public void merge(Dataset<String> merger, Dataset<String> mergee) {
                throw new UnsupportedOperationException("This filter is only for view, it cannot be merged back.");
            }

        });
    }

    private Set<TKey<?, ?>> setupGeneralKeys() {
        Set<TKey<?, ?>> generalKeys = new HashSet<>();
        generalKeys.add(DatasetKeys.NAME);
        generalKeys.add(DatasetKeys.IDENTIFIER);
        generalKeys.add(DatasetKeys.COUNTRY);
        generalKeys.add(DatasetKeys.INGESTION_STATUS);
        generalKeys.add(DatasetKeys.DATASET_TYPE);

        validQualifiers.put(DatasetKeys.NAME, new HashSet<Class<? extends Enum<?>>>() {
            {
                add(DatasetQualifiers.NAME_TYPE);
                add(DatasetQualifiers.LANGUAGE);
            }
        });

        uniqueKeys.add(DatasetKeys.COUNTRY);
        uniqueKeys.add(DatasetKeys.INGESTION_STATUS);
        uniqueKeys.add(DatasetKeys.DATASET_TYPE);
        uniqueKeys.add(DatasetKeys.IDENTIFIER);

        return generalKeys;
    }

    private Set<TKey<?, ?>> setupRedistributeKeys() {
        Set<TKey<?, ?>> redistributeKeys = new HashSet<>();

        redistributeKeys.add(DatasetKeys.AGREEMENT);
        redistributeKeys.add(DatasetKeys.RESTRICTION);
        redistributeKeys.add(DatasetKeys.LICENSE);

        uniqueKeys.add(DatasetKeys.AGREEMENT);
        uniqueKeys.add(DatasetKeys.RESTRICTION);
        uniqueKeys.add(DatasetKeys.LICENSE);

        return redistributeKeys;
    }

    private Set<TKey<?, ?>> setupDataKeys() {
        Set<TKey<?, ?>> dataKeys = new HashSet<>();

        dataKeys.add(DatasetKeys.DIGITISATION_STATUS);
        dataKeys.add(DatasetKeys.EXPECTED_RECORDS);
        dataKeys.add(DatasetKeys.EXPECTED_DIGITAL_OBJECTS);
        dataKeys.add(DatasetKeys.DATA_FORMAT);

//        additionalKeys.add(DatasetKeys.STATUS_INFO);
//        additionalKeys.add(DatasetKeys.DIGITISED_TYPE);
//        additionalKeys.add(DatasetKeys.DISCIPLINE);
//        additionalKeys.add(DatasetKeys.NOTE);
//        additionalKeys.add(DatasetKeys.LINK);
//        validQualifiers.put(DatasetKeys.NOTE, new HashSet<Class<? extends Enum<?>>>() {
//            {
//                add(DatasetQualifiers.NOTE_TYPE);
//                add(DatasetQualifiers.LANGUAGE);
//            }
//        });
//        validQualifiers.put(DatasetKeys.LINK, new HashSet<Class<? extends Enum<?>>>() {
//            {
//                add(DatasetQualifiers.LINK_TYPE);
//            }
//        });
        validQualifiers.put(DatasetKeys.DATA_FORMAT, new HashSet<Class<? extends Enum<?>>>() {
            {
                add(DatasetQualifiers.DATA_TYPE);
            }
        });

        uniqueKeys.add(DatasetKeys.DIGITISATION_STATUS);
        uniqueKeys.add(DatasetKeys.EXPECTED_RECORDS);
        uniqueKeys.add(DatasetKeys.EXPECTED_DIGITAL_OBJECTS);

        return dataKeys;
    }

    private Set<TKey<?, ?>> setupPortalKeys() {
        Set<TKey<?, ?>> portalKeys = new HashSet<>();

        portalKeys.add(DatasetKeys.PORTAL_STATUS);
        portalKeys.add(DatasetKeys.LINK);
        portalKeys.add(DatasetKeys.NOTE);

        validQualifiers.put(DatasetKeys.LINK, new HashSet<Class<? extends Enum<?>>>() {
            {
                add(DatasetQualifiers.LINK_TYPE);
            }
        });
        validQualifiers.put(DatasetKeys.NOTE, new HashSet<Class<? extends Enum<?>>>() {
            {
                add(DatasetQualifiers.NOTE_TYPE);
            }
        });

        uniqueKeys.add(DatasetKeys.PORTAL_STATUS);

        return portalKeys;
    }

    private Set<TKey<?, ?>> setupDescriptionKeys() {
        Set<TKey<?, ?>> descriptionKeys = new HashSet<>();

        descriptionKeys.add(DatasetKeys.DISCIPLINE);
        descriptionKeys.add(DatasetKeys.LANGUAGE);
        descriptionKeys.add(DatasetKeys.SUBJECT);
        descriptionKeys.add(DatasetKeys.TIME_COVERAGE);
        descriptionKeys.add(DatasetKeys.SPATIAL_COVERAGE);
        descriptionKeys.add(DatasetKeys.ITEM_TYPE);
        descriptionKeys.add(DatasetKeys.COLLECTION_DESCRIPTION);

        validQualifiers.put(DatasetKeys.COLLECTION_DESCRIPTION, new HashSet<Class<? extends Enum<?>>>() {
            {
                add(DatasetQualifiers.LANGUAGE);
            }
        });

        return descriptionKeys;
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

    public EntityFilterFactory<String, Dataset<String>> getFilterFactory() {
        return filterFactory;
    }

    public static synchronized DatasetRegistry getInstance() {
        if (INSTANCE == null) {
            new DatasetRegistry();
        }
        return INSTANCE;
    }

}
