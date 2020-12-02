package com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.exceptions;

import com.totvs.tjf.api.context.stereotype.ApiError;
import com.totvs.tjf.api.context.stereotype.ApiErrorParameter;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@ApiError("UnidadeAdministrativaDesabilitada")
@AllArgsConstructor
public class UnidadeAdministrativaDesabilitadaException extends RuntimeException {

    @Getter
    @ApiErrorParameter
    private final UUID id;
}
