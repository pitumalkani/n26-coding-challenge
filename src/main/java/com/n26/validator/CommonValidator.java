package com.n26.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.n26.entity.request.presentation.Transaction;
import com.n26.exception.ExpiredTransactionException;

@Component
public class CommonValidator {
    /** The validator. */
    @Autowired
    private Validator validator;

    public boolean validate( Transaction transaction ) throws ExpiredTransactionException {
        boolean flag = false;
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate( transaction );
        if ( !constraintViolations.isEmpty() ) {
            flag = true;
        }
        return flag;
    }

}
