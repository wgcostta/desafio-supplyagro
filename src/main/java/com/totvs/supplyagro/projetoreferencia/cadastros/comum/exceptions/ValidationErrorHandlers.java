package com.totvs.supplyagro.projetoreferencia.cadastros.comum.exceptions;

import com.totvs.tjf.api.context.v2.response.ApiErrorResponse;
import com.totvs.tjf.api.context.v2.response.ApiErrorResponseDetail;
import com.totvs.tjf.i18n.I18nService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ValidationErrorHandlers {

    @Autowired
    private I18nService i18nService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErrorResponse handleValidationErrors(MethodArgumentNotValidException exception) {
        List<ApiErrorResponseDetail> detalhes = new ArrayList<>();
        List<ObjectError> erros = exception.getBindingResult().getAllErrors();
        for (ObjectError erro : erros) {
            if (erro instanceof FieldError) {
                FieldError fieldError = (FieldError) erro;
                detalhes.add(new ApiErrorResponseDetail(fieldError.getCode(), fieldError.getDefaultMessage(), null, null));
            } else {
                String mensagem = i18nService.getMessage(erro.getCode(), erro.getArguments());
                detalhes.add(new ApiErrorResponseDetail(erro.getCode(), mensagem, null, null));
            }
        }
        String titulo = i18nService.getMessage("ValidatorConstraintException.message");
        String detalhe = i18nService.getMessage("ValidatorConstraintException.detail");
        return new ApiErrorResponse("ValidatorConstraintException", titulo, detalhe, null, Collections.unmodifiableList(detalhes));
    }
}
