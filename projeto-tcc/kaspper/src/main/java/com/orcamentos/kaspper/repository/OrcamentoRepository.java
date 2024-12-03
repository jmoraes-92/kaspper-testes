package com.orcamentos.kaspper.repository;

import com.orcamentos.kaspper.model.Orcamento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {
    List<Orcamento> findByDemandaId(Integer idDemanda);
}

