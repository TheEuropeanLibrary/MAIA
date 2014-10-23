package org.theeuropeanlibrary.maia.converter.xml;

import org.theeuropeanlibrary.maia.common.registry.TestEntityRegistry;
import org.theeuropeanlibrary.maia.common.registry.TestKey;
import org.theeuropeanlibrary.maia.converter.xml.serializer.AnnotationBasedXmlConverter;
import org.theeuropeanlibrary.maia.converter.xml.factory.BaseXmlFieldConverterFactory;

/**
 * This setups a converter using the test setup from the commons package.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 20.10.2014
 */
public class TestXmlFieldConverterFactory extends BaseXmlFieldConverterFactory {

    public static TestXmlFieldConverterFactory INSTANCE = new TestXmlFieldConverterFactory();

    private TestXmlFieldConverterFactory() {
        super(TestEntityRegistry.INSTANCE);

        final AnnotationBasedXmlConverter<TestKey> testKeyConverter = new AnnotationBasedXmlConverter<>(
                TestKey.class, null);
        converters.put(TestKey.class, testKeyConverter);
    }
}
