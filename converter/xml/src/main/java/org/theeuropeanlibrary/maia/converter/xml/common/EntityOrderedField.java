package org.theeuropeanlibrary.maia.converter.xml.common;

import org.theeuropeanlibrary.maia.common.TKey;

/**
 * A temporary holder for fields to be added to a mdr. Used to keep the order
 * when desirializing from XML
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @since 4 de Out de 2012
 */
@SuppressWarnings("rawtypes")
public class EntityOrderedField implements Comparable<EntityOrderedField> {

    private final TKey tkey;
    private final Object value;
    private final Enum[] qualifiers;
    private final Double orderIndex;

    /**
     * Creates a new instance of this class.
     *
     * @param tkey
     * @param value
     * @param qualifiers
     * @param double1
     */
    public EntityOrderedField(TKey tkey, Object value, Enum[] qualifiers, Double double1) {
        this.tkey = tkey;
        this.value = value;
        this.qualifiers = qualifiers;
        this.orderIndex = double1;
    }

    /**
     * @return Tkey
     */
    public TKey getTkey() {
        return tkey;
    }

    /**
     * @return Value
     */
    public Object getValue() {
        return value;
    }

    /**
     * @return Qualifiers
     */
    public Enum[] getQualifiers() {
        return qualifiers;
    }

    /**
     * @return OrderIndex
     */
    public Double getOrderIndex() {
        return orderIndex;
    }

    @Override
    public int compareTo(EntityOrderedField arg0) {
        return (int) (orderIndex * 100000 - arg0.orderIndex * 100000);
    }
}
