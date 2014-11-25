package org.theeuropeanlibrary.maia.tel.model.common;

import org.theeuropeanlibrary.maia.common.FieldId;


/**
 * Defining a URL link to an image, digital object, online catalog, etc. The
 * type of link (image, digital object, online catalog) is defined externally to
 * the class in a qualifier
 *
 * @author Markus Muhr (markus.muhr@kb.nl)
 * @since Mar 18, 2011
 */
public class BasicLink {

    /**
     * uniform resource location
     */
    @FieldId(1)
    protected String url;

    /**
     * Creates a new instance of this class.
     */
    public BasicLink() {
        super();
    }

    /**
     * Creates a new instance of this class.
     *
     * @param url uniform resource identification to get to the actual value
     */
    public BasicLink(String url) {
        if (url == null) {
            throw new IllegalArgumentException("Argument 'url' should not be null!");
        }
        this.url = url;
    }

    /**
     * @return uniform resource identification to get to the actual value
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url uniform resource identification to get to the actual value
     */
    public void setUrl(String url) {
        if (url == null) {
            throw new IllegalArgumentException("Argument 'url' should not be null!");
        }
        this.url = url;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BasicLink other = (BasicLink) obj;
        if (url == null) {
            if (other.url != null) {
                return false;
            }
        } else if (!url.equals(other.url)) {
            return false;
        }
        return true;
    }
}
