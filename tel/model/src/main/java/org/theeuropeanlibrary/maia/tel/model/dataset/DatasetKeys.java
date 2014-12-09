package org.theeuropeanlibrary.maia.tel.model.dataset;

import org.theeuropeanlibrary.maia.common.FieldId;
import org.theeuropeanlibrary.maia.common.TKey;
import org.theeuropeanlibrary.maia.tel.model.common.qualifier.Country;
import org.theeuropeanlibrary.maia.tel.model.common.qualifier.Language;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.Agreement;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.DatasetType;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.DigitisationStatus;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.IngestionNumber;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.IngestionStatus;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.IngestionUpdate;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.License;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.Restriction;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.Statistic;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.descriptions.CollectionDescription;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.descriptions.Discipline;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.descriptions.ItemType;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.descriptions.SpatialCoverage;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.descriptions.Subject;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.descriptions.TimeCoverage;
import org.theeuropeanlibrary.maia.tel.model.provider.ProviderKeys;
import org.theeuropeanlibrary.maia.tel.model.provider.definitions.EntityRelation;
import org.theeuropeanlibrary.maia.tel.model.provider.definitions.PortalStatus;

/**
 * This class holds all the keys and qualifiers defining an provider in The
 * European Library domain model
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 28.10.2014
 */
public final class DatasetKeys {

    private DatasetKeys() {
        // constants class
    }

    // General 
    // Portal
    // Collection Descriptions
    // Ingestion
    // Controlling
    // Relations
    @FieldId(1)
    public static final TKey<DatasetKeys, String> NAME = TKey.register(
            DatasetKeys.class,
            "Name",
            String.class);

    @FieldId(2)
    public static final TKey<DatasetKeys, String> IDENTIFIER = TKey.register(
            DatasetKeys.class,
            "Identifier",
            String.class);

    @FieldId(3)
    public static final TKey<DatasetKeys, Country> COUNTRY = TKey.register(
            DatasetKeys.class,
            "Country",
            Country.class);

    @FieldId(4)
    public static final TKey<DatasetKeys, IngestionStatus> INGESTION_STATUS = TKey.register(DatasetKeys.class,
            "IngestionStatus",
            IngestionStatus.class);

    @FieldId(5)
    public static final TKey<DatasetKeys, DatasetType> DATASET_TYPE = TKey.register(
            DatasetKeys.class,
            "DatasetType",
            DatasetType.class);

    @FieldId(51)
    public static final TKey<DatasetKeys, Agreement> AGREEMENT = TKey.register(
            DatasetKeys.class,
            "Agreement",
            Agreement.class);

    @FieldId(52)
    public static final TKey<DatasetKeys, Restriction> RESTRICTION = TKey.register(
            DatasetKeys.class,
            "Restriction",
            Restriction.class);

    @FieldId(53)
    public static final TKey<DatasetKeys, License> LICENSE = TKey.register(
            DatasetKeys.class,
            "License",
            License.class);

    @FieldId(101)
    public static final TKey<DatasetKeys, DigitisationStatus> DIGITISATION_STATUS = TKey.register(
            DatasetKeys.class,
            "DigitisationStatus",
            DigitisationStatus.class);

    @FieldId(102)
    public static final TKey<DatasetKeys, String> EXPECTED_RECORDS = TKey.register(
            DatasetKeys.class,
            "ExpectedRecords",
            String.class);

    @FieldId(103)
    public static final TKey<DatasetKeys, String> EXPECTED_DIGITAL_OBJECTS = TKey.register(
            DatasetKeys.class,
            "ExpectedDigitalObjects",
            String.class);

    @FieldId(104)
    public static final TKey<DatasetKeys, String> DATA_FORMAT = TKey.register(
            DatasetKeys.class,
            "DataFormat",
            String.class);

    @FieldId(151)
    public static final TKey<ProviderKeys, PortalStatus> PORTAL_STATUS = TKey.register(
            ProviderKeys.class,
            "PortalStatus",
            PortalStatus.class);

    @FieldId(152)
    public static final TKey<ProviderKeys, String> LINK = TKey.register(
            ProviderKeys.class,
            "Link",
            String.class);

    @FieldId(153)
    public static final TKey<ProviderKeys, String> NOTE = TKey.register(
            ProviderKeys.class,
            "Note",
            String.class);

//    @FieldId(201)
//    public static final TKey<ProviderKeys, String> DISCIPLINE = TKey.register(
//            ProviderKeys.class,
//            "Discipline",
//            String.class);
    @FieldId(201)
    public static final TKey<ProviderKeys, Discipline> DISCIPLINE = TKey.register(
            ProviderKeys.class,
            "Discipline",
            Discipline.class);

    @FieldId(202)
    public static final TKey<DatasetKeys, Language> LANGUAGE = TKey.register(
            DatasetKeys.class,
            "Language",
            Language.class);

//    @FieldId(203)
//    public static final TKey<DatasetKeys, String> SUBJECT = TKey.register(
//            DatasetKeys.class,
//            "Subject",
//            String.class);
    @FieldId(203)
    public static final TKey<DatasetKeys, Subject> SUBJECT = TKey.register(
            DatasetKeys.class,
            "Subject",
            Subject.class);

//    @FieldId(204)
//    public static final TKey<DatasetKeys, String> TIME_COVERAGE = TKey.register(
//            DatasetKeys.class,
//            "TimeCoverage",
//            String.class);
    @FieldId(204)
    public static final TKey<DatasetKeys, TimeCoverage> TIME_COVERAGE = TKey.register(
            DatasetKeys.class,
            "TimeCoverage",
            TimeCoverage.class);

//    @FieldId(205)
//    public static final TKey<DatasetKeys, String> SPATIAL_COVERAGE = TKey.register(
//            DatasetKeys.class,
//            "SpatialCoverage",
//            String.class);
    @FieldId(205)
    public static final TKey<DatasetKeys, SpatialCoverage> SPATIAL_COVERAGE = TKey.register(
            DatasetKeys.class,
            "SpatialCoverage",
            SpatialCoverage.class);

//    @FieldId(206)
//    public static final TKey<DatasetKeys, String> ITEM_TYPE = TKey.register(
//            DatasetKeys.class,
//            "ItemType",
//            String.class);
    @FieldId(206)
    public static final TKey<DatasetKeys, ItemType> ITEM_TYPE = TKey.register(
            DatasetKeys.class,
            "ItemType",
            ItemType.class);

    @FieldId(207)
    public static final TKey<DatasetKeys, CollectionDescription> COLLECTION_DESCRIPTION = TKey.register(DatasetKeys.class,
            "CollectionDescription",
            CollectionDescription.class);

    @FieldId(251)
    public static final TKey<DatasetKeys, String> HARVESTING_TIME = TKey.register(DatasetKeys.class,
            "HarvestingTime",
            String.class);

    @FieldId(252)
    public static final TKey<DatasetKeys, String> HARVESTING_TIME_OTHER = TKey.register(DatasetKeys.class,
            "HarvestingTimeOther",
            String.class);

    @FieldId(253)
    public static final TKey<DatasetKeys, String> HARVESTING_DATE = TKey.register(DatasetKeys.class,
            "HarvestingDate",
            String.class);

    @FieldId(254)
    public static final TKey<DatasetKeys, String> HARVESTING_UPDATE = TKey.register(DatasetKeys.class,
            "HarvestingUpdate",
            String.class);

    @FieldId(255)
    public static final TKey<DatasetKeys, String> HARVESTING_RECORDS = TKey.register(DatasetKeys.class,
            "HarvestedRecords",
            String.class);

    @FieldId(301)
    public static final TKey<DatasetKeys, IngestionNumber> INGESTION_NUMBER = TKey.register(DatasetKeys.class,
            "IngestionNumber",
            IngestionNumber.class);

    @FieldId(302)
    public static final TKey<DatasetKeys, IngestionUpdate> INGESTION_UPDATE = TKey.register(DatasetKeys.class,
            "IngestionUpdate",
            IngestionUpdate.class);

    @FieldId(351)
    public static final TKey<DatasetKeys, Statistic> STATISTIC = TKey.register(DatasetKeys.class,
            "Statistic",
            Statistic.class);

    @FieldId(401)
    public static final TKey<ProviderKeys, String> AGGREGATION_DATE = TKey.register(
            ProviderKeys.class,
            "AggregationDate",
            String.class);

    @FieldId(402)
    public static final TKey<ProviderKeys, String> AGGREGATION_UPDATE = TKey.register(
            ProviderKeys.class,
            "AggregationUpdate",
            String.class);

    @FieldId(403)
    public static final TKey<ProviderKeys, String> EUROPEANA_ID = TKey.register(
            ProviderKeys.class,
            "EuropeanaId",
            String.class);

    @FieldId(404)
    public static final TKey<ProviderKeys, String> EUROPEANA_RECORDS = TKey.register(
            ProviderKeys.class,
            "EuropeanaRecords",
            String.class);

    @FieldId(405)
    public static final TKey<ProviderKeys, String> DIGITISED_CONTENT_RIGHTS = TKey.register(
            ProviderKeys.class,
            "DigitisedContentRights",
            String.class);

    @FieldId(405)
    public static final TKey<ProviderKeys, String> EUROPEANA_DELIVERY_DATE = TKey.register(
            ProviderKeys.class,
            "EuropeanaDeliveryDate",
            String.class);

    @FieldId(451)
    public static final TKey<ProviderKeys, EntityRelation> PROJECT = TKey.register(
            ProviderKeys.class,
            "Project",
            EntityRelation.class);

    @FieldId(452)
    public static final TKey<ProviderKeys, EntityRelation> CASE = TKey.register(
            ProviderKeys.class,
            "Case",
            EntityRelation.class);

    @FieldId(453)
    public static final TKey<ProviderKeys, EntityRelation> TICKET = TKey.register(
            ProviderKeys.class,
            "Ticket",
            EntityRelation.class);

    @FieldId(454)
    public static final TKey<ProviderKeys, EntityRelation> TASK = TKey.register(
            ProviderKeys.class,
            "Task",
            EntityRelation.class);

    @FieldId(455)
    public static final TKey<ProviderKeys, EntityRelation> SUBSET = TKey.register(
            ProviderKeys.class,
            "Subset",
            EntityRelation.class);

}
