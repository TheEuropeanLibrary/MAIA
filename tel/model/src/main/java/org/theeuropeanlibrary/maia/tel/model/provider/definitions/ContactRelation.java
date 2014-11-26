package org.theeuropeanlibrary.maia.tel.model.provider.definitions;

import org.theeuropeanlibrary.maia.common.FieldId;

/**
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 25.11.2014
 */
public class ContactRelation extends EntityRelation {

    @FieldId(3)
    private String role;

    @FieldId(4)
    private String email;

    public ContactRelation() {
        super();
    }

    public ContactRelation(String id, String name) {
        super(id, name);
    }

    public ContactRelation(String id, String name, String role, String email) {
        super(id, name);
        this.role = role;
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ContactRelation{" + "id=" + getId() + ", name=" + getName() + "role=" + role + ", email=" + email + '}';
    }

}
