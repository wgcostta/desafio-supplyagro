package com.totvs.supplyagro.projetoreferencia.cadastros.evento.api;

import com.totvs.supplyagro.projetoreferencia.cadastros.evento.dominio.Evento;
import com.totvs.supplyagro.projetoreferencia.cadastros.evento.dominio.TipoEvento;
import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio.Endereco;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.dominio.Talhao;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Builder
public class EventoRequest {
    private UUID idTalhao;
    private Float area;
    private TipoEvento tipo;
    private Talhao talhao;

    public Evento toModel() {
        return Evento.builder()
                .area(this.area)
                .tipo(this.tipo)
                .talhao(this.talhao)
                .build();
    }
}
