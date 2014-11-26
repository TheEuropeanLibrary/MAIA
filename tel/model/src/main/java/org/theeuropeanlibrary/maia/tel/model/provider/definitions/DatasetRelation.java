package org.theeuropeanlibrary.maia.tel.model.provider.definitions;

import org.theeuropeanlibrary.maia.common.FieldId;

/**
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 25.11.2014
 */
public class DatasetRelation extends EntityRelation {

    @FieldId(3)
    private String identifier;

    @FieldId(4)
    private String status;

    public DatasetRelation() {
        super();
    }

    public DatasetRelation(String id, String name) {
        super(id, name);
    }

    public DatasetRelation(String id, String name, String identifier, String status) {
        super(id, name);
        this.identifier = identifier;
        this.status = status;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DatasetRelation{" + "id=" + getId() + ", name=" + getName() + "identifier=" + identifier + ", status=" + status + '}';
    }

}
