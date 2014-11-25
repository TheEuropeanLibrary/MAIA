package org.theeuropeanlibrary.maia.tel.model.provider;

import java.util.List;
import junit.framework.Assert;
import org.junit.Test;
import org.theeuropeanlibrary.maia.common.Entity.QualifiedValue;
import org.theeuropeanlibrary.maia.common.definitions.Provider;
import org.theeuropeanlibrary.maia.converter.json.EntityObjectMapper;
import org.theeuropeanlibrary.maia.converter.json.ProviderEntityJsonConverter;
import org.theeuropeanlibrary.maia.tel.model.common.BasicLink;
import org.theeuropeanlibrary.maia.tel.model.common.qualifier.Language;
import org.theeuropeanlibrary.maia.tel.model.common.qualifier.NameType;
import org.theeuropeanlibrary.maia.tel.model.provider.definitions.EntityRelation;
import org.theeuropeanlibrary.maia.tel.model.provider.definitions.LinkType;
import org.theeuropeanlibrary.maia.tel.model.provider.definitions.ProviderRelationType;

/**
 * This class tests conversion from and to xml representations for the The
 * European Library provider.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 28.10.2014
 */
public class ProviderJsonConverterTest {

    @Test
    public void encodeDecodeProviderTest() throws Exception {
        EntityObjectMapper mapper = new EntityObjectMapper(ProviderRegistry.getInstance(), null, null);
        ProviderEntityJsonConverter converter = new ProviderEntityJsonConverter(mapper);

//        JsonSchema jsonSchema = mapper.generateJsonSchema(Provider.class);
//        String schemaStr = jsonSchema.toString();
//        System.out.println(schemaStr);
//        FileUtils.writeStringToFile(new File("/home/markus/NetBeansProjects/MAIA/tel/model/src/main/resources/provider.json"), schemaStr);

        String id = "prov_0";
        String name = "TEL";
        String otherName = "Europeana";

        Provider<String> provider = new Provider<>();
        provider.setId(id);
        provider.addValue(ProviderKeys.PROVIDER, new EntityRelation("TEL", "The European Library"));
        provider.addValue(ProviderKeys.PROVIDER, new EntityRelation("EU", "Europeana"), ProviderRelationType.AGGREGATOR);
        provider.addValue(ProviderKeys.LINK, new BasicLink("http://test.html"), LinkType.LOGO);
        provider.addValue(ProviderKeys.LINK, new BasicLink("http://test2.html"), LinkType.OPENING);
        provider.addValue(ProviderKeys.IDENTIFIER, id);
        provider.addValue(ProviderKeys.NAME, name, NameType.ACRONYM, Language.ENG);
        provider.addValue(ProviderKeys.NAME, otherName, NameType.ACRONYM, Language.ENG);

        String enc = converter.encode(provider);
//        System.out.println(enc);
//        FileUtils.writeStringToFile(new File("/home/markus/NetBeansProjects/MAIA/tel/model/src/main/resources/test.json"), enc);
        Provider<String> providerDecoded = converter.decode(enc);

        List<QualifiedValue<String>> nameField = providerDecoded.getQualifiedValues(ProviderKeys.NAME);
        QualifiedValue<String> decodedBase = nameField.get(0);

        Assert.assertEquals(id, provider.getId());
        Assert.assertEquals(name, decodedBase.getValue());
    }
}
