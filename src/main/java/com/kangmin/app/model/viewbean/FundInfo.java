package com.kangmin.app.model.viewbean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FundInfo {

    private String name;

    private int shares;

    private double price;
}
