package com.n26.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.n26.entity.request.presentation.Transaction;
import com.n26.exception.ExpiredTransactionException;
import com.n26.exception.FutureTransactionException;
import com.n26.exception.InvalidTransactionException;
import com.n26.service.StatisticService;
import com.n26.service.TransactionService;
import com.n26.validator.CommonValidator;

/**
 * The Class TransactionController.
 */
@RestController
@RequestMapping ( value = "/transactions" )
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    StatisticService statisticService;

    @Autowired
    private CommonValidator validator;

    /**
     * Post.
     *
     * @param transaction the transaction
     * @return the response entity
     * @throws FutureTransactionException
     * @throws InvalidTransactionException
     */
    @RequestMapping ( method = RequestMethod.POST )
    public ResponseEntity<Transaction> post( @RequestBody Transaction transaction ) throws FutureTransactionException, InvalidTransactionException {
        try {
            if ( validator.validate( transaction ) ) {
                return new ResponseEntity<Transaction>( HttpStatus.UNPROCESSABLE_ENTITY );
            }
            transactionService.process( transaction );
            ResponseEntity<Transaction> response = new ResponseEntity<Transaction>( transaction, HttpStatus.CREATED );
            return response;
        } catch ( ExpiredTransactionException e ) {
            return new ResponseEntity<Transaction>( HttpStatus.NO_CONTENT );
        } catch ( FutureTransactionException | InvalidTransactionException e ) {
            return new ResponseEntity<Transaction>( HttpStatus.UNPROCESSABLE_ENTITY );
        }

    }

    /**
     * Delete all.
     *
     * @return the response entity
     */
    @RequestMapping ( method = RequestMethod.DELETE )
    public ResponseEntity<String> deleteAll() {
        return new ResponseEntity<String>( statisticService.delete(), HttpStatus.NO_CONTENT );
    }

}
