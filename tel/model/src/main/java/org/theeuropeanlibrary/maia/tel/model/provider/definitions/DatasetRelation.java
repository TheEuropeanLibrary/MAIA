package org.theeuropeanlibrary.maia.tel.model.provider.definitions;

import org.theeuropeanlibrary.maia.common.FieldId;

/**
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 25.11.2014
 */
public class DatasetRelation extends EntityRelation {

    @FieldId(3)
    private String status;

    public DatasetRelation() {
        super();
    }

    public DatasetRelation(String id, String name) {
        super(id, name);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
