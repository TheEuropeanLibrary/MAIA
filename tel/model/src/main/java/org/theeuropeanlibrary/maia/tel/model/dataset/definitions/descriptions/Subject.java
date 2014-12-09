package org.theeuropeanlibrary.maia.tel.model.dataset.definitions.descriptions;

/**
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 */
public enum Subject {

    MEDIEVAL_HISTORY("Medieval History", "", "http://dbpedia.org/page/Middle_Ages", "", "");

    private final String objectType;

    private final String type;

    private final String dbpediaLink;

    private final String freebaseLink;

    private final String locLink;

    private Subject(String objectType, String type, String dbpediaLink, String freebaseLink, String locLink) {
        this.objectType = objectType;
        this.type = type;
        this.dbpediaLink = dbpediaLink;
        this.freebaseLink = freebaseLink;
        this.locLink = locLink;
    }

    public String getObjectType() {
        return objectType;
    }

    public String getType() {
        return type;
    }

    public String getDbpediaLink() {
        return dbpediaLink;
    }

    public String getFreebaseLink() {
        return freebaseLink;
    }

    public String getLocLink() {
        return locLink;
    }
}
