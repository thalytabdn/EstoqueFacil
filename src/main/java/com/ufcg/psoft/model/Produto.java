package com.ufcg.psoft.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ufcg.psoft.enumeration.DescontoProduto;
import com.ufcg.psoft.enumeration.SituacaoProduto;
import com.ufcg.psoft.model.Desconto.BomDesconto;

import exceptions.ObjetoInvalidoException;

@Entity
public class Produto implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String nome;

	private BigDecimal preco;

	private String codigoBarra;

	private String fabricante;

	private String categoria;

	public SituacaoProduto situacao; // usa variaveis estaticas abaixo


	private DescontoProduto desconto;

	public Produto() {
		this.id = 0;
		this.preco = new BigDecimal(0);
		this.desconto = DescontoProduto.SEM_DESCONTO;
	}

	public Produto(long id, String nome, String codigoBarra, String fabricante,
			String nomeCategoria, DescontoProduto desconto){
		this.id = id;
		this.nome = nome;
		this.preco = new BigDecimal(0);
		this.codigoBarra = codigoBarra;
		this.fabricante = fabricante;
		this.categoria = nomeCategoria;
		this.situacao = SituacaoProduto.DISPONIVEL;
		this.desconto = desconto;
	}

	public String getNome() {
		return nome;
	}

	public void mudaNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getPreco() {

        if (desconto.equals(desconto.BOM_DESCONTO)){
            BigDecimal valorComDesconto = new BomDesconto(this.preco).calculaDesconto();
            this.preco = valorComDesconto;
        }
        else if (desconto.equals(desconto.OTIMO_DESCONTO)){
            BigDecimal valorComDesconto = new BomDesconto(this.preco).calculaDesconto();
            this.preco = valorComDesconto;
		}
		else if (desconto.equals(desconto.SUPER_DESCONTO)){
            BigDecimal valorComDesconto = new BomDesconto(this.preco).calculaDesconto();
            this.preco = valorComDesconto;
		}

        return preco; //Formatar para duas casas decimais
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public long getId() {
		return id;
	}

	public void mudaId(long codigo) {
		this.id = codigo;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void mudaFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public String getCategoria() {
		return this.categoria;
	}

	public void mudaCategoria(String categoria) {
		this.categoria = categoria;
	}
		
	public void mudaSituacao(SituacaoProduto situacao) throws ObjetoInvalidoException {
		if (situacao.equals(SituacaoProduto.DISPONIVEL)){
			this.situacao = SituacaoProduto.DISPONIVEL;
		}else if (situacao.equals(SituacaoProduto.INDISPONIVEL)){
			this.situacao = SituacaoProduto.INDISPONIVEL;
		}else{
			throw new ObjetoInvalidoException("Situacao Invalida");
		}
	}

	public SituacaoProduto getSituacao() throws ObjetoInvalidoException {
		return this.situacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fabricante == null) ? 0 : fabricante.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (fabricante == null) {
			if (other.fabricante != null)
				return false;
		} else if (!fabricante.equals(other.fabricante))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	public DescontoProduto getDesconto() {
		return desconto;
	}

	public void setDesconto(DescontoProduto desconto) {
		this.desconto = desconto;
	}
}
