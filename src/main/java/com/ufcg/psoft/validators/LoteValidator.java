package com.ufcg.psoft.validators;

import com.ufcg.psoft.enumeration.SituacaoProduto;
import com.ufcg.psoft.model.Lote;

import org.springframework.stereotype.Component;

@Component
public class LoteValidator {

    public void ValidLote(Lote lote) throws Exception {
        if(lote.getNumeroDeItens() <= 0) throw new Exception("Numero de produtos menor ou igual a 0");
        if(lote.getProduto().situacao.equals(SituacaoProduto.INDISPONIVEL)) throw new Exception("Produto indisponível");
    }
}

