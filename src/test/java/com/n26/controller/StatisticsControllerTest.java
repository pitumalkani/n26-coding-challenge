package com.n26.controller;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.n26.entity.response.Statistic;
import com.n26.service.StatisticService;

@RunWith ( MockitoJUnitRunner.class )
public class StatisticsControllerTest {

    /** The statistic service. */
    @InjectMocks
    private StatisticsController statisticsController;

    @Mock
    private StatisticService statisticService;

    @Test
    public void testGetStatistics() {
        Statistic s = createStatisticsObject( Instant.now().getEpochSecond() );
        when( statisticService.getStatistics() ).thenReturn( s );
        ResponseEntity<Statistic> response = statisticsController.getStatistics();
        Assert.assertEquals( HttpStatus.OK, response.getStatusCode() );
    }

    /**
     * Creates the statistics object.
     *
     * @param timestamp the timestamp
     * @return the statistic
     */
    private Statistic createStatisticsObject( Long timestamp ) {
        Statistic statistic = new Statistic();
        statistic.setMax( new BigDecimal( Double.MIN_VALUE ) );
        statistic.setMin( new BigDecimal( Double.MAX_VALUE ) );
        statistic.setSum( BigDecimal.ZERO );
        statistic.setCount( 0l );
        return statistic;
    }
}
