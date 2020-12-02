package com.totvs.supplyagro.projetoreferencia.cadastros.comum.exceptions;

import com.totvs.tjf.api.context.stereotype.error.ApiBadRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@ApiBadRequest
public class ValidatorConstraintException extends ConstraintViolationException {

    public ValidatorConstraintException(Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(constraintViolations);
    }
}
