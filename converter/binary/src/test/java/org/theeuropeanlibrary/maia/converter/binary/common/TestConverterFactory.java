package org.theeuropeanlibrary.maia.converter.binary.common;

import java.lang.reflect.Field;
import org.theeuropeanlibrary.maia.common.FieldId;
import org.theeuropeanlibrary.maia.common.TKey;
import org.theeuropeanlibrary.maia.converter.binary.BaseConverterFactory;

/**
 * This setups a test converter factory.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public class TestConverterFactory extends BaseConverterFactory {

    public TestConverterFactory() {
        for (Field f : TestKeyRegistry.class.getDeclaredFields()) {
            FieldId ann = f.getAnnotation(FieldId.class);
            if (ann != null) {
                if (fieldIdTkey.containsKey(ann.value())) {
                    throw new RuntimeException(
                            "Duplicate field id '" + ann.value() + "' is not allowed!");
                }

                try {
                    tkeyFieldId.put((TKey<?, ?>) f.get(TKey.class), ann.value());
                    fieldIdTkey.put(ann.value(), (TKey<?, ?>) f.get(TKey.class));
                } catch (Exception e) {
                    throw new RuntimeException("Field '" + f + "' cannot be accessed!", e);
                }
            }
        }
        
        for (Field f : TestQualifierRegistry.class.getDeclaredFields()) {
            FieldId fann = f.getAnnotation(FieldId.class);
            if (fann == null) {
                continue;
            }

            Class<?> qualifierType;
            try {
                qualifierType = (Class<?>)f.get(Enum.class);
            } catch (Exception e) {
                throw new RuntimeException("Field '" + f + "' cannot be accessed!", e);
// continue;
            }

            for (Field g : qualifierType.getDeclaredFields()) {
                FieldId eann = g.getAnnotation(FieldId.class);

                if (g.getName().contains("ENUM$VALUES")) {
                    continue;
                }

                String none = qualifierType.getName() + "@" + g.getName();

                if (eann != null) {
                    String second = f.getDeclaringClass().getName() + "@" + eann.value();

                    if (fieldIdEnum.containsKey(second)) { throw new RuntimeException(
                            "Duplicate field id '" + second + "' is not allowed!"); }

                    try {
                        fieldIdEnum.put(second, none);
                        enumFieldId.put(none, second);
                    } catch (Exception e) {
                        throw new RuntimeException("Field '" + f + "' cannot be accessed!", e);
                    }
                }

                if (fann != null) {
                    String first = fann.value() + "@" + g.getName();

                    if (fieldIdEnum.containsKey(first)) { throw new RuntimeException(
                            "Duplicate field id '" + first + "' is not allowed!"); }

                    try {
                        fieldIdEnum.put(first, none);
                        enumFieldId.put(none, first);
                    } catch (Exception e) {
                        throw new RuntimeException("Field '" + f + "' cannot be accessed!", e);
                    }
                }

                if (fann != null && eann != null) {
                    String full = fann.value() + "@" + eann.value();

                    if (fieldIdEnum.containsKey(full)) { throw new RuntimeException(
                            "Duplicate field id '" + full + "' is not allowed!"); }

                    try {
                        fieldIdEnum.put(full, none);
                        enumFieldId.put(none, full);
                    } catch (Exception e) {
                        throw new RuntimeException("Field '" + f + "' cannot be accessed!", e);
                    }
                }
            }
        }
    }
}
