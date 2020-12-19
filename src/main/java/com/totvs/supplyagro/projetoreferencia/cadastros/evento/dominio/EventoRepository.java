package com.totvs.supplyagro.projetoreferencia.cadastros.evento.dominio;

import com.totvs.supplyagro.projetoreferencia.cadastros.talhao.dominio.Talhao;
import com.totvs.tjf.api.jpa.repository.ApiJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface EventoRepository extends CrudRepository<Evento, UUID>, ApiJpaRepository<Evento> {
    Page<Evento> findByTalhao(Talhao talhao, Pageable pageable);
    List<Evento> findByTalhao(Talhao talhao);
    boolean existsByTalhao(Talhao talhao);
    boolean existsByTalhaoAndTipo(Talhao talhao, TipoEvento tipo);
}
