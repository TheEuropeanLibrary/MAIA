package org.theeuropeanlibrary.maia.tel.model.dataset.definitions;

import org.theeuropeanlibrary.maia.common.FieldId;

/**
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 08.12.2014
 */
public class License {

    @FieldId(1)
    private LicenseType source;

    @FieldId(2)
    private String otherSource;

    @FieldId(3)
    private LicenseType distribution;

    @FieldId(4)
    private String furtherInformation;

    public License() {
        // default constructor
    }

    public LicenseType getSource() {
        return source;
    }

    public void setSource(LicenseType source) {
        this.source = source;
    }

    public String getOtherSource() {
        return otherSource;
    }

    public void setOtherSource(String otherSource) {
        this.otherSource = otherSource;
    }

    public LicenseType getDistribution() {
        return distribution;
    }

    public void setDistribution(LicenseType distribution) {
        this.distribution = distribution;
    }

    public String getFurtherInformation() {
        return furtherInformation;
    }

    public void setFurtherInformation(String furtherInformation) {
        this.furtherInformation = furtherInformation;
    }

    @Override
    public String toString() {
        return "License{" + "source=" + source + ", otherSource=" + otherSource + ", distribution=" + distribution + ", furtherInformation=" + furtherInformation + '}';
    }

}
