package com.totvs.supplyagro.projetoreferencia.cadastros.talhao.api.exceptions;

import com.totvs.tjf.api.context.stereotype.ApiErrorParameter;
import com.totvs.tjf.api.context.stereotype.error.ApiNotFound;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;
@ApiNotFound("TalhaoNaoEncontrado")
@AllArgsConstructor
public class TalhaoNaoEncontradoException extends RuntimeException {
    @Getter
    @ApiErrorParameter
    public final UUID id;
}
