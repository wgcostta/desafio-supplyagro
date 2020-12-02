package com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.api;

import com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.dominio.UnidadeAdministrativa;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UnidadeAdministrativaDTO {
    private UUID id;
    private String descricao;
    private String municipio;

    public static UnidadeAdministrativaDTO from(UnidadeAdministrativa unidade) {
        return new UnidadeAdministrativaDTO(unidade.getId(),
                unidade.getDescricao(),
                unidade.getMunicipio());
    }
}
