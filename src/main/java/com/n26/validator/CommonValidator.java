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

@Component
public class CommonValidator {
    /** The validator. */
    @Autowired
    private Validator validator;

    public boolean validate( Transaction transaction ) throws ExpiredTransactionException {
        boolean flag = false;
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate( transaction );
        try {
            if ( !constraintViolations.isEmpty() ) flag = true;
            LocalDateTime.parse( transaction.getTimestamp().toString(), DateTimeFormatter.ISO_DATE_TIME );
        } catch ( DateTimeParseException ex ) {
            flag = true;
        }
        return flag;
    }
}
