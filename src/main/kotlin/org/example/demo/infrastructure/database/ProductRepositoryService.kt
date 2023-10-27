package org.example.demo.infrastructure.database

import org.example.demo.domain.Product
import org.example.demo.domain.ports.ProductRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ProductRepositoryService(
    private val productDtoRepository: ProductDtoRepository
) : ProductRepository {

    override fun findByCode(productCode: String): Product? {
        val dto = productDtoRepository.findByCode(productCode) ?: return null
        return ProductMapper.toDomain(dto)
    }

    override fun save(product: Product) {
        productDtoRepository.save(ProductMapper.toDto(product))
    }

    override fun updateProductPrice(productCode: String, price: BigDecimal) {
        productDtoRepository.updateProductPrice(productCode, price)
    }
}

private object ProductMapper {
    fun toDto(product: Product): ProductDto {
        return ProductDto(
            id = product.id,
            code = product.code,
            name = product.name,
            price = product.price
        )
    }

    fun toDomain(product: ProductDto): Product {
        return Product(
            id = product.id,
            code = product.code,
            name = product.name,
            price = product.price
        )
    }
}