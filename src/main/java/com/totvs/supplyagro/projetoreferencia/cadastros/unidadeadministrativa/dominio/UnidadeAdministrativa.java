package com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.dominio;

import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Getter
@ToString
@Table(name = "unidadeadm")
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class UnidadeAdministrativa {

    @Id
    @Setter
    @GeneratedValue
    private UUID id;
    @NotNull
    private String descricao;
    @NotNull
    private String municipio;
    private boolean habilitada = true;

    public UnidadeAdministrativa(@NonNull String descricao, @NonNull String municipio) {
        this.descricao = descricao;
        this.municipio = municipio;
    }

    public void desabilitar() {
        Assert.isTrue(habilitada, "Unidade já está habilitada");
        habilitada = false;
    }
}
