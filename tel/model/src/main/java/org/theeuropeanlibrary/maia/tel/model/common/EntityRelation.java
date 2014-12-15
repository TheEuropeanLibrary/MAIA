package org.theeuropeanlibrary.maia.tel.model.common;

import java.util.Objects;
import org.theeuropeanlibrary.maia.common.FieldId;

/**
 * Base class for entity relations.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 10.11.2014
 */
public class EntityRelation {

    @FieldId(1)
    private String id;

    @FieldId(2)
    private String name;
    
    public EntityRelation() {
    }

    public EntityRelation(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EntityRelation other = (EntityRelation) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntityRelation{" + "id=" + id + ", name=" + name + '}';
    }
}
