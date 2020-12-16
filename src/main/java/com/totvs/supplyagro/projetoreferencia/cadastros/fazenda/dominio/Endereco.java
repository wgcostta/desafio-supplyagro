package com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;


@Entity
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Builder
public class Endereco {
    @Id
    @GeneratedValue
    @Setter
    private UUID id;

    @Size(min = 8, max = 9, message = "O CEP é inválido.")
    @NotBlank(message = "O CEP é inválido.")
    private String cep;

    @NotBlank(message = "Cidade inválida.")
    private String cidade;

    @NotBlank(message = "Estado inválido.")
    private String estado;

    @NotBlank(message = "O Endereço é obrigatório.")
    private String logradouro;

    @Setter
    @ManyToOne
    private Fazenda fazenda;

    public Endereco(String cidade, String estado, String logradouro) {
        this.cidade = cidade;
        this.estado = estado;
        this.logradouro = logradouro;
    }
}
