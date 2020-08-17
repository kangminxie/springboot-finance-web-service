package com.kangmin.app.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "funds")
public class Fund {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String symbol;

    private double price;

    @Override
    public String toString() {
        return "Fund{"
                + "name='" + name + '\'' + ", "
                + "symbol='" + symbol + '\'' + ", "
                + "price=" + price
                + '}';
    }
}
