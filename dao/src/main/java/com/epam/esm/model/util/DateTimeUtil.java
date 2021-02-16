package com.epam.esm.model.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class, consists of methods, working with date and time.
 */
public class DateTimeUtil {

    private DateTimeUtil() {
    }

    /**
     * Gets current date iso.
     *
     * @return the current date iso
     */
    public static String getCurrentDateIso() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}