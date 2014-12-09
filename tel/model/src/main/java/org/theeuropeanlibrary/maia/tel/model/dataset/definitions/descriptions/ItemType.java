package org.theeuropeanlibrary.maia.tel.model.dataset.definitions.descriptions;

/**
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 */
public enum ItemType {

    BLUE_PRINT("TEXT", "Blue Print", "http://dbpedia.org/page/Blueprint", "", "http://id.loc.gov/authorities/subjects/sh85111445");

    private final String objectType;

    private final String type;

    private final String dbpediaLink;

    private final String freebaseLink;

    private final String locLink;

    private ItemType(String objectType, String type, String dbpediaLink, String freebaseLink, String locLink) {
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
