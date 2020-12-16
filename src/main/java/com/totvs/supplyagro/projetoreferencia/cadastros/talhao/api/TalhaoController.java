package com.totvs.supplyagro.projetoreferencia.cadastros.talhao.api;

import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.api.FazendaDTO;
import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.api.FazendaRequest;
import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.api.FazendaRequestUpdate;
import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.api.exceptions.FazendaNaoEncontradaException;
import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio.Fazenda;
import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio.FazendaRepository;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.api.exceptions.TalhaoNaoEncontradoException;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.api.validacoes.TalhaoValidator;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.dominio.Talhao;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.dominio.TalhaoRepository;
import com.totvs.tjf.api.context.stereotype.ApiGuideline;
import com.totvs.tjf.api.context.v2.request.ApiFieldRequest;
import com.totvs.tjf.api.context.v2.request.ApiPageRequest;
import com.totvs.tjf.api.context.v2.request.ApiSortRequest;
import com.totvs.tjf.api.context.v2.response.ApiCollectionResponse;
import com.totvs.tjf.core.api.jpa.repository.ApiJpaCollectionResult;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/talhao")
@ApiGuideline(ApiGuideline.ApiGuidelineVersion.V2)
public class TalhaoController {
    private final TalhaoRepository repository;
    private final FazendaRepository fazendaRepository;
    private final TalhaoValidator talhaoValidator;

    @InitBinder("talhaoRequest")
    public void initBinders(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(talhaoValidator);
    }


    @GetMapping
    @ApiOperation("Busca todos os Talhões")
    public ApiCollectionResponse<TalhaoResponse> buscaTodos(ApiFieldRequest fieldRequest, ApiPageRequest pageRequest, ApiSortRequest sortRequest) {
        ApiJpaCollectionResult<Talhao> page = repository.findAll(pageRequest, sortRequest);
        List<TalhaoResponse> talhoes = page.getItems()
                .stream()
                .map(TalhaoResponse::from)
                .collect(Collectors.toList());
        return ApiCollectionResponse.of(talhoes, page.hasNext());
    }

    @PostMapping
    @ApiOperation("Cadastra um Talhão")
    @Transactional
    public ResponseEntity<TalhaoResponse> cadastrar(@Valid @RequestBody TalhaoRequest talhaoRequest) {
        Talhao talhao = talhaoRequest.toModel();
        Fazenda fazenda = fazendaRepository.findById(talhaoRequest.getIdFazenda())
                .orElseThrow(() -> new FazendaNaoEncontradaException(talhaoRequest.getIdFazenda()));
        talhao.setFazenda(fazenda);
        repository.save(talhao);
        return ResponseEntity.ok(TalhaoResponse.from(talhao));
    }

    @PutMapping(path = "/{id}")
    @ApiOperation("Altera um Talhao")
    @Transactional
    public ResponseEntity<TalhaoResponse> alterar(@PathVariable("id") UUID id, @Valid @RequestBody EditTalhaoRequest talhaoRequest) {
        Talhao talhao = repository.findById(id).orElseThrow(() -> new TalhaoNaoEncontradoException(id));
        talhao.alterar(talhaoRequest);
        repository.save(talhao);
        return ResponseEntity.ok(TalhaoResponse.from(talhao));
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation("Exclui um Talhao")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable("id") UUID id) {
        Talhao talhao = repository.findById(id).orElseThrow(() -> new TalhaoNaoEncontradoException(id));
        repository.delete(talhao);
        return ResponseEntity.noContent().build();
    }
}
