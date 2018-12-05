package com.n26.controller;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.n26.exception.FutureTransactionException;
import com.n26.entity.request.Transaction;
import com.n26.exception.ExpiredTransactionException;
import com.n26.service.StatisticService;
import com.n26.validator.CommonValidator;

@RunWith ( MockitoJUnitRunner.class )
public class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private StatisticService statisticService;

    @Mock
    private CommonValidator validator;

    @Test
    public void testPost() throws ExpiredTransactionException, FutureTransactionException {
        Transaction transaction = new Transaction();
        transaction.setAmount( BigDecimal.TEN );
        transaction.setTimestamp( convertToLocalDateTime( Instant.now().getEpochSecond() ) );
        Mockito.doNothing().when( statisticService ).add( transaction );
        ResponseEntity<Transaction> response = transactionController.post( transaction );
        Assert.assertEquals( HttpStatus.CREATED, response.getStatusCode() );
    }

    @Test
    public void testThrowInvalidTransaction() throws ExpiredTransactionException, FutureTransactionException {
        Transaction transaction = new Transaction();
        transaction.setTimestamp( convertToLocalDateTime( Instant.now().getEpochSecond() ) );
        Mockito.doThrow( ExpiredTransactionException.class ).when( statisticService ).add( transaction );
        ResponseEntity<Transaction> response = transactionController.post( transaction );
        Assert.assertEquals( HttpStatus.NO_CONTENT, response.getStatusCode() );
    }

    @Test
    public void testDelete() throws ExpiredTransactionException {
        ResponseEntity<String> response = transactionController.deleteAll();
        Assert.assertEquals( HttpStatus.NO_CONTENT, response.getStatusCode() );
    }

    /**
     * Convert to local date time.
     *
     * @param timestamp the timestamp
     * @return the local date time
     */
    private LocalDateTime convertToLocalDateTime( Long timestamp ) {
        return LocalDateTime.ofInstant( Instant.ofEpochMilli( timestamp * 1000 ), TimeZone.getDefault().toZoneId() );
    }
}
