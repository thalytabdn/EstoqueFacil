package com.ufcg.psoft.controllers;

import java.util.List;

import com.ufcg.psoft.enumeration.StatusCompra;
import com.ufcg.psoft.model.Compra;
import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.service.compra.CompraBean;
import com.ufcg.psoft.service.lote.LoteBean;
import com.ufcg.psoft.service.produto.ProdutoBean;
import com.ufcg.psoft.util.CustomErrorType;

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

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CompraController {

    @Autowired
    private CompraBean compraBean;

    @Autowired
    private ProdutoBean produtoBean;

    @Autowired
    private LoteBean LoteBean;

    // -------------------Cria compra-------------------
    @PostMapping("/public/compra")
    public ResponseEntity<Compra> criaCompra(@RequestBody Compra compra){
        compraBean.saveCompra(compra);
        return new ResponseEntity<Compra>(compra, HttpStatus.OK);
    };

    // ------------------- Retorna todos as Compras -------------------
	@GetMapping("/admin/compra")
	public ResponseEntity<List<Compra>> listAllCompras(@RequestParam (required = false) String order) {
		List<Compra> compras = compraBean.findAllCompras(order);

		if (compras.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Compra>>(compras, HttpStatus.OK);
	}

    // -------------------Adiciona produto em Compra-------------------
    @PutMapping("/cliente/compra/{idCompra}/{idProduto}")
    public ResponseEntity<?> addProduto(@PathVariable("idCompra") long idCompra,
        @PathVariable("idProduto") long idProduto) {
        
            
            try {
                Compra compraAux = compraBean.findById(idCompra);
                Produto newProduto = produtoBean.findById(idProduto);

                if(compraAux.getStatus().equals(StatusCompra.FECHADA)){
                    return new ResponseEntity<CustomErrorType>(new CustomErrorType("Compra ja finalizada"),
				HttpStatus.NOT_FOUND);
                }
                
                compraAux.addProduto(newProduto);
                compraAux.addValorCompra(newProduto.getPreco());

            compraBean.saveCompra(compraAux);

			return new ResponseEntity<Compra>(compraAux, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("O produto não pode ser inserido, pois está indisponível"),
				HttpStatus.NOT_FOUND);
		}
    }

    // -------------------Finaliza compra-------------------
    @PutMapping("/cliente/compra/{idCompra}")
    public ResponseEntity<?> finalizarCompra(@PathVariable("idCompra") long idCompra) throws Exception {
        Compra compraAux = compraBean.findById(idCompra);
        List<Produto> produtos = compraAux.getProdutos();

        try {
            LoteBean.diminuiLote(produtos);
        } catch (Exception e) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Produto indisponivel"),
            HttpStatus.NOT_FOUND);
        }
        
        compraAux.setStatus(StatusCompra.FECHADA);
        LoteBean.atualizaSituacaoProduto(produtos);
        compraBean.saveCompra(compraAux);

        return new ResponseEntity<Compra>(compraAux, HttpStatus.OK);        
    }

    // -------------------Cancela compra-------------------
    @DeleteMapping("/compra/{idCompra}")
    public ResponseEntity<?> cancelarCompra(@PathVariable("idCompra") long idCompra) {
        Compra compraAux = compraBean.findById(idCompra);
        List<Produto> produtos = compraAux.getProdutos();

        LoteBean.aumentaLote(produtos);

        compraBean.saveCompra(compraAux);

        if (compraAux == null) {
			return new ResponseEntity(new CustomErrorType("Unable to delete. Compra not found."),
					HttpStatus.NOT_FOUND);
		}

        compraBean.deleteCompraById(idCompra);
		return new ResponseEntity<String>("Compra cancelada", HttpStatus.OK);    
    }
}
