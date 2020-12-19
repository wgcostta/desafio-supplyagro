package com.totvs.supplyagro.projetoreferencia.cadastros.evento.api;

import com.totvs.supplyagro.projetoreferencia.cadastros.evento.dominio.Evento;
import com.totvs.supplyagro.projetoreferencia.cadastros.evento.dominio.EventoRepository;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.api.exceptions.TalhaoNaoEncontradoException;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.dominio.Talhao;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.dominio.TalhaoRepository;
import com.totvs.tjf.api.context.stereotype.ApiGuideline;
import com.totvs.tjf.api.context.v2.request.ApiPageRequest;
import com.totvs.tjf.api.context.v2.request.ApiSortRequest;
import com.totvs.tjf.api.context.v2.response.ApiCollectionResponse;
import com.totvs.tjf.api.jpa.ApiRequestConverter;
import com.totvs.tjf.core.api.context.request.ApiExpandRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.UUID;

@Api
@RestController
@ApiGuideline(ApiGuideline.ApiGuidelineVersion.V2)
@RequestMapping(path = "/api/v1/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoRepository eventoRepository;
    private final TalhaoRepository talhaoRepository;
    private final EventoValidator eventoValidator;

    @InitBinder("eventoRequest")
    public void initBinders(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(eventoValidator);
    }

    @GetMapping(path = "/{id}")
    @ApiOperation("Busca todos Eventos")
    public ApiCollectionResponse<EventoResponse> findAllTalhao(@PathVariable("id") UUID id, ApiPageRequest pageRequest, ApiSortRequest sortRequest, ApiExpandRequest expandRequest) {
        Talhao talhao = talhaoRepository.findById(id).orElseThrow(() -> new TalhaoNaoEncontradoException(id));
        Pageable pageable = ApiRequestConverter.convert(pageRequest, sortRequest);
        Page<Evento> eventos = eventoRepository.findByTalhao(talhao, pageable);
        return ApiCollectionResponse.of(EventoResponse.fromModels(eventos.getContent()), eventos.hasNext());
    }

    @PostMapping(path = "/{idTalhao}")
    @ApiOperation("Cadastrar um evento para um talhão.")
    @Transactional
    public ResponseEntity<EventoResponse> cadastrarRelacionamento(@PathVariable("idTalhao") UUID idTalhao, @RequestBody @Valid EventoRequest eventoRequest) {
        Evento evento = eventoRequest.toModel();
        Talhao talhao = talhaoRepository.findById(idTalhao).orElseThrow(
                () ->  new TalhaoNaoEncontradoException(idTalhao)
        );
        evento.setTalhao(talhao);
        eventoRepository.save(evento);

        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/")
                .path(evento.getId().toString()).build().toUri())
                .body(EventoResponse.fromModel(evento));
    }
}
