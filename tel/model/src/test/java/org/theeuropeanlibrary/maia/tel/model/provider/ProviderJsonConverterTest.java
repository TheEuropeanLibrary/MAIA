package org.theeuropeanlibrary.maia.tel.model.provider;

import java.util.List;
import junit.framework.Assert;
import org.junit.Test;
import org.theeuropeanlibrary.maia.common.Entity.QualifiedValue;
import org.theeuropeanlibrary.maia.common.definitions.Provider;
import org.theeuropeanlibrary.maia.converter.json.EntityObjectMapper;
import org.theeuropeanlibrary.maia.converter.json.ProviderEntityJsonConverter;

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
//        FileUtils.writeStringToFile(new File("/home/markus/provider.json"), schemaStr);
        
        String id = "prov_0";
        String name = "TEL";
        
        Provider<String> provider = new Provider<>();
        provider.setId(id);
        provider.addValue(ProviderKeys.NAME, name);
        
        String enc = converter.encode(provider);
//        System.out.println(enc);
        Provider<String> providerDecoded = converter.decode(enc);
        
        List<QualifiedValue<String>> nameField = providerDecoded.getQualifiedValues(ProviderKeys.NAME);
        QualifiedValue<String> decodedBase = nameField.get(0);
        
        Assert.assertEquals(id, provider.getId());
        Assert.assertEquals(name, decodedBase.getValue());
    }
}
