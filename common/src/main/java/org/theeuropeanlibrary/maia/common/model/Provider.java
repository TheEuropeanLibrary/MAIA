package org.theeuropeanlibrary.maia.common.model;

import java.util.Objects;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a TEL data provider.
 */
@XmlRootElement
public class Provider {

    public Provider() {
    }

    public Provider(final String id) {
        this.id = id;
    }

    /**
     * The provider id.
     */
    private String id;

    /**
     * The provider name.
     */
    private String name;

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
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
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
        final Provider other = (Provider) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "DataProvider{" + "id=" + id + ", name=" + name + '}';
    }
}