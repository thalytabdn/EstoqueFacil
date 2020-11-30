package com.ufcg.psoft.model.Desconto;

import java.math.BigDecimal;

public class OtimoDesconto implements Desconto {
    private BigDecimal valorProduto;

    public OtimoDesconto(BigDecimal valorProduto){
        this.valorProduto = valorProduto;
    }

    @Override
    public BigDecimal calculaDesconto() {
        BigDecimal desconto = new BigDecimal(0.25).multiply(valorProduto);
        this.valorProduto = this.valorProduto.subtract(desconto);
    
        return this.valorProduto;
    }
    
}
