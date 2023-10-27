package org.example.demo.domain

import java.math.BigDecimal

data class ProductPriceChanged(
    val productCode: String,
    val price: BigDecimal
)