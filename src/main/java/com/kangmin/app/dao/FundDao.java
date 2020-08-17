package com.kangmin.app.dao;

import com.kangmin.app.model.Fund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FundDao extends JpaRepository<Fund, Long> {

    List<Fund> findAll();

    Optional<Fund> findById(int id);

    Optional<Fund> findByName(String name);

    Optional<Fund> findBySymbol(String symbol);

    Optional<Fund> findByNameOrSymbol(String name, String symbol);

    boolean existsAccountByNameOrSymbol(String name, String symbol);
}
