package org.theeuropeanlibrary.maia.converter.binary;

import org.theeuropeanlibrary.maia.common.registry.TestKeyRegistry;
import org.theeuropeanlibrary.maia.common.registry.TestQualifierRegistry;
import org.theeuropeanlibrary.maia.converter.binary.factory.BaseBinaryConverterFactory;

/**
 * This setups a test converter factory.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public class TestBinaryConverterFactory extends BaseBinaryConverterFactory {

    public TestBinaryConverterFactory() {
        super(TestKeyRegistry.class, TestQualifierRegistry.class);
    }
}
