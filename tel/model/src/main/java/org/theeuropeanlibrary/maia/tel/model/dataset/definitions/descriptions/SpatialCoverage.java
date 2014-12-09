package org.theeuropeanlibrary.maia.tel.model.dataset.definitions.descriptions;

/**
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 */
public enum SpatialCoverage {

    PARIS("Paris", "");

    private final String place;

    private final String placeLink;

    private SpatialCoverage(String place, String placeLink) {
        this.place = place;
        this.placeLink = placeLink;
    }

    public String getPlace() {
        return place;
    }

    public String getPlaceLink() {
        return placeLink;
    }

}
