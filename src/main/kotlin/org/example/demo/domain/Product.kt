package org.example.demo.domain

import java.math.BigDecimal

data class Product(
    val id: Long?,
    val code: String,
    val name: String,
    val price: BigDecimal
)
