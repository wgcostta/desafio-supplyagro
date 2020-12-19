package com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.api;

import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio.Endereco;
import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio.Fazenda;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class FazendaResponse {
    private final UUID id;
    private final String descricao;
    private final String cnpj;
    private final Set<Endereco> enderecos;

    public static FazendaResponse from(Fazenda fazenda) {
        return new FazendaResponse(
                fazenda.getId(),
                fazenda.getDescricao(),
                fazenda.getCnpj(),
                fazenda.getEnderecos()
        );
    }
}
