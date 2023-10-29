package org.example.demo.domain

import org.example.demo.domain.ports.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository
) {

    fun handleEvent(productPriceChanged: ProductPriceChanged) {
        productRepository.updateProductPrice(productPriceChanged.productCode, productPriceChanged.price)
    }
}