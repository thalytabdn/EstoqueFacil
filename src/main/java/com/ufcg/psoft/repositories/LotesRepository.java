package com.ufcg.psoft.repositories;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LotesRepository<T, ID extends Serializable> extends JpaRepository<Lote, Long>{
    
    Optional<Lote> findByProduto(Produto produto);

    List<Lote> findAllByProduto(Produto produto);

    List<Lote> findAllByNumeroDeItensLessThan(int quantidadeDeItens);

    List<Lote> findAllByDataDeValidadeLessThanEqual(Date hojeMais30Dias);

    // @Query(value = "SELECT l FROM Lote l l.dataDeValidade")
    // List<Date> findDataDeValidadeByProduto(Produto produto);

}

