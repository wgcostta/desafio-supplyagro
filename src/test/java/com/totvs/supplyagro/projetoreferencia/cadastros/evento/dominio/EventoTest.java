package com.totvs.supplyagro.projetoreferencia.cadastros.evento.dominio;

import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio.Endereco;
import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio.Fazenda;
import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.dominio.Talhao;
import org.junit.jupiter.api.*;


import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Tag("unit")
public class EventoTest {

    private Endereco endereco;
    private Set<Endereco> enderecos;
    private Fazenda fazenda;
    private Talhao talhao;
    private Evento evento;

    @BeforeEach
    void setup() {
        endereco = new Endereco("Assis", "SP","Rua Leonor");
        enderecos = new HashSet<Endereco>();
        enderecos.add(endereco);
        fazenda = new Fazenda("Fazenda", "24.171.513/0001-23", enderecos);
        talhao = Talhao.builder()
                .codigo(String.valueOf("codigo"))
                .area(Float.valueOf(20))
                .produtivade(10)
                .safra(2020)
                .fazenda(fazenda)
                .build();

        evento = Evento.builder()
                .area(Float.valueOf(11))
                .tipo(TipoEvento.PLANTIO)
                .dataEvento(ZonedDateTime.now())
                .talhao(talhao)
                .build();

    }

    @Test
    @DisplayName("Criar um Evento com um talhao")
    void criarNovoEvento() {
        Assertions.assertNotNull(evento);
        Assertions.assertEquals(TipoEvento.PLANTIO, evento.getTipo());
    }
}
