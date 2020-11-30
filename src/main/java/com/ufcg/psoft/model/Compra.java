package com.ufcg.psoft.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.ufcg.psoft.enumeration.StatusCompra;

@Entity
public class Compra {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private BigDecimal valorCompra;
    
    private StatusCompra status;
    
    private Date dataCompra;
    
    @ManyToMany
    private List<Produto> produtos;

    public Compra() {
        this.valorCompra = new BigDecimal(0);
        this.status = StatusCompra.ABERTA;
        this.dataCompra = new Date();
        this.produtos = new ArrayList<Produto>();
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValorCompra() {
        return valorCompra;
    }

    public void addValorCompra(BigDecimal valor) {
        valorCompra = valorCompra.add(valor);
    }

    public StatusCompra getStatus() {
        return status;
    }

    public void setStatus(StatusCompra status) {
        this.status = status;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void addProduto(Produto produto) throws Exception {
        this.produtos.add(produto);
    }

    public void removeProduto(Produto produto){
        this.produtos.remove(produto);
    }
}
