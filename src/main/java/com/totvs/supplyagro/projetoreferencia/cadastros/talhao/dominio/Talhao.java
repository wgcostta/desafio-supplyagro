package com.totvs.supplyagro.projetoreferencia.cadastros.talhao.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.totvs.supplyagro.projetoreferencia.cadastros.evento.dominio.Evento;
import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio.Fazenda;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.api.EditTalhaoRequest;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name="talhao")
public class Talhao {

    @Id
    @GeneratedValue
    @Setter
    private UUID id;

    @Size(min = 2, max = 10, message = "O Código é inválido.")
    @NotBlank(message = "O Código é inválido.")
    private String codigo;

    @NotBlank(message = "A Area é inválido.")
    private Float area;
    //Área em hectares (valor decimal)

    @NotBlank(message = "O Safra é inválido.")
    private Integer safra;
    //Safra (número inteiro, identificando o ano)

    @NotBlank(message = "O estimativa de produtivadade é inválida.")
    private Integer produtivade;
    //Estimativa de Produtividade (em sacas, onde cada saca equivale a 60 kg)

    @NotBlank(message = "A Fazenda é inválida.")
    @ManyToOne
    @Setter
    private Fazenda fazenda;

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "talhao", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Evento> eventos;

    public void alterar(EditTalhaoRequest talhaoRequest) {
        this.produtivade = talhaoRequest.getProdutivade();
        this.area = talhaoRequest.getArea();
    }
}
