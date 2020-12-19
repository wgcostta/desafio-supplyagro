package com.totvs.supplyagro.projetoreferencia.cadastros.evento.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.totvs.supplyagro.projetoreferencia.cadastros.evento.dominio.Evento;
import com.totvs.supplyagro.projetoreferencia.cadastros.evento.dominio.TipoEvento;
import com.totvs.tjf.api.context.v2.request.ApiExpandRequest;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventoResponse {

    private UUID id;
    private ZonedDateTime dataEvento;
    private Float area;
    private TipoEvento tipo;

    public static EventoResponse fromModel(Evento evento){
        EventoResponse eventoResponse = new EventoResponse();

        eventoResponse.id = evento.getId();
        eventoResponse.dataEvento = evento.getDataEvento();
        eventoResponse.area = evento.getArea();
        eventoResponse.tipo = evento.getTipo();

        return eventoResponse;
    }

    public static List<EventoResponse> fromModels(@NonNull Collection<Evento> eventos){
        return eventos.stream().map(obj -> EventoResponse.fromModel(obj)).collect(Collectors.toList());
    }
}
