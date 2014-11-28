package org.theeuropeanlibrary.maia.tel.model.provider;

import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import java.io.File;
import java.util.List;
import java.util.UUID;
import junit.framework.Assert;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.theeuropeanlibrary.maia.common.Entity.QualifiedValue;
import org.theeuropeanlibrary.maia.common.definitions.Provider;
import org.theeuropeanlibrary.maia.converter.json.EntityObjectMapper;
import org.theeuropeanlibrary.maia.converter.json.ProviderEntityJsonConverter;
import org.theeuropeanlibrary.maia.tel.model.common.Coordinate;
import org.theeuropeanlibrary.maia.tel.model.common.qualifier.Country;
import org.theeuropeanlibrary.maia.tel.model.common.qualifier.Language;
import org.theeuropeanlibrary.maia.tel.model.common.qualifier.NameType;
import org.theeuropeanlibrary.maia.tel.model.provider.definitions.ConsortiumType;
import org.theeuropeanlibrary.maia.tel.model.provider.definitions.ContactRelation;
import org.theeuropeanlibrary.maia.tel.model.provider.definitions.DatasetRelation;
import org.theeuropeanlibrary.maia.tel.model.provider.definitions.EntityRelation;
import org.theeuropeanlibrary.maia.tel.model.provider.definitions.LibraryOrganization;
import org.theeuropeanlibrary.maia.tel.model.provider.definitions.LinkType;
import org.theeuropeanlibrary.maia.tel.model.provider.definitions.MembershipType;
import org.theeuropeanlibrary.maia.tel.model.provider.definitions.PortalStatus;
import org.theeuropeanlibrary.maia.tel.model.provider.definitions.ProviderRelationType;
import org.theeuropeanlibrary.maia.tel.model.provider.definitions.ProviderType;

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
//////        System.out.println(schemaStr);
//        FileUtils.writeStringToFile(new File("/home/markus/NetBeansProjects/MAIA/tel/model/src/main/resources/provider-schema.json"), schemaStr);
        String id = "prov_0";
        String name = "TEL";
        String otherName = "Europeana";

        Provider<String> provider = new Provider<>();
        provider.setId(id);
        provider.addValue(ProviderKeys.PROVIDER, new EntityRelation("TEL", "The European Library"));
        provider.addValue(ProviderKeys.PROVIDER, new EntityRelation("EU", "Europeana"), ProviderRelationType.AGGREGATOR);
        provider.addValue(ProviderKeys.LINK, "http://test.html", LinkType.LOGO);
        provider.addValue(ProviderKeys.LINK, "http://test2.html", LinkType.OPENING);
        provider.addValue(ProviderKeys.IDENTIFIER, id);
        provider.addValue(ProviderKeys.NAME, name, NameType.ACRONYM, Language.ENG);
        provider.addValue(ProviderKeys.NAME, otherName, NameType.ACRONYM, Language.ENG);
        provider.addValue(ProviderKeys.COUNTRY, Country.SE);
        provider.addValue(ProviderKeys.PHONE, "555-12345678");
        provider.addValue(ProviderKeys.EMAIL, "markus.muhr@kb.nl");
        provider.addValue(ProviderKeys.CONSORTIUM_TYPE, ConsortiumType.PURCHASING);
        ContactRelation cR1 = new ContactRelation(UUID.randomUUID().toString(), "Markus Muhr");
        cR1.setRole("Manager");
//        cR1.setEmail("markus.muhr@theeuropeanlibrary.org");
        provider.addValue(ProviderKeys.CONTACT, cR1);

//        Provider<String> prov = ProviderRegistry.getInstance().getFilterFactory().getFilterForName("general").filter(provider);

        String enc = converter.encode(provider);
//        System.out.println(enc);
//        FileUtils.writeStringToFile(new File("/home/markus/NetBeansProjects/MAIA/tel/model/src/main/resources/provider.json"), enc);
        Provider<String> providerDecoded = converter.decode(enc);

        List<QualifiedValue<String>> nameField = providerDecoded.getQualifiedValues(ProviderKeys.NAME);
        QualifiedValue<String> decodedBase = nameField.get(0);

        Assert.assertEquals(id, provider.getId());
        Assert.assertEquals(name, decodedBase.getValue());
    }

    private void createProviders() {
        Provider<String> provider = new Provider<>();
        provider.setId("1");
        provider.addValue(ProviderKeys.IDENTIFIER, "P01264");
        provider.addValue(ProviderKeys.NAME, "The British Library", NameType.MAIN, Language.ENG);
        provider.addValue(ProviderKeys.NAME, "NL United Kingdom", NameType.INTERNAL, Language.ENG);
        provider.addValue(ProviderKeys.NAME, "BL", NameType.ACRONYM, Language.ENG);
        provider.addValue(ProviderKeys.COUNTRY, Country.GB);
        provider.addValue(ProviderKeys.PROVIDER_TYPE, ProviderType.NATIONAL_LIBRARY);

        provider.addValue(ProviderKeys.MEMBER, true);
        provider.addValue(ProviderKeys.MEMBERSHIP_TYPE, MembershipType.MEMBERS);

        provider.addValue(ProviderKeys.LIBRARY_ORGANIZATION, LibraryOrganization.CENL);

        provider.addValue(ProviderKeys.PORTAL_STATUS, PortalStatus.LIVE);
        provider.addValue(ProviderKeys.COORDINATE, new Coordinate(51.5294, -0.1269));

        provider.addValue(ProviderKeys.LINK, "http://www.bl.uk/images/bl_logo_100.gif", LinkType.LOGO);
        provider.addValue(ProviderKeys.LINK, "www.bl.uk/", LinkType.WEBSITE);
        provider.addValue(ProviderKeys.LINK, "http://www.bl.uk/aboutus/contact/index.html", LinkType.CONTACTS);
        provider.addValue(ProviderKeys.LINK, "http://www.bl.uk/aboutus/quickinfo/loc/stp/opening/index.html#reading", LinkType.OPENING);
        provider.addValue(ProviderKeys.LINK, "http://en.wikipedia.org/wiki/British_Library", LinkType.WIKIPEDIA_ENGLISH);
        provider.addValue(ProviderKeys.LINK, "http://en.wikipedia.org/wiki/British_Library", LinkType.WIKIPEDIA_NATIVE);

        provider.addValue(ProviderKeys.IMAGE, "http://upload.wikimedia.org/wikipedia/commons/thumb/2/22/British_library_london.jpg/230px-British_library_london.jpg");
        provider.addValue(ProviderKeys.IMAGE, "http://www.theeuropeanlibrary.org/exhibition/buildings/images/pictures/uk_l04.jpg");
        provider.addValue(ProviderKeys.IMAGE, "http://search.theeuropeanlibrary.org/images/treasure");

        provider.addValue(ProviderKeys.CONTACT, new ContactRelation("11", "Roly Keating", "Director or Deputy Director", "", ""));
        provider.addValue(ProviderKeys.CONTACT, new ContactRelation("12", "Janet Zmroczek", "Collections Contact", "", ""));
        provider.addValue(ProviderKeys.CONTACT, new ContactRelation("13", "Corine Deliot", "Technical contact", "", ""));
        provider.addValue(ProviderKeys.CONTACT, new ContactRelation("14", "Rossitza Atanassova", "Marketing Contact", "", ""));
        provider.addValue(ProviderKeys.CONTACT, new ContactRelation("15", "Library Coordinator Group", "Janet Zmroczek", "", ""));

        provider.addValue(ProviderKeys.DATASET, new DatasetRelation("21", "British Library integrated catalogue - Online catalogues of printed and electronic resources", "a0037", "Live"));
        provider.addValue(ProviderKeys.DATASET, new DatasetRelation("22", "EC1914 BL-Printed Literary Sources", "a0554", "Internal"));

        provider.addValue(ProviderKeys.PROJECT, new EntityRelation("31", "EC1914"));
    }
}
