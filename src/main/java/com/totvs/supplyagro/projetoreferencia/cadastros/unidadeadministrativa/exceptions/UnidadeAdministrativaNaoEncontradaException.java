package com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.exceptions;

import com.totvs.tjf.api.context.stereotype.ApiErrorParameter;
import com.totvs.tjf.api.context.stereotype.error.ApiNotFound;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@ApiNotFound("UnidadeAdministrativaNaoEncontrada")
public class UnidadeAdministrativaNaoEncontradaException extends RuntimeException {
    @Getter
    @ApiErrorParameter
    private final UUID id;
}
