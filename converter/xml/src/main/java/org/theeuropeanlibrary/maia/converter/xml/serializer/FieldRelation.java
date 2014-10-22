/* FieldRelation.java - created on 18/12/2013, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.xml.serializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds data a about relations in a MDR, for parsing the XML
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @since 18/12/2013
 */
public class FieldRelation {

    private int sourceOrdIndex;
    private int targetOrdIndex;
    private List<Enum<?>> qualifiers;

    /**
     * Creates a new instance of this class.
     *
     * @param sourceOrdIndex
     * @param targetOrdIndex
     */
    public FieldRelation(int sourceOrdIndex, int targetOrdIndex) {
        super();
        this.qualifiers = new ArrayList<>(2);
        this.sourceOrdIndex = sourceOrdIndex;
        this.targetOrdIndex = targetOrdIndex;
    }

    /**
     * Returns the sourceOrdIndex.
     *
     * @return the sourceOrdIndex
     */
    public final int getSourceOrdIndex() {
        return sourceOrdIndex;
    }

    /**
     * Sets the sourceOrdIndex
     *
     * @param sourceOrdIndex the sourceOrdIndex to set
     */
    public final void setSourceOrdIndex(int sourceOrdIndex) {
        this.sourceOrdIndex = sourceOrdIndex;
    }

    /**
     * Returns the targetOrdIndex.
     *
     * @return the targetOrdIndex
     */
    public final int getTargetOrdIndex() {
        return targetOrdIndex;
    }

    /**
     * Sets the targetOrdIndex
     *
     * @param targetOrdIndex the targetOrdIndex to set
     */
    public final void setTargetOrdIndex(int targetOrdIndex) {
        this.targetOrdIndex = targetOrdIndex;
    }

    /**
     * Returns the qualifiers.
     *
     * @return the qualifiers
     */
    public final List<Enum<?>> getQualifiers() {
        return qualifiers;
    }

    /**
     * Sets the qualifiers
     *
     * @param qualifiers the qualifiers to set
     */
    public final void setQualifiers(List<Enum<?>> qualifiers) {
        this.qualifiers = qualifiers;
    }

    /**
     * @return qualifiers
     */
    public Enum<?>[] getQualifierArray() {
        return qualifiers.toArray(new Enum[qualifiers.size()]);

    }
}
