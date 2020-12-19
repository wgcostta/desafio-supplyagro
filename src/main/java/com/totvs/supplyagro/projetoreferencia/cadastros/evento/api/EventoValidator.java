package com.totvs.supplyagro.projetoreferencia.cadastros.evento.api;

import com.totvs.supplyagro.projetoreferencia.cadastros.evento.api.exceptions.EventoEncerradoException;
import com.totvs.supplyagro.projetoreferencia.cadastros.evento.api.exceptions.EventoPlantioExcedidoException;
import com.totvs.supplyagro.projetoreferencia.cadastros.evento.dominio.Evento;
import com.totvs.supplyagro.projetoreferencia.cadastros.evento.dominio.EventoRepository;
import com.totvs.supplyagro.projetoreferencia.cadastros.evento.dominio.TipoEvento;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.api.TalhaoRequest;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.api.exceptions.TalhaoNaoEncontradoException;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.dominio.Talhao;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.dominio.TalhaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
@RequiredArgsConstructor
public class EventoValidator implements Validator {
    private final EventoRepository eventoRepository;
    private final TalhaoRepository talhaoRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == TalhaoRequest.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        EventoRequest request = (EventoRequest) target;
        Talhao talhao = talhaoRepository.findById(request.getIdTalhao()).orElseThrow(
                () ->  new TalhaoNaoEncontradoException(request.getIdTalhao())
        );

        if(eventoRepository.existsByTalhaoAndTipo(talhao, TipoEvento.ENCERRAMENTO)){
            throw new EventoEncerradoException(request.getIdTalhao().toString());
        }

        AtomicReference<Float> areaColheita = null;
        AtomicReference<Float> areaPlantio = null;

        List<Evento> eventos = eventoRepository.findByTalhao(talhao);
        eventos.forEach(evento -> {
            if(evento.getTipo() == TipoEvento.COLHEITA){
                areaColheita.set(areaColheita.get() + evento.getArea());
            }else if(evento.getTipo() == TipoEvento.PLANTIO){
                areaPlantio.set(areaPlantio.get() + evento.getArea());
            }
        });

        if(areaColheita.get() >= talhao.getArea()){
            throw new EventoPlantioExcedidoException(talhao.getCodigo());
        }
        /*
        A soma da área dos eventos do mesmo tipo e da mesma safra não podem ser maior que a área do
        talhão. Ex: Talhão de 10 ha com 1 evento de plantio de 10 ha, e outro evento de colheita com
        10 ha é permitido. Não é permitido 2 eventos de plantio, cada um com área de 10 ha, totalizando 20 ha de plantio.
        Obs: os eventos serão gerados pelos apontamentos, nessa tarefa é necessário contemplar o recebimento
        e visualização dos eventos. (O recebimento será via apontamentos e não direto pelo usuário)
        */
    }
}
