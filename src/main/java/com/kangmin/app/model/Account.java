package com.kangmin.app.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    private String accountId;

    private String email;

    private String username;

    private String name;

    private String password;

    private String role;

    private double cash;

    public Account() {
    }

    public Account(final String accountId,
                   final String email,
                   final String username,
                   final String name,
                   final String role,
                   final String password) {
        this(accountId, email, username, name, role, password, 0.0);
    }
    public Account(final String accountId,
                   final String email,
                   final String username,
                   final String name,
                   final String role,
                   final String password,
                   final double cash) {
        this.accountId = accountId;
        this.email = email;
        this.username = username;
        this.name = name;
        this.role = role;
        this.password = password;
        this.cash = cash;
    }
}
