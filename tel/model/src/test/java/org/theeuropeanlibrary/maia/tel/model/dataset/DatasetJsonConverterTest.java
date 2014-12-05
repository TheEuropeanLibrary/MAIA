package org.theeuropeanlibrary.maia.tel.model.dataset;

import java.util.List;
import junit.framework.Assert;
import org.junit.Test;
import org.theeuropeanlibrary.maia.common.Entity.QualifiedValue;
import org.theeuropeanlibrary.maia.common.definitions.Dataset;
import org.theeuropeanlibrary.maia.converter.json.EntityObjectMapper;
import org.theeuropeanlibrary.maia.converter.json.DatasetEntityJsonConverter;
import org.theeuropeanlibrary.maia.tel.model.common.qualifier.Country;
import org.theeuropeanlibrary.maia.tel.model.common.qualifier.Language;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.DatasetType;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.IngestionStatus;

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
//////        System.out.println(schemaStr);
//        FileUtils.writeStringToFile(new File("/home/markus/NetBeansProjects/MAIA/tel/model/src/main/resources/dataset-schema.json"), schemaStr);
        String id = "coll_0";
        String name = "TEL Collection";

        Dataset<String> dataset = new Dataset<>();
        dataset.setId(id);
        dataset.addValue(DatasetKeys.IDENTIFIER, id);
        dataset.addValue(DatasetKeys.NAME, name);
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
        dataset.addValue(DatasetKeys.NAME, "British Library integrated catalogue - Online catalogues of printed and electronic resources");
        dataset.addValue(DatasetKeys.LANGUAGE, Language.ENG);
        dataset.addValue(DatasetKeys.COUNTRY, Country.GB);
        dataset.addValue(DatasetKeys.INGESTION_STATUS, IngestionStatus.PUBLISH);
        dataset.addValue(DatasetKeys.DATASET_TYPE, DatasetType.CATALOGUE);
    }
}
