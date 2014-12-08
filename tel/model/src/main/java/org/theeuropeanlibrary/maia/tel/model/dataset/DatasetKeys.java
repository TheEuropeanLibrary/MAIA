package org.theeuropeanlibrary.maia.tel.model.dataset;

import org.theeuropeanlibrary.maia.common.FieldId;
import org.theeuropeanlibrary.maia.common.TKey;
import org.theeuropeanlibrary.maia.tel.model.common.BasicLink;
import org.theeuropeanlibrary.maia.tel.model.common.qualifier.Country;
import org.theeuropeanlibrary.maia.tel.model.common.qualifier.Discipline;
import org.theeuropeanlibrary.maia.tel.model.common.qualifier.Language;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.Agreement;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.DatasetType;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.DigitisedType;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.IngestionStatus;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.License;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.Restriction;

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
    public static final TKey<DatasetKeys, Language> LANGUAGE = TKey.register(
            DatasetKeys.class,
            "Language",
            Language.class);

    @FieldId(4)
    public static final TKey<DatasetKeys, Country> COUNTRY = TKey.register(
            DatasetKeys.class,
            "Country",
            Country.class);

    @FieldId(5)
    public static final TKey<DatasetKeys, IngestionStatus> INGESTION_STATUS = TKey.register(DatasetKeys.class,
            "Status",
            IngestionStatus.class);

    @FieldId(6)
    public static final TKey<DatasetKeys, DatasetType> DATASET_TYPE = TKey.register(
            DatasetKeys.class,
            "DatasetType",
            DatasetType.class);

    @FieldId(51)
    public static final TKey<DatasetKeys, String> STATUS_INFO = TKey.register(
            DatasetKeys.class,
            "StatusInfo",
            String.class);

    @FieldId(52)
    public static final TKey<DatasetKeys, DigitisedType> DIGITISED_TYPE = TKey.register(
            DatasetKeys.class,
            "DigitisedType",
            DigitisedType.class);

    @FieldId(53)
    public static final TKey<DatasetKeys, Discipline> DISCIPLINE = TKey.register(
            DatasetKeys.class,
            "Discipline",
            Discipline.class);

    @FieldId(54)
    public static final TKey<DatasetKeys, String> NOTE = TKey.register(
            DatasetKeys.class,
            "Note",
            String.class);

    @FieldId(55)
    public static final TKey<DatasetKeys, BasicLink> LINK = TKey.register(
            DatasetKeys.class,
            "Link",
            BasicLink.class);

    @FieldId(101)
    public static final TKey<DatasetKeys, Agreement> AGREEMENT = TKey.register(
            DatasetKeys.class,
            "Agreement",
            Agreement.class);
    
    @FieldId(102)
    public static final TKey<DatasetKeys, Restriction> RESTRICTION = TKey.register(
            DatasetKeys.class,
            "Restriction",
            Restriction.class);
    
    @FieldId(103)
    public static final TKey<DatasetKeys, License> LICENSE = TKey.register(
            DatasetKeys.class,
            "License",
            License.class);
}
