package com.n26.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith ( MockitoJUnitRunner.class )
public class DateUtilTest {

    @Test
    public void testConvertToTimeStamp() {
        Long timeStamp = DateUtil.convertToTimeStamp( LocalDateTime.parse( "2018-12-02T19:52:52", DateTimeFormatter.ISO_DATE_TIME ) );
        Assert.assertEquals( Long.valueOf( 1543780372000l ), timeStamp );
    }
    @Test
    public void convertToLocalDateTime() {
            LocalDateTime date = DateUtil.convertToLocalDateTime( LocalDateTime.parse( "2018-12-02T19:52:52", DateTimeFormatter.ISO_DATE_TIME ));
            Assert.assertEquals(LocalDateTime.of(2018, 12, 03, 01, 22, 52), date);
    }
}
