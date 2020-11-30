package com.ufcg.psoft.service.compra;

import java.math.BigDecimal;
import java.util.List;

import com.ufcg.psoft.enumeration.StatusCompra;
import com.ufcg.psoft.model.Compra;
import com.ufcg.psoft.repositories.ComprasRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("compraService")
public class CompraBean implements CompraService {

    @Autowired
    private ComprasRepository<Compra, Long> compraDAO;

    @Override
    public List<Compra> findAllCompras(String order) {
        if (order == null) {
            order = "";
        }
        if (order.equals("data")) {
            return compraDAO.getCompraByDataCompra();
        } else if (order.equals("preco")) {
            return compraDAO.getCompraByValorCompra();
        } else if (order.equals("status")) {
            return compraDAO.getCompraByStatus();
        } else {
            return compraDAO.findAll();
        }
    }

    @Override
    public Compra saveCompra(Compra compra) {
        return compraDAO.save(compra);
    }

    @Override
    public void updateCompra(Compra compra) {
        compraDAO.save(compra);
    }

    @Override
    public void deleteCompraById(long id) {
        compraDAO.deleteById(id);
    }

    @Override
    public long size() {
        return compraDAO.count();
    }

    @Override
    public Compra findById(long id) {
        return compraDAO.findById(id).get();
    }

    public List<Compra> getByStatus(StatusCompra status) {
        return this.compraDAO.getByStatus(status);
    }
}
