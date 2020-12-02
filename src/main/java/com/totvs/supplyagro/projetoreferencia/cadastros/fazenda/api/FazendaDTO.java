package com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.api;

import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio.Fazenda;
import lombok.Data;

import java.util.UUID;

@Data
public class FazendaDTO {
    private final UUID id;
    private final String descricao;
    private final String cnpj;
    private final UUID unidadeAdministrativaId;

    public static FazendaDTO from(Fazenda fazenda) {
        return new FazendaDTO(
                fazenda.getId(),
                fazenda.getDescricao(),
                fazenda.getCnpj(),
                fazenda.getUnidadeAdministrativa().getId()
        );
    }
}
