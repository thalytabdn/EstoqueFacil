package com.ufcg.psoft.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.model.DTO.ProdutoDTO;
import com.ufcg.psoft.service.lote.LoteBean;
import com.ufcg.psoft.service.produto.ProdutoBean;
import com.ufcg.psoft.util.CustomErrorType;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProdutoController {

	@Autowired
	private ProdutoBean produtoBean;

	public ProdutoController(ProdutoBean produtoBean, LoteBean loteBean){
		super();
		this.produtoBean = produtoBean;
	}

	// ------------------- Retorna todos os Produtos -------------------
	@GetMapping("/public/produto")
	public ResponseEntity<List<Produto>> listAllProdutos(@RequestParam (required = false) String order) {
		List<Produto> produtos = produtoBean.findAllProdutos(order);

		if (produtos.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}

	// -------------------Criar um Produto-------------------
	@PostMapping("/admin/produto")
	public ResponseEntity<?> criarProduto(@RequestBody Produto produto) {

		if (produtoBean.doesProdutoExist(produto)){
			return new ResponseEntity(new CustomErrorType("O produto " + produto.getNome() + " do fabricante "
					+ produto.getFabricante() + " ja esta cadastrado!"), HttpStatus.CONFLICT);
		}

		produtoBean.saveProduto(produto);
		return new ResponseEntity<Produto>(produto, HttpStatus.CREATED);
	}

	// -------------------Consultar um Produto-------------------
	@GetMapping("/public/produto/{id}")
	public ResponseEntity<?> consultarProduto(@PathVariable("id") long id) {

		try {
			Produto produtoAux = produtoBean.findById(id);
			ProdutoDTO produtoDTO = new ProdutoDTO(produtoAux);
			
			return new ResponseEntity<ProdutoDTO>(produtoDTO, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Produto with id " + id + " not found"),
				HttpStatus.NOT_FOUND);
		}

	}

	// ------------------- Update Produto -------------------
	@PutMapping("/admin/produto/{id}")
	public ResponseEntity<?> updateProduto(@PathVariable("id") long id, @RequestBody Produto produto) {

		Produto produtoAux = produtoBean.findById(id);

		if(!produtoBean.doesProdutoExist(produtoAux)){
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Produto with id " + id + " not found"),
				HttpStatus.NOT_FOUND);
		}

		//Verificacoes que permitem alterar apenas um parametro
		if(produto.getNome() != null){
			produtoAux.mudaNome(produto.getNome());
		}

		if(produto.getPreco() != null){
			produtoAux.setPreco(produto.getPreco());
		}

		if(produto.getCodigoBarra() != null){
			produtoAux.setCodigoBarra(produto.getCodigoBarra());
		}

		if(produto.getFabricante() != null){
			produtoAux.mudaFabricante(produto.getFabricante());
		}

		if(produto.getCategoria() != null){
			produtoAux.mudaCategoria(produto.getCategoria());
		}

		produtoBean.updateProduto(produtoAux);
		return new ResponseEntity<Produto>(produtoAux, HttpStatus.OK);
	}


	// ------------------- Delete Produto -------------------
	@DeleteMapping("/admin/produto/{id}")
	public ResponseEntity<?> deleteProduto(@PathVariable("id") long id) {

		Produto produtoAux = produtoBean.findById(id);

		if (produtoAux == null) {
			return new ResponseEntity(new CustomErrorType("Unable to delete. Produto with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		produtoBean.deleteProdutoById(id);
		return new ResponseEntity<Produto>(HttpStatus.NO_CONTENT);
	}

	
}
