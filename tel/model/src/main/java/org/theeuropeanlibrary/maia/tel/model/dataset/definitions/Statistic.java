package org.theeuropeanlibrary.maia.tel.model.dataset.definitions;

import org.theeuropeanlibrary.maia.common.FieldId;

/**
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 08.12.2014
 */
public class Statistic {

    @FieldId(1)
    private String type;

    @FieldId(2)
    private String status;

    @FieldId(3)
    private String url;

    public Statistic() {
        // default constructor
    }

    public Statistic(String type, String status, String url) {
        this.type = type;
        this.status = status;
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Statistic{" + "type=" + type + ", status=" + status + ", url=" + url + '}';
    }

}
