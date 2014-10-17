/* EnumFieldEncoder.java - created on 6 de Abr de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.xml.basetype;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

/**
 * A <code>BaseTypeEncoder</code> for <code>Date</code>
 * 
 * @author Nuno Freire (nfreire@gmail.com)
 * @date 6 de Abr de 2011
 */
public class DateEncoder implements BaseTypeEncoder<Date> {
    private final static Logger log = Logger.getLogger(DateEncoder.class.getName());

    @Override
    public Date decodeFromString(String value) throws Exception {
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

    @Override
    public String encodeToString(Date value) throws Exception {
        return String.valueOf(value.getTime());
    }
}
