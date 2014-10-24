/* FieldId.java - created on 5 de Abr de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a field should be persisted and specifies the field identifier
 * within the class used by the Converters for persisting the entity objects
 * into different representations.
 *
 * @author Nuno Freire (nuno.freire@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldId {

    /**
     * @return the field identifier within the class
     */
    int value();
}
