package org.theeuropeanlibrary.maia.tel.model.provider;

import java.util.List;
import junit.framework.Assert;
import org.junit.Test;
import org.theeuropeanlibrary.maia.common.converter.ConverterException;
import org.theeuropeanlibrary.maia.common.Entity.QualifiedValue;
import org.theeuropeanlibrary.maia.common.definitions.Provider;
import org.theeuropeanlibrary.maia.converter.binary.ProviderEntityBinaryConverter;
import org.theeuropeanlibrary.maia.converter.binary.factory.BaseBinaryConverterFactory;

/**
 * This class tests conversion from and to binary format for the The European
 * Library provider.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 28.10.2014
 */
public class ProviderBinaryConverterTest {

    @Test
    public void encodeDecodeRecordTest() throws ConverterException {
        String id = "prov_0";
        String name = "TEL";

        Provider<String> provider = new Provider<>();
        provider.setId(id);
        provider.addValue(ProviderKeys.NAME, name);

        ProviderEntityBinaryConverter conv = new ProviderEntityBinaryConverter(new BaseBinaryConverterFactory(ProviderKeys.class));

        byte[] mdrEncoded = conv.encode(provider);
        Provider<String> providerDecoded = conv.decode(mdrEncoded);

        List<QualifiedValue<String>> nameField = providerDecoded.getQualifiedValues(ProviderKeys.NAME);
        QualifiedValue<String> decodedBase = nameField.get(0);

        Assert.assertEquals(id, provider.getId());
        Assert.assertEquals(name, decodedBase.getValue());
    }
}
