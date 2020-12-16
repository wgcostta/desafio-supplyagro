package com.totvs.supplyagro.projetoreferencia.cadastros.talhao.api.validacoes;

import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.api.FazendaRequest;
import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.api.exceptions.FazendaNaoEncontradaException;
import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio.Fazenda;
import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio.FazendaRepository;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.api.TalhaoRequest;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.api.exceptions.TalhaoDuplicadoFazendaException;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.dominio.Talhao;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.dominio.TalhaoRepository;
import com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.dominio.UnidadeAdministrativaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class TalhaoValidator implements Validator {

    private final FazendaRepository fazendaRepository;
    private final TalhaoRepository talhaoRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == TalhaoRequest.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        TalhaoRequest request = (TalhaoRequest) target;
        Fazenda fazenda = fazendaRepository.findById(request.getIdFazenda())
                .orElseThrow(() -> new FazendaNaoEncontradaException(request.getIdFazenda()));

        if(talhaoRepository.validCodigoAndFazenda(request.getCodigo(),request.getIdFazenda()) > 0){
            throw new TalhaoDuplicadoFazendaException(request.getCodigo());
        }
//        Ser único para a Fazenda relacionada. Por exemplo: Fazenda A possui o talhão com código T01.
//        Portanto não deve permitir inserir outro talhão com código T01 para a Fazenda A.
//        Mas pode permitir inserir para a Fazenda B que não possui talhão com código T01
    }
}
