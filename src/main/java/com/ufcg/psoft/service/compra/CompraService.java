package com.ufcg.psoft.service.compra;

import java.util.List;

import com.ufcg.psoft.model.Compra;

public interface CompraService {

    List<Compra> findAllCompras(String order);
    
    Compra saveCompra(Compra Compra);
    
    void updateCompra(Compra Compra);
    
    void deleteCompraById(long id);
    
    long size();
    
    Compra findById(long id);
    
}
