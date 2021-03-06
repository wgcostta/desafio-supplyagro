package com.totvs.supplyagro.projetoreferencia.cadastros.talhao.api.exceptions;

import com.totvs.tjf.api.context.stereotype.ApiErrorParameter;
import com.totvs.tjf.api.context.stereotype.error.ApiBadRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

@ApiBadRequest("ComponenteDuplicado")
@AllArgsConstructor
public class TalhaoDuplicadoFazendaException extends RuntimeException {

    @Getter
    @ApiErrorParameter
    public final String codigo;

}
