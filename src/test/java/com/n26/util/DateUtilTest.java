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
        Long timeStamp = DateUtil.convertToTimeStamp( LocalDateTime.parse( "2019-03-09T09:08:50", DateTimeFormatter.ISO_DATE_TIME ) );
        Assert.assertEquals( Long.valueOf( 1552122530000l ), timeStamp );
    }
    @Test
    public void convertToLocalDateTime() {
            LocalDateTime date = DateUtil.convertToLocalDateTime( LocalDateTime.parse( "2019-03-03T12:45:50", DateTimeFormatter.ISO_DATE_TIME ));
            Assert.assertEquals(LocalDateTime.of(2019, 03, 03, 12, 45, 50), date);
    }
}
