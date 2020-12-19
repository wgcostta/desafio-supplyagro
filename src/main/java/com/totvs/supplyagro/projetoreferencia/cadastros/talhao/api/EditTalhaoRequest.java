package com.totvs.supplyagro.projetoreferencia.cadastros.talhao.api;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
public class EditTalhaoRequest {

    @NotNull(message = "o id é inválido.")
    private UUID id;

    @NotNull(message = "A Area é inválido.")
    private Float area;

    @NotNull(message = "O estimativa de produtivadade é inválida.")
    private Integer produtivade;
}
