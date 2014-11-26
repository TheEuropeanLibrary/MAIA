package org.theeuropeanlibrary.maia.common.definitions;

import org.theeuropeanlibrary.maia.common.AbstractEntity;

/**
 * Implements {@link Record} holding all information in memory.
 *
 * @param <ID> unique ID
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public class Record<ID> extends AbstractEntity<ID> {

    /**
     * the dataset that is responsible for this record
     */
    private Dataset<ID> dataset;

    /**
     * @return the dataset that is responsible for this record
     */
    public Dataset<ID> getDataset() {
        return dataset;
    }

    /**
     * @param dataset the dataset that is responsible for this record
     */
    public void setDataset(Dataset<ID> dataset) {
        this.dataset = dataset;
    }

    @Override
    public AbstractEntity<ID> createInstance() {
        return new Record<>();
    }
}
