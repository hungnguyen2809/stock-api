package com.hungnv28.core.utils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatterUtil {
    public final static DateTimeFormatter DDMMYYYY = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public final static DateTimeFormatter DDMMYYYYHHmmss = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    public final static DateTimeFormatter YYYYMMDD = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    public final static DateTimeFormatter YYYYMMDDHHmmss = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static LocalDate parseDate(String input, DateTimeFormatter formatter) {
        return LocalDate.parse(input, formatter);
    }

    public static LocalDateTime parseDateTime(String input, DateTimeFormatter formatter) {
        return LocalDateTime.parse(input, formatter);
    }

    public static String formatTimestamp(Timestamp timestamp, DateTimeFormatter formatter){
        if (timestamp == null) return "";
        return timestamp.toLocalDateTime().format(formatter);
    }
}
