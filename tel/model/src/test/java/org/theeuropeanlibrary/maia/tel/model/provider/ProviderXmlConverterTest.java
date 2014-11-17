package org.theeuropeanlibrary.maia.tel.model.provider;

import java.util.List;
import junit.framework.Assert;
import org.junit.Test;
import org.theeuropeanlibrary.maia.common.Entity.QualifiedValue;
import org.theeuropeanlibrary.maia.common.converter.ConverterException;
import org.theeuropeanlibrary.maia.common.definitions.Provider;
import org.theeuropeanlibrary.maia.converter.xml.ProviderEntityXmlConverter;
import org.theeuropeanlibrary.maia.converter.xml.factory.BaseXmlFieldConverterFactory;
import org.w3c.dom.Element;

/**
 * This class tests conversion from and to xml representations of a simple test
 * model.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 20.10.2014
 */
public class ProviderXmlConverterTest {

    @Test
    public void encodeDecodeProviderTest() throws ConverterException {
        String id = "prov_0";
        String name = "TEL";

        Provider<String> provider = new Provider<>();
        provider.setId(id);
        provider.addValue(ProviderKeys.NAME, name);

        ProviderEntityXmlConverter conv = new ProviderEntityXmlConverter(new BaseXmlFieldConverterFactory(ProviderRegistry.getInstance()));

        Element mdrEncoded = conv.encode(provider);
//        System.out.println(XmlUtil.writeDomToString(mdrEncoded));
        Provider<String> providerDecoded = conv.decode(mdrEncoded);

        List<QualifiedValue<String>> nameField = providerDecoded.getQualifiedValues(ProviderKeys.NAME);
        QualifiedValue<String> decodedBase = nameField.get(0);

        Assert.assertEquals(id, provider.getId());
        Assert.assertEquals(name, decodedBase.getValue());
    }
}
