package com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.api;

import com.totvs.supplyagro.projetoreferencia.cadastros.comum.api.Unique;
import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio.Endereco;
import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio.Fazenda;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class FazendaRequest {

    @Unique(entidade = Fazenda.class, atributo = "descricao", message = "{Fazenda.descricao.Unique}")
    @NotBlank(message = "{Fazenda.descriocao.NotBlank}")
    private String descricao;

    @CNPJ(message = "{FazendaRequest.cnpj.CNPJ}")
    private String cnpj;

    @NotNull(message = "{Fazenda.unidadeAdministrativaId.NotNull}")
    private Set<Endereco> enderecos;

    public Fazenda toModel() {
        return new Fazenda(descricao, cnpj, enderecos);
    }
}
