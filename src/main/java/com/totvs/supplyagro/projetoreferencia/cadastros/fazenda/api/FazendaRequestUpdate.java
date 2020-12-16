package com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.api;

import com.totvs.supplyagro.projetoreferencia.cadastros.comum.api.Unique;
import com.totvs.supplyagro.projetoreferencia.cadastros.fazenda.dominio.Fazenda;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class FazendaRequestUpdate {
    @Unique(entidade = Fazenda.class, atributo = "descricao", message = "{Fazenda.descricao.Unique}")
    @NotBlank(message = "{Fazenda.descriocao.NotBlank}")
    private String descricao;

}
