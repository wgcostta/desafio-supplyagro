package com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.api;

import com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.dominio.UnidadeAdministrativa;
import com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.dominio.UnidadeAdministrativaRepository;
import com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.exceptions.UnidadeAdministrativaNaoEncontradaException;
import com.totvs.tjf.api.context.stereotype.ApiGuideline;
import com.totvs.tjf.api.context.v2.request.ApiFieldRequest;
import com.totvs.tjf.api.context.v2.request.ApiPageRequest;
import com.totvs.tjf.api.context.v2.request.ApiSortRequest;
import com.totvs.tjf.api.context.v2.response.ApiCollectionResponse;
import com.totvs.tjf.core.api.jpa.repository.ApiJpaCollectionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/unidades")
@ApiGuideline(ApiGuideline.ApiGuidelineVersion.V2)
public class UnidadeAdministrativaController {

    private final UnidadeAdministrativaRepository repository;

    @Transactional
    @PostMapping
    public ResponseEntity<UnidadeAdministrativaDTO> cadastrar(@Valid @RequestBody UnidadeAdministrativaRequest novaUnidade) {
        UnidadeAdministrativa unidade = novaUnidade.toEntity();
        repository.save(unidade);
        return ResponseEntity.ok(UnidadeAdministrativaDTO.from(unidade));
    }

    @GetMapping
    public ApiCollectionResponse<UnidadeAdministrativaDTO> listar(ApiFieldRequest fieldRequest, ApiPageRequest pageRequest, ApiSortRequest sortRequest) {
        ApiJpaCollectionResult<UnidadeAdministrativa> unidadesPage = repository.findAll(pageRequest, sortRequest);
        List<UnidadeAdministrativaDTO> unidades = unidadesPage.getItems()
                .stream()
                .map(UnidadeAdministrativaDTO::from)
                .collect(Collectors.toList());
        return ApiCollectionResponse.of(unidades, unidadesPage.hasNext());
    }

    @Transactional
    @PutMapping(path = "/{id}")
    public ResponseEntity<UnidadeAdministrativaDTO> alterar(@PathVariable UUID id, @Valid @RequestBody UnidadeAdministrativaRequest unidade) {
        verificarIdValido(id);
        UnidadeAdministrativa unidadeAlterada = unidade.toEntity();
        unidadeAlterada.setId(id);
        repository.save(unidadeAlterada);
        return ResponseEntity.ok(UnidadeAdministrativaDTO.from(unidadeAlterada));
    }

    @Transactional
    @PutMapping(path = "/{id}/desabilitar")
    public ResponseEntity<Void> desabilitar(@PathVariable UUID id) {
        UnidadeAdministrativa unidade = repository.findById(id)
                .orElseThrow(() -> new UnidadeAdministrativaNaoEncontradaException(id));
        unidade.desabilitar();
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        UnidadeAdministrativa unidade = repository.findById(id)
                .orElseThrow(() -> new UnidadeAdministrativaNaoEncontradaException(id));
        repository.delete(unidade);
        return ResponseEntity.noContent().build();
    }

    private void verificarIdValido(@PathVariable UUID id) {
        if (!repository.existsById(id)) {
            throw new UnidadeAdministrativaNaoEncontradaException(id);
        }
    }
}