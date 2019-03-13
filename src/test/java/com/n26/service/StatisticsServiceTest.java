package com.n26.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.n26.entity.request.Transaction;
import com.n26.entity.response.Statistic;
import com.n26.exception.ExpiredTransactionException;
import com.n26.exception.FutureTransactionException;

@RunWith ( MockitoJUnitRunner.class )
public class StatisticsServiceTest {

    @InjectMocks
    private StatisticService statisticService;


    @Test
    public void testGetStatistics() throws ExpiredTransactionException, FutureTransactionException {
        LocalDateTime dateTimeObject = LocalDateTime.now();
        Transaction t1 = new Transaction();
        t1.setAmount( new BigDecimal( 30.0 ) );
        t1.setTimestamp( dateTimeObject );
        statisticService.add( t1 );

        Transaction t2 = new Transaction();
        t2.setAmount( new BigDecimal( 40.0 ) );
        t2.setTimestamp( dateTimeObject );
        statisticService.add( t2 );

        Transaction t3 = new Transaction();
        t3.setAmount( new BigDecimal( 50.0 ) );
        t3.setTimestamp( dateTimeObject );
        statisticService.add( t3 );

        Statistic statistic = statisticService.getStatistics();
        Assert.assertEquals( Long.valueOf( 3l ), statistic.getCount() );
        assertEquals( format( new BigDecimal( 30.0 ) ).toString(), statistic.getMin() );
        assertEquals( format( new BigDecimal( 50.0 ) ).toString(), statistic.getMax() );
        assertEquals( format( new BigDecimal( 120.0 ) ).toString(), statistic.getSum() );
        assertEquals( format( new BigDecimal( 40.0 ) ).toString(), statistic.getAvg() );

    }

    @Test
    public void testDelete() throws ExpiredTransactionException {
        Assert.assertEquals( "Deleted all transactions.", statisticService.delete() );
    }

    /**
     * Format.
     *
     * @param value the value
     * @return the big decimal
     */
    private BigDecimal format( BigDecimal value ) {
        value = value.setScale( 2, BigDecimal.ROUND_HALF_UP );
        return value;
    }
}
