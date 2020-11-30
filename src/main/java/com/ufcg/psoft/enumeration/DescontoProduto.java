package com.ufcg.psoft.enumeration;

public enum DescontoProduto {
    SEM_DESCONTO("Sem Desconto"),
    BOM_DESCONTO("Bom Desconto"),
    OTIMO_DESCONTO("otimo"),
    SUPER_DESCONTO("super");

    public final String descontoProduto;

    private DescontoProduto(String descontoProduto) {
        this.descontoProduto = descontoProduto;
    }
}
