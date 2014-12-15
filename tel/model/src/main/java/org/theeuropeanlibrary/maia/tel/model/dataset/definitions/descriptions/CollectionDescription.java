package org.theeuropeanlibrary.maia.tel.model.dataset.definitions.descriptions;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.theeuropeanlibrary.maia.common.FieldId;

/**
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 08.12.2014
 */
public class CollectionDescription {

	@FieldId(1)
	private String title;

	@FieldId(2)
	private String description;

	public CollectionDescription() {
		// default constructor
	}

	public CollectionDescription(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
    public int hashCode() {
		
        return new HashCodeBuilder(8574, 3243).
            append(title).
            append(description).
            toHashCode();
    }

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof CollectionDescription))
			return false;
		if (obj == this)
			return true;

		CollectionDescription c = (CollectionDescription) obj;
		
		return new EqualsBuilder().
	            append(c.getTitle(), this.title).
	            append(c.getDescription(), this.description).
	            isEquals();
	}

	@Override
	public String toString() {
		return "CollectionDescription{" + "title=" + title + ", description="
				+ description + '}';
	}

}
