package com.n26.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.TimeZone;

/**
 * The Class DateUtil.
 */
public class DateUtil {

    /**
     * Convert to time stamp.
     *
     * @param localDateTime the local date time
     * @return the long
     */
    public static Long convertToTimeStamp( LocalDateTime localDateTime ) {
        return localDateTime.toEpochSecond( ZoneOffset.UTC ) * 1000;
    }

    public static LocalDateTime convertToLocalDateTime( LocalDateTime localDateTime ) {
        Long timestamp = localDateTime.toEpochSecond( ZoneOffset.UTC ) * 1000;
        return LocalDateTime.ofInstant( Instant.ofEpochMilli( timestamp ), TimeZone.getDefault().toZoneId() );
    }
}
