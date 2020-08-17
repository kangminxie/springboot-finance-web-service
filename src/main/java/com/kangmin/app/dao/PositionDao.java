package com.kangmin.app.dao;

import com.kangmin.app.model.Account;
import com.kangmin.app.model.Fund;
import com.kangmin.app.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PositionDao extends JpaRepository<Position, Integer> {

    Optional<Position> findByFundAndAccount(Fund fund, Account account);

    List<Position> findByAccount(Account account);
}
