package com.kangmin.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@Table(name = "positions")
public class Position {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private Fund fund;

    @ManyToOne
    private Account account;

    private int shares;

    public Position(final Fund fund,
                    final Account account,
                    final int shares) {
        this.fund = fund;
        this.account = account;
        this.shares = shares;
    }
}
