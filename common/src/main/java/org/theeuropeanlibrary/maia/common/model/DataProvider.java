package org.theeuropeanlibrary.maia.common.model;

import java.util.Objects;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a TEL data provider.
 */
@XmlRootElement
public class DataProvider {

	public DataProvider() {
	}
	
	public DataProvider(final String id) {
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
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.name);
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
        final DataProvider other = (DataProvider) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
    	return super.toString();
    }
}
