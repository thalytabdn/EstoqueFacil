package com.ufcg.psoft.service.lote;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.enumeration.SituacaoProduto;
import com.ufcg.psoft.enumeration.StatusCompra;
import com.ufcg.psoft.model.Compra;
import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.model.RegistroGeral;
import com.ufcg.psoft.repositories.LotesRepository;
import com.ufcg.psoft.service.compra.CompraBean;
import com.ufcg.psoft.validators.LoteValidator;

@Service("loteService")
public class LoteBean implements LoteService {

	@Autowired
	private LotesRepository<Lote, Long> lotesDAO;

	@Autowired
	private LoteValidator validaLote;

	@Autowired
	private CompraBean compraBean;

	private static final AtomicLong counter = new AtomicLong();

	@Override
	public Lote saveLote(Lote lote) throws Exception {
		validaLote.ValidLote(lote);
		lote.setId(counter.incrementAndGet());
		lotesDAO.save(lote);
		return lote;
	}

	@Override
	public Lote findById(long id) {
		return lotesDAO.findById(id).get();
	}

	public List<Lote> getLotesProxVencimento(Date dataHoje) {
		dataHoje.setDate(dataHoje.getDate() + 30);
		return lotesDAO.findAllByDataDeValidadeLessThanEqual(dataHoje);
	}

	@Override
	public List<Lote> getProdutosEmBaixa() {
		return this.lotesDAO.findAllByNumeroDeItensLessThan(15);
	}

	@Override
	public void diminuiLote(List<Produto> produtos) throws Exception {
		for (Produto produto : produtos) {
			Lote loteAux = this.lotesDAO.findByProduto(produto).get();
			validaLote.ValidLote(loteAux);
			loteAux.diminuiNumeroDeItens();
		}
	}

	@Override
	public void aumentaLote(List<Produto> produtos) {
		for (Produto produto : produtos) {
			Lote loteAux = this.lotesDAO.findByProduto(produto).get();
			loteAux.addNumeroDeItens();
		}
	}

	@Override
	public void deleteLoteById(long id) {
		lotesDAO.deleteById(id);
	}

	@Override
	public List<Lote> findAllLotes() {
		return lotesDAO.findAll();
	}

	@Override
	public long size() {
		return lotesDAO.count();
	}

	public Lote findByProduto(Produto newProduto) {
		return this.findByProduto(newProduto);
	}

	public List<Object> getRegistro(List<Produto> produtos) {
		List<RegistroGeral> registrosGerais = new ArrayList<>();
		List<Compra> comprasFinalizadas = compraBean.getByStatus(StatusCompra.FECHADA);
		List<Object> registroFinal = new ArrayList<>();

		for (Produto produto : produtos) {
			List<Lote> lotes = new ArrayList<>();
			List<Date> datasDeValidade = new ArrayList<>();

			lotes = this.lotesDAO.findAllByProduto(produto);

			int numeroDeItens = 0;
			int numeroDeLotes = lotes.size();

			for (Lote lote : lotes) {
				datasDeValidade.add(lote.getDataDeValidade());
				numeroDeItens = numeroDeItens + lote.getNumeroDeItens();
			}

			RegistroGeral newRegistro = new RegistroGeral(produto, numeroDeLotes, numeroDeItens, datasDeValidade);
			registrosGerais.add(newRegistro);
		}

		BigDecimal receitaArrecadada = new BigDecimal(0);
		for (Compra compra : comprasFinalizadas) {
			receitaArrecadada = receitaArrecadada.add(compra.getValorCompra());
		}

		registroFinal.add(registrosGerais);
		registroFinal.add(comprasFinalizadas);
		registroFinal.add("Receita arrecadada: R$" + receitaArrecadada.toString());

		return registroFinal;

	}

	public void atualizaSituacaoProduto(List<Produto> produtos) throws Exception {
		for (Produto produto : produtos) {
			Lote loteAux = this.lotesDAO.findByProduto(produto).get();
			
			if(loteAux.getNumeroDeItens() == 0){
				produto.mudaSituacao(SituacaoProduto.INDISPONIVEL);
				produto.setPreco(null);
			}
		}
	}

}
