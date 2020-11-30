package com.ufcg.psoft.enumeration;

public enum SituacaoProduto {
    DISPONIVEL("Disponivel"), 
    INDISPONIVEL("Indisponivel");

    public final String situacaoProduto;

    private SituacaoProduto(String situacaoProduto) {
        this.situacaoProduto = situacaoProduto;
    }
}
