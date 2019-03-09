package com.n26.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.n26.entity.request.Transaction;
import com.n26.entity.response.Statistic;
import com.n26.exception.ExpiredTransactionException;
import com.n26.exception.FutureTransactionException;
import com.n26.util.DateUtil;

/**
 * The Class StatisticService.
 */
@Service
public class StatisticService {

    /** The lapse time. */
    private static Long lapseTime = 60000L;

    /** The lock. */
    private Object LOCK = new Object();

    /** The Constant DELETED_TRANSACTIONS. */
    private static final String DELETED_TRANSACTIONS = "Deleted all transactions.";

    /** The statistics. */
    private Map<Long, Statistic> statistics = new ConcurrentHashMap<Long, Statistic>();

    /** The statistic timestamps. */
    private Queue<Long> statisticTimestamps = new PriorityBlockingQueue<Long>();;

    /**
     * Adds the.
     *
     * @param transaction the transaction
     * @throws ExpiredTransactionException the invalid transaction exception
     * @throws FutureTransactionException
     */
    public synchronized void add( Transaction transaction ) throws ExpiredTransactionException, FutureTransactionException {
        Long currentTimestamp = DateUtil.convertToTimeStamp( LocalDateTime.now() );
        Long transactionTimestamp = DateUtil.convertToTimeStamp( transaction.getTimestamp() );

        synchronized ( LOCK ) {
            /*
             * calculates the statistics for the entire time window while fetching, it checks the current timestamp and
             * get the statistics
             **/
            for ( Long i = currentTimestamp; i < transactionTimestamp + lapseTime; i+=1000 ) {
                Statistic statistic = this.statistics.get( i );

                if ( statistic == null ) {

                    statistic = createStatisticsObject( i );

                    statistics.put( i, statistic );
                    
                    this.statisticTimestamps.add(i);

                }

                if ( transaction.getAmount().compareTo( new BigDecimal( statistic.getMax() ) ) > 0 )
                    statistic.setMax( transaction.getAmount() );
                if ( transaction.getAmount().compareTo( new BigDecimal( statistic.getMin() ) ) < 0 )
                    statistic.setMin( transaction.getAmount() );

                statistic.setSum( new BigDecimal( statistic.getSum() ).add( transaction.getAmount() ) );
                statistic.setCount( statistic.getCount() + 1 );
                statistic.setAvg( new BigDecimal( statistic.getSum() ).divide( new BigDecimal( statistic.getCount() ), BigDecimal.ROUND_HALF_UP ) );
            }
        }

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
        return statistic;
    }

    /**
     * Gets the statistics.
     *
     * @return the statistics
     */
    public Statistic getStatistics() {

        Long currentTimestamp = DateUtil.convertToTimeStamp( LocalDateTime.now() );

        Statistic statistic = statistics.get( currentTimestamp );
        if ( null == statistic ) {
            statistic = new Statistic();
        }

        return statistic;
    }

    /**
     * Delete.
     *
     * @return the string
     */
    public String delete() {
        statistics.clear();
        return DELETED_TRANSACTIONS;
    }

    /**
     * Removes the expired statistics.
     */
    @Scheduled ( fixedDelayString = "10000" )
    private void removeExpiredStatistics() {
        Long currentTimestamp = DateUtil.convertToTimeStamp( LocalDateTime.now() );
        if ( this.statisticTimestamps.isEmpty() || this.statisticTimestamps.peek() >= currentTimestamp )
            return;
        synchronized ( LOCK ) {
            while ( !this.statisticTimestamps.isEmpty() && this.statisticTimestamps.peek() < currentTimestamp ) {
                Long key = this.statisticTimestamps.poll();
                this.statistics.remove( key );
            }
        }
    }
}
