package com.ufcg.psoft.enumeration;

public enum StatusCompra {
    ABERTA("em aberto"),
    FECHADA("fechada");

    public final String statusCompra;

    private StatusCompra(String statusCompra) {
        this.statusCompra = statusCompra;
    }
}
