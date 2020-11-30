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
public class ProdutoBean implements ProdutoService {

    @Autowired
    private ProdutosRepository<Produto, Long> produtosDAO;

    private static final AtomicLong counter = new AtomicLong();

    @Override
    public List<Produto> findAllProdutos(String order) {
        if (order == null) {
            order = "";
        }
        if (order.equals("nome")) {
            return produtosDAO.getProdutoByNome();
        } else if (order.equals("preco")) {
            return produtosDAO.getProdutoByPreco();
        } else if (order.equals("categoria")) {
            return produtosDAO.getProdutoByCategoria();
        } else {
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
