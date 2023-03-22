package com.example.quick_cheque.model

import java.math.BigDecimal

class Product {
    var name: String;
    var price: BigDecimal;
    var count: Int;

    constructor(name: String, price: BigDecimal, count: Int) {
        this.name = name;
        this.price = price;
        this.count = count;
    }
}
