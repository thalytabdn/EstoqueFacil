package com.ufcg.psoft.service.lote;

import java.util.List;
import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;

public interface LoteService {
    
    Lote saveLote(Lote lote) throws Exception;

    Lote findById(long id);

    void diminuiLote(List<Produto> produtos) throws Exception;

    void aumentaLote(List<Produto> produtos);

    List<Lote> findAllLotes();

    void deleteLoteById(long id);

    long size();

    List<Lote> getProdutosEmBaixa();
}
