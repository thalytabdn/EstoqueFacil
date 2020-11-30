package com.ufcg.psoft.repositories;

import java.io.Serializable;
import java.util.List;

import com.ufcg.psoft.enumeration.SituacaoProduto;
import com.ufcg.psoft.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutosRepository<T, ID extends Serializable> extends JpaRepository<Produto, Long>{

	boolean existsByCodigoBarra(String codigoBarra);

	@Query(value = "SELECT p FROM Produto p ORDER BY p.nome")
	List<Produto> getProdutoByNome();

	@Query(value = "SELECT p FROM Produto p ORDER BY p.preco")
	List<Produto> getProdutoByPreco();

	@Query(value = "SELECT p FROM Produto p ORDER BY p.categoria")
	List<Produto> getProdutoByCategoria();

	List<Produto> getBySituacao(SituacaoProduto situacao);

}
