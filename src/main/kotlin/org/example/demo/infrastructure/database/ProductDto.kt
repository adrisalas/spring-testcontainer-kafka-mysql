package org.example.demo.infrastructure.database

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table(name = "products")
data class ProductDto(
    @Id val id: Long?,
    val code: String,
    val name: String,
    val price: BigDecimal
)