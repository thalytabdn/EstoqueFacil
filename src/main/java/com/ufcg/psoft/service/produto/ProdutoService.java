package com.ufcg.psoft.service.produto;

import java.util.List;

import com.ufcg.psoft.enumeration.SituacaoProduto;
import com.ufcg.psoft.model.Produto;

public interface ProdutoService {

    List<Produto> findAllProdutos(String order);
    
    Produto saveProduto(Produto produto);
    
    void updateProduto(Produto produto);
    
    void deleteProdutoById(long id);
    
    long size();
    
    void deleteAllProducts();
    
    Produto findById(long id);
    
    boolean doesProdutoExist(Produto produto);    
}
