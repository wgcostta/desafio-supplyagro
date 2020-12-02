package com.totvs.supplyagro.projetoreferencia.cadastros.fazenda;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.totvs.supplyagro.projetoreferencia.cadastros.ApplicationTests;
import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.api.FazendaRequest;
import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio.Fazenda;
import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio.FazendaRepository;
import com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.dominio.UnidadeAdministrativa;
import com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.dominio.UnidadeAdministrativaRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Tag("integration")
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = ApplicationTests.class)
@Sql(value = "classpath:scripts/insere_unidade_adm.sql")
class FazendaIT {

    private static final String API_PATH = "/api/v1/fazendas";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private FazendaRepository fazendaRepository;
    @Autowired
    private UnidadeAdministrativaRepository unidadeAdministrativaRepository;

    @Test
    @DisplayName("Deve cadastrar uma fazenda corretamente")
    void cadastrar() throws Exception {
        UUID idUnidade = UUID.fromString("3871e0d3-721a-4d0f-8d93-aa2c85981da7");
        FazendaRequest novaFazenda = new FazendaRequest(idUnidade, "Nova Fazenda", "24.171.513/0001-23");
        mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(novaFazenda)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Não deve permitir cadastrar uma fazenda com uma unidade desabilitada")
    void unidadeDesabilitada() throws Exception {
        UUID idUnidade = UUID.fromString("3871e0d3-721a-4d0f-8d93-aa2c85981da7");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/unidades/" + idUnidade.toString() + "/desabilitar")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        FazendaRequest novaFazenda = new FazendaRequest(idUnidade, "Nova Fazenda", "24.171.513/0001-23");
        mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(novaFazenda)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.details[0].message", Matchers.equalTo("A Unidade Administrativa Teste não está habilitada")));
    }

    @Test
    @DisplayName("Deve alterar uma fazenda corretamente")
    void alterar() throws Exception {
        UUID idUnidade = UUID.fromString("3871e0d3-721a-4d0f-8d93-aa2c85981da7");
        UnidadeAdministrativa unidadeAdministrativa = unidadeAdministrativaRepository.findById(idUnidade).orElseThrow();
        Fazenda fazendaExistente = new Fazenda("Fazenda existente", "24.171.513/0001-23", unidadeAdministrativa);
        fazendaRepository.save(fazendaExistente);

        FazendaRequest alteracao = new FazendaRequest(idUnidade, "Fazenda alterada", "24.171.513/0001-23");
        mockMvc.perform(MockMvcRequestBuilders.put(API_PATH + "/" + fazendaExistente.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(alteracao)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Fazenda fazendaAlterada = fazendaRepository.findById(fazendaExistente.getId()).orElseThrow();
        Assertions.assertEquals("Fazenda alterada", fazendaAlterada.getDescricao());
    }

    @Test
    @DisplayName("Deve excluir uma fazenda")
    void excluir() throws Exception {
        UUID idUnidade = UUID.fromString("3871e0d3-721a-4d0f-8d93-aa2c85981da7");
        UnidadeAdministrativa unidadeAdministrativa = unidadeAdministrativaRepository.findById(idUnidade).orElseThrow();
        Fazenda fazenda = new Fazenda("Fazenda existente", "24.171.513/0001-23", unidadeAdministrativa);
        fazendaRepository.save(fazenda);

        mockMvc.perform(MockMvcRequestBuilders.delete(API_PATH + "/" + fazenda.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Assertions.assertFalse(fazendaRepository.existsById(fazenda.getId()));
    }

}
