package org.theeuropeanlibrary.maia.converter.json;

import org.theeuropeanlibrary.maia.common.registry.TestEntityRegistry;
import org.theeuropeanlibrary.maia.converter.json.factory.BaseJsonConverterFactory;

/**
 * This setups a converter using the test setup from the commons package.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 20.10.2014
 */
public class TestJsonConverterFactory extends BaseJsonConverterFactory {

    public static TestJsonConverterFactory INSTANCE = new TestJsonConverterFactory();

    private TestJsonConverterFactory() {
        super(TestEntityRegistry.INSTANCE);
    }
}
