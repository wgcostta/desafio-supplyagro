package com.totvs.supplyagro.projetoreferencia.cadastros.talhao.api;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class EditTalhaoRequest {
    @NotNull(message = "A Area é inválido.")
    private Float area;

    @NotNull(message = "O estimativa de produtivadade é inválida.")
    private Integer produtivade;
}
