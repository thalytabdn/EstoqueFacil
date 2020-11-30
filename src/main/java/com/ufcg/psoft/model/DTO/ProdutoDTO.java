package com.ufcg.psoft.model.DTO;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ufcg.psoft.enumeration.SituacaoProduto;
import com.ufcg.psoft.model.Produto;

import exceptions.ObjetoInvalidoException;

public class ProdutoDTO {

    private Produto produto;

    public ProdutoDTO() {
        this.produto = new Produto();
    }

    @JsonIgnore
    public Produto getProduto() {
        return produto;
    }
    
    public ProdutoDTO(Produto produto) {
        this.produto = produto;
	}

    public String getNome() {
        return produto.getNome();
    }

    public BigDecimal getPreco() {
        return produto.getPreco();
    }

    public SituacaoProduto getSituacao() throws ObjetoInvalidoException {
       return produto.getSituacao();
    }
    
}
