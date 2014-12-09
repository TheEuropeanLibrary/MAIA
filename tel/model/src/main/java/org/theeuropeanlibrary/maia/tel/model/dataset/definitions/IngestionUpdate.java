package org.theeuropeanlibrary.maia.tel.model.dataset.definitions;

import org.theeuropeanlibrary.maia.common.FieldId;

/**
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 08.12.2014
 */
public class IngestionUpdate {

    @FieldId(1)
    private String type;

    @FieldId(2)
    private String date;

    @FieldId(3)
    private String number;

    public IngestionUpdate() {
        // default constructor
    }
    
    public IngestionUpdate(String type, String date, String number) {
        this.type = type;
        this.date = date;
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "IngestionUpdate{" + "type=" + type + ", date=" + date + ", number=" + number + '}';
    }

}
