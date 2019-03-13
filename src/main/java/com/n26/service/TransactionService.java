package com.n26.service;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.n26.entity.request.Transaction;
import com.n26.exception.ExpiredTransactionException;
import com.n26.exception.FutureTransactionException;
import com.n26.exception.InvalidTransactionException;

@Service
public class TransactionService {

    /** The statis service. */
    @Autowired
    private StatisticService statisticService;

    public Transaction process( com.n26.entity.request.presentation.Transaction t ) throws ExpiredTransactionException, FutureTransactionException, InvalidTransactionException {
        Transaction transaction = new Transaction();
        try {
            transaction.setAmount( new BigDecimal( t.getAmount() ) );
            transaction.setTimestamp( LocalDateTime.parse( t.getTimestamp().toString(), DateTimeFormatter.ISO_DATE_TIME ) );
            statisticService.add( transaction );

        } catch ( DateTimeException | NumberFormatException ex ) {
            throw new InvalidTransactionException();
        }
        return transaction;
    }
}
