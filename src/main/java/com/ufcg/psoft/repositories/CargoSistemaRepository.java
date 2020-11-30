package com.ufcg.psoft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.ufcg.psoft.model.CargoSistema;

@NoRepositoryBean
public interface CargoSistemaRepository extends JpaRepository<CargoSistema, Long> {

}
