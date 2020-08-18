package com.kangmin.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@Table(name = "funds")
public class Fund {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String symbol;

    private double price;

    public Fund(final String name,
                final String symbol,
                final double price) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Fund{"
                + "name='" + name + '\'' + ", "
                + "symbol='" + symbol + '\'' + ", "
                + "price=" + price
                + '}';
    }
}
