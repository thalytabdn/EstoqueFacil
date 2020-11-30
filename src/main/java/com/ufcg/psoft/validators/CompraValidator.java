package com.ufcg.psoft.validators;

import java.util.List;

import com.ufcg.psoft.model.Compra;
import com.ufcg.psoft.model.Produto;

import org.springframework.stereotype.Component;

@Component
public class CompraValidator {
    
    private ProdutoValidator validaProduto;

    public void ValidaCompra(Compra compra) throws Exception {
        List<Produto> produtos = compra.getProdutos();
        
        for (Produto produto : produtos) {
            validaProduto.ValidProduto(produto);
        }
    }
}
