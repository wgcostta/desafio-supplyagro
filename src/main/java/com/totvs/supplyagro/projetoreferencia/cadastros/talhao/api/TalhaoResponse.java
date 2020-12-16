package com.totvs.supplyagro.projetoreferencia.cadastros.talhao.api;

import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio.Fazenda;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.dominio.Talhao;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class TalhaoResponse {
    private UUID id;
    private String codigo;
    private Float area;
    private Integer safra;
    private Integer produtivade;
    private Fazenda fazenda;

    public static TalhaoResponse from(Talhao talhao) {
        return TalhaoResponse.builder()
                .id(talhao.getId())
                .codigo(talhao.getCodigo())
                .area(talhao.getArea())
                .safra(talhao.getSafra())
                .produtivade(talhao.getProdutivade())
                .fazenda(talhao.getFazenda())
                .build();
    }
}
