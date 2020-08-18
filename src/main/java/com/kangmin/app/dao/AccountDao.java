package com.kangmin.app.dao;

import com.kangmin.app.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountDao extends JpaRepository<Account, String> {

    List<Account> findAll();

    Optional<Account> findById(String accountId);

    // Optional<Account> findByEmail(String email);

    Optional<Account> findByUsername(String username);

    boolean existsAccountByEmailOrUsername(String email, String username);
}
