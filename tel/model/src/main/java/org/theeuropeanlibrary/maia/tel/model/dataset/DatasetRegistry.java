package org.theeuropeanlibrary.maia.tel.model.dataset;

import java.util.HashSet;
import java.util.LinkedHashSet;
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

        Set<TKey<?, ?>> harvestingKeys = setupHarvestingKeys();
        EntityFilter harvestingFilter = new BaseEntityFilter(harvestingKeys);
        filterFactory.registerFilter("harvesting", harvestingFilter);

        Set<TKey<?, ?>> ingestionKeys = setupIngestionKeys();
        EntityFilter ingestionFilter = new BaseEntityFilter(ingestionKeys);
        filterFactory.registerFilter("ingestion", ingestionFilter);

        Set<TKey<?, ?>> statisticKeys = setupStatisticsKeys();
        EntityFilter statisticFilter = new BaseEntityFilter(statisticKeys);
        filterFactory.registerFilter("statistic", statisticFilter);

        Set<TKey<?, ?>> aggregationKeys = setupAggregationKeys();
        EntityFilter aggregationFilter = new BaseEntityFilter(aggregationKeys);
        filterFactory.registerFilter("aggregation", aggregationFilter);

        Set<TKey<?, ?>> relKeys = new LinkedHashSet<>();
        relKeys.add(DatasetKeys.PROJECT);
        filterFactory.registerFilter("project", new BaseEntityFilter(relKeys));

        relKeys = new LinkedHashSet<>();
        relKeys.add(DatasetKeys.CASE);
        filterFactory.registerFilter("case", new BaseEntityFilter(relKeys));

        relKeys = new LinkedHashSet<>();
        relKeys.add(DatasetKeys.TICKET);
        filterFactory.registerFilter("ticket", new BaseEntityFilter(relKeys));

        relKeys = new LinkedHashSet<>();
        relKeys.add(DatasetKeys.TASK);
        filterFactory.registerFilter("task", new BaseEntityFilter(relKeys));
        
        relKeys = new LinkedHashSet<>();
        relKeys.add(DatasetKeys.SUBSET);
        filterFactory.registerFilter("subset", new BaseEntityFilter(relKeys));

        keys.addAll(generalKeys);
        keys.addAll(redistributeKeys);
        keys.addAll(dataKeys);
        keys.addAll(portalKeys);
        keys.addAll(descriptionKeys);
        keys.addAll(harvestingKeys);
        keys.addAll(ingestionKeys);
        keys.addAll(statisticKeys);
        keys.addAll(aggregationKeys);
        keys.add(DatasetKeys.PROJECT);
        keys.add(DatasetKeys.CASE);
        keys.add(DatasetKeys.TICKET);
        keys.add(DatasetKeys.TASK);
        keys.add(DatasetKeys.SUBSET);

        Set<TKey<?, ?>> providerUserKeys = new HashSet<>();
        providerUserKeys.addAll(generalKeys);
        providerUserKeys.addAll(redistributeKeys);
        providerUserKeys.addAll(dataKeys);
        providerUserKeys.addAll(portalKeys);
        providerUserKeys.addAll(descriptionKeys);
        providerUserKeys.addAll(harvestingKeys);
        providerUserKeys.addAll(ingestionKeys);
        providerUserKeys.addAll(statisticKeys);
        providerUserKeys.addAll(aggregationKeys);
        providerUserKeys.add(DatasetKeys.PROJECT);
        providerUserKeys.add(DatasetKeys.CASE);
        providerUserKeys.add(DatasetKeys.TICKET);
        providerUserKeys.add(DatasetKeys.TASK);
        providerUserKeys.add(DatasetKeys.SUBSET);
        EntityFilter providerUserFilter = new BaseEntityFilter(providerUserKeys);
        filterFactory.registerFilter("provider", providerUserFilter);

        Set<TKey<?, ?>> officeUserKeys = new HashSet<>();
        officeUserKeys.addAll(generalKeys);
        officeUserKeys.addAll(redistributeKeys);
        officeUserKeys.addAll(dataKeys);
        officeUserKeys.addAll(portalKeys);
        officeUserKeys.addAll(descriptionKeys);
        officeUserKeys.addAll(harvestingKeys);
        officeUserKeys.addAll(ingestionKeys);
        officeUserKeys.addAll(statisticKeys);
        officeUserKeys.addAll(aggregationKeys);
        officeUserKeys.add(DatasetKeys.PROJECT);
        officeUserKeys.add(DatasetKeys.CASE);
        officeUserKeys.add(DatasetKeys.TICKET);
        officeUserKeys.add(DatasetKeys.TASK);
        officeUserKeys.add(DatasetKeys.SUBSET);
        EntityFilter officeUserFilter = new BaseEntityFilter(officeUserKeys);
        filterFactory.registerFilter("office", officeUserFilter);

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

    private Set<TKey<?, ?>> setupHarvestingKeys() {
        Set<TKey<?, ?>> ingestionKeys = new HashSet<>();

        ingestionKeys.add(DatasetKeys.HARVESTING_TIME);
        ingestionKeys.add(DatasetKeys.HARVESTING_TIME_OTHER);
        ingestionKeys.add(DatasetKeys.HARVESTING_DATE);
        ingestionKeys.add(DatasetKeys.HARVESTING_UPDATE);
        ingestionKeys.add(DatasetKeys.HARVESTING_RECORDS);

        uniqueKeys.add(DatasetKeys.HARVESTING_TIME);
        uniqueKeys.add(DatasetKeys.HARVESTING_TIME_OTHER);
        uniqueKeys.add(DatasetKeys.HARVESTING_DATE);
        uniqueKeys.add(DatasetKeys.HARVESTING_UPDATE);
        uniqueKeys.add(DatasetKeys.HARVESTING_RECORDS);

        return ingestionKeys;
    }

    private Set<TKey<?, ?>> setupIngestionKeys() {
        Set<TKey<?, ?>> ingestionKeys = new HashSet<>();

        ingestionKeys.add(DatasetKeys.INGESTION_NUMBER);
        ingestionKeys.add(DatasetKeys.INGESTION_UPDATE);

        return ingestionKeys;
    }

    private Set<TKey<?, ?>> setupStatisticsKeys() {
        Set<TKey<?, ?>> statisticKeys = new HashSet<>();

        statisticKeys.add(DatasetKeys.STATISTIC);

        return statisticKeys;
    }

    private Set<TKey<?, ?>> setupAggregationKeys() {
        Set<TKey<?, ?>> aggregationKeys = new HashSet<>();

        aggregationKeys.add(DatasetKeys.AGGREGATION_DATE);
        aggregationKeys.add(DatasetKeys.AGGREGATION_UPDATE);
        aggregationKeys.add(DatasetKeys.EUROPEANA_ID);
        aggregationKeys.add(DatasetKeys.EUROPEANA_RECORDS);
        aggregationKeys.add(DatasetKeys.DIGITISED_CONTENT_RIGHTS);
        aggregationKeys.add(DatasetKeys.EUROPEANA_DELIVERY_DATE);
        
        uniqueKeys.add(DatasetKeys.AGGREGATION_DATE);
        uniqueKeys.add(DatasetKeys.AGGREGATION_UPDATE);
        uniqueKeys.add(DatasetKeys.EUROPEANA_ID);
        uniqueKeys.add(DatasetKeys.EUROPEANA_RECORDS);
        uniqueKeys.add(DatasetKeys.DIGITISED_CONTENT_RIGHTS);
        uniqueKeys.add(DatasetKeys.EUROPEANA_DELIVERY_DATE);

        return aggregationKeys;
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
