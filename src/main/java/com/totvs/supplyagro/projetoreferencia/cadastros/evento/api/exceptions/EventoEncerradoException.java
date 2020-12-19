package com.totvs.supplyagro.projetoreferencia.cadastros.evento.api.exceptions;

import com.totvs.tjf.api.context.stereotype.ApiErrorParameter;
import com.totvs.tjf.api.context.stereotype.error.ApiBadRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

@ApiBadRequest("EventoEncerrado")
@AllArgsConstructor
public class EventoEncerradoException extends RuntimeException  {
    @Getter
    @ApiErrorParameter
    public final String codigo;
}
