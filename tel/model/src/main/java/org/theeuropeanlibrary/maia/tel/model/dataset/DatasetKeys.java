package org.theeuropeanlibrary.maia.tel.model.dataset;

import org.theeuropeanlibrary.maia.common.FieldId;
import org.theeuropeanlibrary.maia.common.TKey;
import org.theeuropeanlibrary.maia.tel.model.common.qualifier.Country;
import org.theeuropeanlibrary.maia.tel.model.common.qualifier.Language;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.Agreement;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.DatasetType;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.DigitisationStatus;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.IngestionStatus;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.License;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.Restriction;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.descriptions.CollectionDescription;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.descriptions.Discipline;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.descriptions.ItemType;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.descriptions.SpatialCoverage;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.descriptions.Subject;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.descriptions.TimeCoverage;
import org.theeuropeanlibrary.maia.tel.model.provider.ProviderKeys;
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

}
