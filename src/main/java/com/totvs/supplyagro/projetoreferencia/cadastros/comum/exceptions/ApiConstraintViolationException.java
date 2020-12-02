package com.totvs.supplyagro.projetoreferencia.cadastros.comum.exceptions;

import com.totvs.tjf.api.context.stereotype.error.ApiBadRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@ApiBadRequest("ApiConstraintViolation")
public class ApiConstraintViolationException extends ConstraintViolationException {

    public ApiConstraintViolationException(Set<? extends ConstraintViolation<?>> constraintViolations) {
            super(constraintViolations);
    }
}

