package com.totvs.supplyagro.projetoreferencia.cadastros.evento.dominio;

import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio.Fazenda;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.dominio.Talhao;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Builder
@Table(name="evento")
public class Evento {

    @Id
    @GeneratedValue
    @Setter
    private UUID id;

    @NotNull
    private ZonedDateTime dataEvento;

    @NotNull
    private Float area;

    @NotNull
    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Include
    private TipoEvento tipo;

    @Setter
    @ManyToOne
    private Talhao talhao;
}
