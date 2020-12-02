package com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.api;

import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.api.exceptions.FazendaNaoEncontradaException;
import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.api.validacoes.UnidadeAdministrativaHabilitadoValidator;
import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio.Fazenda;
import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio.FazendaRepository;
import com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.dominio.UnidadeAdministrativaRepository;
import com.totvs.tjf.api.context.stereotype.ApiGuideline;
import com.totvs.tjf.api.context.v2.request.ApiFieldRequest;
import com.totvs.tjf.api.context.v2.request.ApiPageRequest;
import com.totvs.tjf.api.context.v2.request.ApiSortRequest;
import com.totvs.tjf.api.context.v2.response.ApiCollectionResponse;
import com.totvs.tjf.core.api.jpa.repository.ApiJpaCollectionResult;
import io.swagger.annotations.Api;
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

@Api
@RestController
@ApiGuideline(ApiGuideline.ApiGuidelineVersion.V2)
@RequestMapping(path = "/api/v1/fazendas")
@RequiredArgsConstructor
public class FazendaController {

    private final FazendaRepository repository;
    private final UnidadeAdministrativaRepository unidadeAdministrativaRepository;
    private final UnidadeAdministrativaHabilitadoValidator unidadeAdministrativaHabilitadoValidator;

    @InitBinder("fazendaRequest")
    public void initBinders(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(unidadeAdministrativaHabilitadoValidator);
    }

    @GetMapping
    @ApiOperation("Busca todas as Fazendas")
    public ApiCollectionResponse<FazendaDTO> buscaTodos(ApiFieldRequest fieldRequest, ApiPageRequest pageRequest, ApiSortRequest sortRequest) {
        ApiJpaCollectionResult<Fazenda> fazendasPage = repository.findAll(pageRequest, sortRequest);
        List<FazendaDTO> fazendas = fazendasPage.getItems()
                .stream()
                .map(FazendaDTO::from)
                .collect(Collectors.toList());
        return ApiCollectionResponse.of(fazendas, fazendasPage.hasNext());
    }

    @PostMapping
    @ApiOperation("Cadastra uma Fazenda")
    @Transactional
    public ResponseEntity<FazendaDTO> cadastrar(@Valid @RequestBody FazendaRequest fazendaRequest) {
        Fazenda fazenda = fazendaRequest.toModel(unidadeAdministrativaRepository);
        repository.save(fazenda);
        return ResponseEntity.ok(FazendaDTO.from(fazenda));
    }

    @PutMapping(path = "/{id}")
    @ApiOperation("Altera uma Fazenda")
    @Transactional
    public ResponseEntity<FazendaDTO> alterar(@PathVariable("id") UUID id, @Valid @RequestBody FazendaRequest fazendaRequest) {
        if (!repository.existsById(id)) {
            throw new FazendaNaoEncontradaException(id);
        }
        Fazenda fazenda = fazendaRequest.toModel(unidadeAdministrativaRepository);
        fazenda.setId(id);
        repository.save(fazenda);
        return ResponseEntity.ok(FazendaDTO.from(fazenda));
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation("Exclui uma Fazenda")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable("id") UUID id) {
        Fazenda fazenda = repository.findById(id)
                .orElseThrow(() -> new FazendaNaoEncontradaException(id));
        repository.delete(fazenda);
        return ResponseEntity.noContent().build();
    }
}
