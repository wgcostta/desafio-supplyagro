package com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio;

import com.totvs.tjf.api.jpa.repository.ApiJpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FazendaRepository extends JpaRepository<Fazenda, UUID>, ApiJpaRepository<Fazenda> {
}
