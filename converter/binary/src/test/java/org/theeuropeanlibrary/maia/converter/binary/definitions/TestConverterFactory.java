package org.theeuropeanlibrary.maia.converter.binary.definitions;

import org.theeuropeanlibrary.maia.converter.binary.factory.BaseConverterFactory;

/**
 * This setups a test converter factory.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public class TestConverterFactory extends BaseConverterFactory {

    public TestConverterFactory() {
        super(TestKeyRegistry.class, TestQualifierRegistry.class);
    }
}
