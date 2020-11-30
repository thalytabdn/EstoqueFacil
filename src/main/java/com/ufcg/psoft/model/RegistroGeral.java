package com.ufcg.psoft.model;

import java.util.Date;
import java.util.List;

public class RegistroGeral {
    
    private Produto produto;
    
    private int numeroDeLotes;
    
    private int numeroDeItens;
    
    private List<Date> datasDevalidade;

    
    public RegistroGeral(Produto produto, int numeroDeLotes, int numeroDeItens,
        List<Date> datasDevalidade) {

        this.produto = produto;
        this.numeroDeLotes = numeroDeLotes;
        this.numeroDeItens = numeroDeItens;
        this.datasDevalidade = datasDevalidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getNumeroDeLotes() {
        return numeroDeLotes;
    }

    public void setNumeroDeLotes(int numeroDeLotes) {
        this.numeroDeLotes = numeroDeLotes;
    }

    public int getNumeroDeItens() {
        return numeroDeItens;
    }

    public void setNumeroDeItens(int numeroDeItens) {
        this.numeroDeItens = numeroDeItens;
    }

    public List<Date> getDatasDevalidade() {
        return datasDevalidade;
    }

    public void setDatasDevalidade(List<Date> datasDevalidade) {
        this.datasDevalidade = datasDevalidade;
    }

}
