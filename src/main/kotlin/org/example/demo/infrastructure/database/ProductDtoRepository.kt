package org.example.demo.infrastructure.database

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
interface ProductDtoRepository : CrudRepository<ProductDto, Long> {

    @Query
    fun findByCode(productCode: String): ProductDto?

    @Modifying
    @Query("UPDATE products p SET p.price = :price WHERE p.code = :productCode")
    fun updateProductPrice(productCode: String, price: BigDecimal)
}