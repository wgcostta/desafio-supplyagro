package com.totvs.supplyagro.projetoreferencia.cadastros.evento.dominio;

public enum TipoEvento {
    PLANTIO("PLANTIO"),
    COLHEITA("COLHEITA"),
    ENCERRAMENTO("ENCERRAMENTO");

    private String descricao;

    TipoEvento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
