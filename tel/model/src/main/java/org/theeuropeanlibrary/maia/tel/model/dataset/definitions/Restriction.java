package org.theeuropeanlibrary.maia.tel.model.dataset.definitions;

import org.theeuropeanlibrary.maia.common.FieldId;

/**
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 08.12.2014
 */
public class Restriction {

    @FieldId(1)
    private DistributionFormat format;

    @FieldId(2)
    private String otherFormat;

    @FieldId(3)
    private String terms;

    @FieldId(4)
    private String time;

    @FieldId(5)
    private String duration;

    public Restriction() {
        // default constructor
    }

    public DistributionFormat getFormat() {
        return format;
    }

    public void setFormat(DistributionFormat format) {
        this.format = format;
    }

    public String getOtherFormat() {
        return otherFormat;
    }

    public void setOtherFormat(String otherFormat) {
        this.otherFormat = otherFormat;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Restriction{" + "format=" + format + ", otherFormat=" + otherFormat + ", terms=" + terms + ", time=" + time + ", duration=" + duration + '}';
    }
}
