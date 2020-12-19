package com.totvs.supplyagro.projetoreferencia.cadastros.talhao.dominio;

import com.totvs.tjf.api.jpa.repository.ApiJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TalhaoRepository extends CrudRepository<Talhao, UUID>, ApiJpaRepository<Talhao> {

    @Query(
            value = "select count(*) \n" +
                    "from talhao \n" +
                    "inner join fazenda on (talhao.id = fazenda.talhao_id)\n" +
                    "where talhao.codigo = ?1 and fazenda.id = ?2",
            nativeQuery = true)
    int validCodigoAndFazenda(String codigo, UUID idFazenda);
}
