package com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio;

import com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.dominio.UnidadeAdministrativa;
import com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.exceptions.UnidadeAdministrativaDesabilitadaException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("unit")
class FazendaTest {

    @Test
    @DisplayName("Criar uma fazenda com uma unidade habilitada")
    void criarComUnidadeHabilitada() {
        UnidadeAdministrativa unidade = new UnidadeAdministrativa("Unidade", "Assis");
        Fazenda fazenda = new Fazenda("Fazenda", "24.171.513/0001-23", unidade);
        Assertions.assertNotNull(fazenda);
    }

    @Test
    @DisplayName("Uma fazenda não pode ser criada com uma unidade desabilitada")
    void unidadeDesabilitada() {
        UnidadeAdministrativa unidade = new UnidadeAdministrativa("Unidade", "Assis");
        unidade.desabilitar();
        Assertions.assertThrows(UnidadeAdministrativaDesabilitadaException.class, () ->
                new Fazenda("Fazenda", "24.171.513/0001-23", unidade));
    }
}
