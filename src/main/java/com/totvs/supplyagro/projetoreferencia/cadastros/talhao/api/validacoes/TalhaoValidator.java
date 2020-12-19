package com.totvs.supplyagro.projetoreferencia.cadastros.talhao.api.validacoes;

import com.totvs.supplyagro.projetoreferencia.cadastros.evento.dominio.Evento;
import com.totvs.supplyagro.projetoreferencia.cadastros.evento.dominio.EventoRepository;
import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.api.exceptions.FazendaNaoEncontradaException;
import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio.Fazenda;
import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio.FazendaRepository;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.api.EditTalhaoRequest;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.api.TalhaoRequest;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.api.exceptions.TalhaoComEventosVinculadosException;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.api.exceptions.TalhaoDuplicadoFazendaException;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.api.exceptions.TalhaoNaoEncontradoException;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.dominio.Talhao;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.dominio.TalhaoRepository;
import com.totvs.tjf.api.context.v2.request.ApiPageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class TalhaoValidator implements Validator {

    private final FazendaRepository fazendaRepository;
    private final TalhaoRepository talhaoRepository;
    private final EventoRepository eventoRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == TalhaoRequest.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target instanceof TalhaoRequest) {
            TalhaoRequest request = (TalhaoRequest) target;
            Fazenda fazenda = fazendaRepository.findById(request.getIdFazenda())
                    .orElseThrow(() -> new FazendaNaoEncontradaException(request.getIdFazenda()));

            if (talhaoRepository.validCodigoAndFazenda(request.getCodigo(), request.getIdFazenda()) > 0) {
                throw new TalhaoDuplicadoFazendaException(request.getCodigo());
            }
        }

        if (target instanceof EditTalhaoRequest){
            EditTalhaoRequest talhaoEdit = (EditTalhaoRequest) target;
            Talhao talhao = talhaoRepository.findById(talhaoEdit.getId()).orElseThrow(
                    () ->  new TalhaoNaoEncontradoException(talhaoEdit.getId())
            );

            if(eventoRepository.existsByTalhao(talhao)){
                throw new TalhaoComEventosVinculadosException(talhao.getCodigo());
            }
        }

//        Ser único para a Fazenda relacionada. Por exemplo: Fazenda A possui o talhão com código T01.
//        Portanto não deve permitir inserir outro talhão com código T01 para a Fazenda A.
//        Mas pode permitir inserir para a Fazenda B que não possui talhão com código T01
    }
}
