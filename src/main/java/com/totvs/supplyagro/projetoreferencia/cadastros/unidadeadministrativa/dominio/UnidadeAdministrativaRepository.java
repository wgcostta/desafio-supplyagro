package com.totvs.supplyagro.projetoreferencia.cadastros.unidadeadministrativa.dominio;

import com.totvs.tjf.api.jpa.repository.ApiJpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UnidadeAdministrativaRepository extends CrudRepository<UnidadeAdministrativa, UUID>, ApiJpaRepository<UnidadeAdministrativa> {
}
