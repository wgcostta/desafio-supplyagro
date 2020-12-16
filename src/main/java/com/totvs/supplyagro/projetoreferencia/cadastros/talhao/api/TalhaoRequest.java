package com.totvs.supplyagro.projetoreferencia.cadastros.talhao.api;

import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio.Fazenda;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.dominio.Talhao;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
public class TalhaoRequest {

    @NotNull(message = "O Código é inválido.")
    private String codigo;

    @NotNull(message = "A Area é inválido.")
    private Float area;

    @NotNull(message = "O Safra é inválido.")
    private Integer safra;

    @NotNull(message = "O estimativa de produtivadade é inválida.")
    private Integer produtivade;

    @NotNull(message = "A Fazenda é inválida.")
    private UUID idFazenda;

    public Talhao toModel() {
        return Talhao.builder()
                .codigo(codigo)
                .area(area)
                .safra(safra)
                .produtivade(produtivade)
                .build();
    }
}
