package com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.api;

import com.totvs.supplyagro.projetoreferencia.cadastros.comum.api.Unique;
import com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.dominio.UnidadeAdministrativa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@ToString
@AllArgsConstructor
public class UnidadeAdministrativaRequest {

    @NotEmpty(message = "{UnidadeAdministrativaRequest.descricao.NotEmpty}")
    @Unique(entidade = UnidadeAdministrativa.class, atributo = "descricao", message = "{UnidadeAdministrativaRequest.descricao.ja.existe}")
    private String descricao;

    @NotEmpty(message = "{UnidadeAdministrativaRequest.municipio.NotEmpty}")
    private String municipio;

    public UnidadeAdministrativa toEntity() {
        return new UnidadeAdministrativa(descricao, municipio);
    }
}
