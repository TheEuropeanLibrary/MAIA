/* BinarySerializer.java - created on Jun 14, 2013, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.json.basetype;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

/**
 * A <code>BaseTypeEncoder</code> for <code>Field</code>
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 22.10.2014
 */
public class DateDeserializer extends JsonDeserializer<Date> {

    private final static Logger log = Logger.getLogger(DateDeserializer.class.getName());

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        String value = jp.getText();
        if (value.contains("y")) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MONTH, Calendar.JANUARY);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            cal.set(Calendar.YEAR, Integer.valueOf(value.replace("y", "")));
            return cal.getTime();
        } else if (value.contains("d")) {
            Calendar cal = Calendar.getInstance();
            try {
                String[] years = value.replace("d", "").split("-");
                cal.set(Calendar.MONTH, Integer.valueOf(years[1]) - 1);
                cal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(years[2]));
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                cal.set(Calendar.YEAR, Integer.valueOf(years[0]));
            } catch (Throwable t) {
                log.warning("Cannot parse day scoped date '" + value + "'!");
            }
            return cal.getTime();
        } else if (value.contains("m")) {
            Calendar cal = Calendar.getInstance();
            try {
                String[] years = value.replace("m", "").split("-");
                cal.set(Calendar.MONTH, Integer.valueOf(years[1]) - 1);
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                cal.set(Calendar.YEAR, Integer.valueOf(years[0]));
            } catch (Throwable t) {
                log.warning("Cannot parse day scoped date '" + value + "'!");
            }
            return cal.getTime();
        } else {
            return new Date(Long.valueOf(value));
        }
    }
}
