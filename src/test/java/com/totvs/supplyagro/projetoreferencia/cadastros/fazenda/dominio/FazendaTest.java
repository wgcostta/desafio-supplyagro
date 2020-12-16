package com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio;

import com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.dominio.UnidadeAdministrativa;
import com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.exceptions.UnidadeAdministrativaDesabilitadaException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

@Tag("unit")
class FazendaTest {

    @Test
    @DisplayName("Criar uma fazenda com uma unidade habilitada")
    void criarComUnidadeHabilitada() {
        Endereco endereco = new Endereco("Unidade", "Assis","Rua Leonor");
        Set<Endereco> enderecos = new HashSet<Endereco>();
        enderecos.add(endereco);
        Fazenda fazenda = new Fazenda("Fazenda", "24.171.513/0001-23", enderecos);
        Assertions.assertNotNull(fazenda);
    }

    @Test
    @DisplayName("Uma fazenda não pode ser criada com uma unidade desabilitada")
    void unidadeDesabilitada() {
        Endereco endereco = new Endereco("Unidade", "Assis","Rua Leonor");
        Set<Endereco> enderecos = new HashSet<Endereco>();
        enderecos.add(endereco);
        Assertions.assertThrows(UnidadeAdministrativaDesabilitadaException.class, () ->
                new Fazenda("Fazenda", "24.171.513/0001-23", enderecos));
    }
}
