package com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.api.validacoes;

import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.api.FazendaRequest;
import com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.dominio.UnidadeAdministrativa;
import com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.dominio.UnidadeAdministrativaRepository;
import com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.exceptions.UnidadeAdministrativaNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UnidadeAdministrativaHabilitadoValidator implements Validator {

    private final UnidadeAdministrativaRepository unidadeAdministrativaRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == FazendaRequest.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        FazendaRequest request = (FazendaRequest) target;
        UnidadeAdministrativa unidadeAdministrativa = unidadeAdministrativaRepository.findById(request.getUnidadeAdministrativaId())
                .orElseThrow(() -> new UnidadeAdministrativaNaoEncontradaException(request.getUnidadeAdministrativaId()));
        if (!unidadeAdministrativa.isHabilitada()) {
            errors.reject("UnidadeAdministrativaDesabilitada.detail", new Object[]{unidadeAdministrativa.getDescricao()}, "");
        }
    }
}
