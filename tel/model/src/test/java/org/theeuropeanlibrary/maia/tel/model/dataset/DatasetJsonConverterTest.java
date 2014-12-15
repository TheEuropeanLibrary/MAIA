package org.theeuropeanlibrary.maia.tel.model.dataset;

import java.util.List;
import junit.framework.Assert;
import org.junit.Test;
import org.theeuropeanlibrary.maia.common.Entity.QualifiedValue;
import org.theeuropeanlibrary.maia.common.definitions.Dataset;
import org.theeuropeanlibrary.maia.converter.json.EntityObjectMapper;
import org.theeuropeanlibrary.maia.converter.json.DatasetEntityJsonConverter;
import org.theeuropeanlibrary.maia.tel.model.common.EntityRelation;
import org.theeuropeanlibrary.maia.tel.model.common.PortalStatus;
import org.theeuropeanlibrary.maia.tel.model.common.qualifier.Country;
import org.theeuropeanlibrary.maia.tel.model.common.qualifier.Language;
import org.theeuropeanlibrary.maia.tel.model.common.qualifier.NameType;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.Agreement;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.AgreementStatus;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.DataType;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.DatasetType;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.DigitisationStatus;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.DistributionFormat;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.IngestionNumber;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.IngestionStatus;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.IngestionUpdate;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.License;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.LicenseType;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.LinkType;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.NoteType;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.Restriction;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.Statistic;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.descriptions.CollectionDescription;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.descriptions.Discipline;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.descriptions.ItemType;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.descriptions.SpatialCoverage;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.descriptions.Subject;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.descriptions.TimeCoverage;

/**
 * This class tests conversion from and to xml representations for the The
 * European Library dataset.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 28.10.2014
 */
public class DatasetJsonConverterTest {

    @Test
    public void encodeDecodeDatasetTest() throws Exception {
        EntityObjectMapper mapper = new EntityObjectMapper(null, DatasetRegistry.getInstance(), null);
        DatasetEntityJsonConverter converter = new DatasetEntityJsonConverter(mapper);

//        JsonSchema jsonSchema = mapper.generateJsonSchema(Dataset.class);
//        String schemaStr = jsonSchema.toString();
////        System.out.println(schemaStr);
//        FileUtils.writeStringToFile(new File("/home/markus/NetBeansProjects/MAIA/tel/model/src/main/resources/dataset-schema.json"), schemaStr);
        String id = "coll_0";
        String name = "TEL Collection";

        Dataset<String> dataset = new Dataset<>();
        dataset.setId(id);
        dataset.addValue(DatasetKeys.IDENTIFIER, id);
        dataset.addValue(DatasetKeys.NAME, name, NameType.MAIN, Language.ENG);
        dataset.addValue(DatasetKeys.COUNTRY, Country.SE);

//        Dataset<String> prov = DatasetRegistry.getInstance().getFilterFactory().getFilterForName("general").filter(dataset);
        String enc = converter.encode(dataset);
//        System.out.println(enc);
//        FileUtils.writeStringToFile(new File("/home/markus/NetBeansProjects/MAIA/tel/model/src/main/resources/dataset.json"), enc);
        Dataset<String> datasetDecoded = converter.decode(enc);

        List<QualifiedValue<String>> nameField = datasetDecoded.getQualifiedValues(DatasetKeys.NAME);
        QualifiedValue<String> decodedBase = nameField.get(0);

        Assert.assertEquals(id, dataset.getId());
        Assert.assertEquals(name, decodedBase.getValue());
    }

    private void createDatasets() {
        Dataset<String> dataset = new Dataset<>();
        dataset.setId("a0037");

        dataset.addValue(DatasetKeys.IDENTIFIER, "a0037");
        dataset.addValue(DatasetKeys.NAME, "British Library integrated catalogue - Online catalogues of printed and electronic resources", NameType.MAIN, Language.ENG);
        dataset.addValue(DatasetKeys.COUNTRY, Country.GB);
        dataset.addValue(DatasetKeys.INGESTION_STATUS, IngestionStatus.PUBLISH);
        dataset.addValue(DatasetKeys.DATASET_TYPE, DatasetType.CATALOGUE);

        Agreement agreement = new Agreement();
        agreement.setStatus(AgreementStatus.PUBLISH);
        agreement.setSigned("01-01-2014");
        agreement.setAppendix("01-01-2014");
        dataset.addValue(DatasetKeys.AGREEMENT, agreement);

        Restriction restriction = new Restriction();
        restriction.setFormat(DistributionFormat.OTHER);
        restriction.setOtherFormat("BLA");
        restriction.setTerms("test");
        restriction.setTime("01-01-2019");
        restriction.setDuration("01-01-2021");
        dataset.addValue(DatasetKeys.RESTRICTION, restriction);

        License license = new License();
        license.setSource(LicenseType.OTHER);
        license.setOtherSource("LICENSE");
        license.setDistribution(LicenseType.BY);
        license.setFurtherInformation("BLA");
        dataset.addValue(DatasetKeys.LICENSE, license);
        
        dataset.addValue(DatasetKeys.PORTAL_STATUS, PortalStatus.LIVE);
        dataset.addValue(DatasetKeys.LINK, "http://www.theeuropeanlibrary.org/tel4/collection/a0037", LinkType.ACCESS);
        dataset.addValue(DatasetKeys.NOTE, "Limited access", NoteType.ACCESS_RIGHTS);

        dataset.addValue(DatasetKeys.DIGITISATION_STATUS, DigitisationStatus.PARTIALLY_DIGITISED);
        dataset.addValue(DatasetKeys.EXPECTED_RECORDS, "13000000");
        dataset.addValue(DatasetKeys.EXPECTED_DIGITAL_OBJECTS, "1000000");
        dataset.addValue(DatasetKeys.DATA_FORMAT, "LCSH", DataType.AUTHORITY);

        dataset.addValue(DatasetKeys.DISCIPLINE, Discipline.HUMANITIES);
        dataset.addValue(DatasetKeys.LANGUAGE, Language.ENG);
        dataset.addValue(DatasetKeys.SUBJECT, Subject.MEDIEVAL_HISTORY);
        dataset.addValue(DatasetKeys.TIME_COVERAGE, TimeCoverage.RENAISSANCE);
        dataset.addValue(DatasetKeys.SPATIAL_COVERAGE, SpatialCoverage.PARIS);
        dataset.addValue(DatasetKeys.ITEM_TYPE, ItemType.BLUE_PRINT);
        dataset.addValue(DatasetKeys.COLLECTION_DESCRIPTION, new CollectionDescription("Title", "KatNUK je glavni katalog naše knjižnice in je hkrati največji katalog posamezne knjižnice v Sloveniji. Obsega več kot 900.000 bibliografskih zapisov, ki predstavljajo: gradivo, ki ga knjižnica tekoče pridobiva od leta 1988; za obdobje 1774-1948 samo popis knjig in vse gradivo, ki ga je knjižnica pridobila v obdobju od 1948-1987. V katalogu so popisane monografske publikacije, novejše periodične publikacije in članki iz slovenskih strokovnih časnikov in revij (tekoče se popisujejo od leta 1990)"), Language.ENG);

        dataset.addValue(DatasetKeys.HARVESTING_TIME, "Other");
        dataset.addValue(DatasetKeys.HARVESTING_TIME_OTHER, "Weekly");
        dataset.addValue(DatasetKeys.HARVESTING_DATE, "01-01-2012");
        dataset.addValue(DatasetKeys.HARVESTING_UPDATE, "01-01-2014");
        dataset.addValue(DatasetKeys.HARVESTING_RECORDS, "1000000");

        dataset.addValue(DatasetKeys.INGESTION_NUMBER, new IngestionNumber("Loading", "1000000"));
        dataset.addValue(DatasetKeys.INGESTION_NUMBER, new IngestionNumber("Indexing", "1000000"));
        dataset.addValue(DatasetKeys.INGESTION_UPDATE, new IngestionUpdate("Loading", "01-01-2014", "5000"));
        dataset.addValue(DatasetKeys.INGESTION_UPDATE, new IngestionUpdate("Indexing", "01-01-2014", "5000"));

        dataset.addValue(DatasetKeys.STATISTIC, new Statistic("EDM", "01-01-2014", "5000"));

        dataset.addValue(DatasetKeys.AGGREGATION_DATE, "01-01-2012");
        dataset.addValue(DatasetKeys.AGGREGATION_UPDATE, "01-01-2014");
        dataset.addValue(DatasetKeys.EUROPEANA_ID, "91209312");
        dataset.addValue(DatasetKeys.EUROPEANA_RECORDS, "1000000");
        dataset.addValue(DatasetKeys.DIGITISED_CONTENT_RIGHTS, "CC0");
        dataset.addValue(DatasetKeys.EUROPEANA_DELIVERY_DATE, "01-01-2014");

        dataset.addValue(DatasetKeys.PROJECT, new EntityRelation("31", "EC1914"));

        dataset.addValue(DatasetKeys.TASK, new EntityRelation("132", "Update Information"));

        dataset.addValue(DatasetKeys.TICKET, new EntityRelation("132", "Synchronization on portal doesn't work!"));

        dataset.addValue(DatasetKeys.SUBSET, new EntityRelation("132", "a0037_a"));

    }
}
