package com.ufcg.psoft.service.produto;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.enumeration.SituacaoProduto;
import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.repositories.ProdutosRepository;

@Service("produtoService")
public class ProdutoBean implements ProdutoService{

	@Autowired
	private ProdutosRepository<Produto, Long> produtosDAO;
	
	private static final AtomicLong counter = new AtomicLong();

	// REMOVI LOTE

	// 	produtos.add(new Produto(counter.incrementAndGet(), "Leite Integral", "87654321-B", "Parmalat", "Mercearia"));
	// 	produtos.add(new Produto(counter.incrementAndGet(), "Arroz Integral", "87654322-B", "Tio Joao", "Perecíveis"));
	// 	produtos.add(new Produto(counter.incrementAndGet(), "Sabao em Po", "87654323-B", "OMO", "Limpeza"));
	// 	produtos.add(new Produto(counter.incrementAndGet(), "Agua Sanitaria", "87654324-C", "Dragao", "limpesa"));
	// 	produtos.add(new Produto(counter.incrementAndGet(), "Creme Dental", "87654325-C", "Colgate", "HIGIENE"));

	@Override
	public List<Produto> findAllProdutos(String order) {
		if(order == null){
            order = "";
        }
        if(order.equals("nome")){
            return produtosDAO.getProdutoByNome();
        }else if(order.equals("preco")){
            return produtosDAO.getProdutoByPreco();
        }else if(order.equals("categoria")){
            return produtosDAO.getProdutoByCategoria();
        }else{
            return produtosDAO.findAll();
        }
    }

	@Override
	public Produto saveProduto(Produto produto) {
		produto.mudaId(counter.incrementAndGet());
		return produtosDAO.save(produto);
	}

	@Override
	public void updateProduto(Produto produto) {
		produtosDAO.save(produto);
	}

	@Override
	public void deleteProdutoById(long id) {
		produtosDAO.deleteById(id);
	}

	// este metodo nunca eh chamado, mas se precisar estah aqui
	@Override
	public long size() {
		return produtosDAO.count();
	}

	public void deleteAllProducts() {
		produtosDAO.deleteAll();
	}

	@Override
	public Produto findById(long id) {
		return produtosDAO.findById(id).get();
	}

	@Override
	public boolean doesProdutoExist(Produto produto) {
		return produtosDAO.existsByCodigoBarra(produto.getCodigoBarra());
	}

	public List<Produto> getBySituacao(SituacaoProduto situacao) {
		return this.produtosDAO.getBySituacao(situacao);
	}
	
}
