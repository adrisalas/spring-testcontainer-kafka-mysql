package org.example.demo.domain.ports

import org.example.demo.domain.Product
import java.math.BigDecimal

interface ProductRepository {
    fun findByCode(productCode: String): Product?
    fun save(product: Product)
    fun updateProductPrice(productCode: String, price: BigDecimal)
}