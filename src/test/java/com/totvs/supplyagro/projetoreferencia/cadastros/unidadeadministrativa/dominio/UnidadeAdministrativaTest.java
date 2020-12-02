package com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.dominio;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("unit")
class UnidadeAdministrativaTest {

    @Test
    @DisplayName("Unidade deve ser criada já habilitada")
    void criar() {
        UnidadeAdministrativa unidade = new UnidadeAdministrativa("Unidade", "Assis");
        Assertions.assertTrue(unidade.isHabilitada());
    }

    @Test
    @DisplayName("Após desabilitar a Unidade o atributo deve ser modificado")
    void desabilitar() {
        UnidadeAdministrativa unidade = new UnidadeAdministrativa("Unidade", "Assis");
        unidade.desabilitar();
        Assertions.assertFalse(unidade.isHabilitada());
    }
}
