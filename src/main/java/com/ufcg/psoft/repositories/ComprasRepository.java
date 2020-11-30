package com.ufcg.psoft.repositories;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.ufcg.psoft.enumeration.StatusCompra;
import com.ufcg.psoft.model.Compra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ComprasRepository<T, ID extends Serializable> extends JpaRepository<Compra, Long>{
    
    @Query(value = "SELECT c FROM Compra c ORDER BY c.dataCompra")
	List<Compra> getCompraByDataCompra();

    @Query(value = "SELECT c FROM Compra c ORDER BY c.valorCompra")
	List<Compra> getCompraByValorCompra();

    @Query(value = "SELECT c FROM Compra c ORDER BY c.status")
    List<Compra> getCompraByStatus();
    
    List<Compra> getByStatus(StatusCompra status);
}

