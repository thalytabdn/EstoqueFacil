package com.ufcg.psoft.controllers;

import java.util.List;

import com.ufcg.psoft.enumeration.SituacaoProduto;
import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.service.lote.LoteBean;
import com.ufcg.psoft.service.produto.ProdutoBean;
import com.ufcg.psoft.util.CustomErrorType;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exceptions.ObjetoInvalidoException;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class LoteController {
	
	@Autowired
	private ProdutoBean produtoBean;

	@Autowired
    private LoteBean loteBean;
    
    public LoteController(ProdutoBean produtoBean, LoteBean loteBean){
		super();
		this.produtoBean = produtoBean;
		this.loteBean = loteBean;
	}

	// -------------------Criar Lote-------------------
    @PostMapping("/admin/lote/{id}")
	public ResponseEntity<?> criarLote(@PathVariable("id") long produtoId, @RequestBody Lote lote) throws Exception {
		Produto produto = produtoBean.findById(produtoId);

		if (!produtoBean.doesProdutoExist(produto)){
			return new ResponseEntity(
					new CustomErrorType("Unable to create lote. Produto with id " + produtoId + " not found."),
					HttpStatus.NOT_FOUND);
		}
		
		Lote loteAux = new Lote(produto, lote.getNumeroDeItens(), lote.getDataDeValidade());
		loteBean.saveLote(loteAux);

		try {
			if (produto.getSituacao() == SituacaoProduto.INDISPONIVEL) {
				if (loteAux.getNumeroDeItens() > 0) {
					Produto produtoDisponivel = produto;
					produtoDisponivel.situacao = SituacaoProduto.DISPONIVEL;
					produtoBean.updateProduto(produtoDisponivel);
				}
			}
		} catch (ObjetoInvalidoException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(loteAux, HttpStatus.CREATED);
	}

	// -------------------Listar Lotes-------------------
	@GetMapping("/admin/lote")
	public ResponseEntity<List<Lote>> listAllLotess() {
		List<Lote> lotes = loteBean.findAllLotes();

		if (lotes.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Lote>>(lotes, HttpStatus.OK);
	}
}
