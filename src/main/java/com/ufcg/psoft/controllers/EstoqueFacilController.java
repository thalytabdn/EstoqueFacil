package com.ufcg.psoft.controllers;

import java.util.Date;
import java.util.List;

import com.ufcg.psoft.enumeration.SituacaoProduto;
import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.service.lote.LoteBean;
import com.ufcg.psoft.service.produto.ProdutoBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class EstoqueFacilController {

    @Autowired
    private LoteBean loteBean;

    @Autowired
    private ProdutoBean produtoBean;

    // Retorna quais produtos estao em baixa
    @GetMapping("/relatorio/lotesBaixa")
    public ResponseEntity<?> produtosEmBaixa() {

        List<Lote> lotesBaixa = loteBean.getProdutosEmBaixa();

        if (lotesBaixa.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }

        return new ResponseEntity<List<Lote>>(lotesBaixa, HttpStatus.OK);
    };

    // Retorna quais lotes estao proximo do vencimento
    @GetMapping("/relatorio/lotesProxVencimento")
    public ResponseEntity<?> lotesProxVencimento() {
        Date dataHoje = new Date(System.currentTimeMillis());

        List<Lote> lotesProxVencimento = loteBean.getLotesProxVencimento(dataHoje);

        if (lotesProxVencimento.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }

        return new ResponseEntity<List<Lote>>(lotesProxVencimento, HttpStatus.OK);
    };

    // Retorna o relatorio geral com informações de produtos, vendas e receita
    @GetMapping("/relatorioGeral")
    public ResponseEntity<?> relatorioGeral() {
        List<Produto> produtos = this.produtoBean.findAllProdutos(null);

        List<Object> relatorioGeral = this.loteBean.getRegistro(produtos);

        if (produtos.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }

        return new ResponseEntity<List<Object>>(relatorioGeral, HttpStatus.OK);
    };

    // Retorna quais lotes estao proximo do vencimento
    @GetMapping("/produtosEmFalta")
    public ResponseEntity<?> produtosIndisponiveis() {
        List<Produto> produtosEmFalta = produtoBean.getBySituacao(SituacaoProduto.INDISPONIVEL);

        if (produtosEmFalta.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }

        return new ResponseEntity<List<Produto>>(produtosEmFalta, HttpStatus.OK);
    };

}