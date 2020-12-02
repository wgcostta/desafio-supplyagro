package com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.api;

import com.totvs.supplyagro.projetoreferencia.cadastros.comum.api.Unique;
import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio.Fazenda;
import com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.dominio.UnidadeAdministrativa;
import com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.dominio.UnidadeAdministrativaRepository;
import com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.exceptions.UnidadeAdministrativaNaoEncontradaException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class FazendaRequest {

    @NotNull(message = "{Fazenda.unidadeAdministrativaId.NotNull}")
    private UUID unidadeAdministrativaId;

    @Unique(entidade = Fazenda.class, atributo = "descricao", message = "{Fazenda.descricao.Unique}")
    @NotBlank(message = "{Fazenda.descriocao.NotBlank}")
    private String descricao;

    @CNPJ(message = "{FazendaRequest.cnpj.CNPJ}")
    private String cnpj;

    public Fazenda toModel(UnidadeAdministrativaRepository unidadeAdministrativaRepository) {
        UnidadeAdministrativa unidadeAdministrativa = unidadeAdministrativaRepository.findById(unidadeAdministrativaId).orElseThrow(() -> new UnidadeAdministrativaNaoEncontradaException(unidadeAdministrativaId));
        return new Fazenda(descricao, cnpj, unidadeAdministrativa);
    }
}
