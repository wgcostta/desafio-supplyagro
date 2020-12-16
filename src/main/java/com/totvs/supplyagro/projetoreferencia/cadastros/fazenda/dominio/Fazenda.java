package com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.dominio.Talhao;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@Table(name="fazenda")
public class Fazenda {

    @Id
    @GeneratedValue
    @Setter
    private UUID id;
    @Setter
    @NotNull
    @Max(200)
    private String descricao;
    @NotNull
    private String cnpj;

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "fazenda", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Endereco> enderecos;

    @Setter
    @ManyToOne
    private Talhao talhao;

    public Fazenda(String descricao, String cnpj, Set<Endereco> enderecos) {
        this.descricao = descricao;
        this.cnpj = cnpj;
        this.enderecos = enderecos;
    }

    public void alterar(String descricao) {
        this.descricao = descricao;
    }

}
