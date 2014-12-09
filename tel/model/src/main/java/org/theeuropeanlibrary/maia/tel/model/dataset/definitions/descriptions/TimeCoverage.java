package org.theeuropeanlibrary.maia.tel.model.dataset.definitions.descriptions;

/**
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 */
public enum TimeCoverage {

    RENAISSANCE("renaissance", "", "", "");

    private final String time;

    private final String timeLink;

    private final String startDate;

    private final String endDate;

    private TimeCoverage(String time, String timeLink, String startDate, String endDate) {
        this.time = time;
        this.timeLink = timeLink;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getTime() {
        return time;
    }

    public String getTimeLink() {
        return timeLink;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

}
