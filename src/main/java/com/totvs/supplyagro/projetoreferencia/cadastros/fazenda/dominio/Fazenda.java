package com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio;

import com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.dominio.UnidadeAdministrativa;
import com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.exceptions.UnidadeAdministrativaDesabilitadaException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Fazenda {

    @Id
    @GeneratedValue
    @Setter
    private UUID id;
    @NotNull
    private String descricao;
    @NotNull
    private String cnpj;

    @ManyToOne(fetch = FetchType.LAZY)
    private UnidadeAdministrativa unidadeAdministrativa;

    public Fazenda(String descricao, String cnpj, UnidadeAdministrativa unidadeAdministrativa) {
        if (!unidadeAdministrativa.isHabilitada()) {
            throw new UnidadeAdministrativaDesabilitadaException(unidadeAdministrativa.getId());
        }
        this.descricao = descricao;
        this.cnpj = cnpj;
        this.unidadeAdministrativa = unidadeAdministrativa;
    }
}
