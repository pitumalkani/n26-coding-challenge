package com.n26.validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.n26.entity.request.Transaction;
import com.n26.exception.ExpiredTransactionException;
import com.n26.exception.FutureTransactionException;
import com.n26.util.DateUtil;

@Component
public class CommonValidator {
    /** The validator. */
    @Autowired
    private Validator validator;
    
    /** The lapse time. */
    private static Long lapseTime = 60000L;

    public boolean validate( Transaction transaction ) throws ExpiredTransactionException, FutureTransactionException {
        boolean flag = false;
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate( transaction );
        try {
            if ( !constraintViolations.isEmpty() ) flag = true;
            LocalDateTime.parse( transaction.getTimestamp().toString(), DateTimeFormatter.ISO_DATE_TIME );
            
            Long currentTimestamp = DateUtil.convertToTimeStamp( LocalDateTime.now() );
            Long transactionTimestamp = DateUtil.convertToTimeStamp( transaction.getTimestamp() );

            if ( transactionTimestamp + lapseTime < currentTimestamp )
                throw new ExpiredTransactionException();
            if ( currentTimestamp + lapseTime < transactionTimestamp )
                throw new FutureTransactionException();
            
        } catch ( DateTimeParseException ex ) {
            flag = true;
        }
        return flag;
    }
}
