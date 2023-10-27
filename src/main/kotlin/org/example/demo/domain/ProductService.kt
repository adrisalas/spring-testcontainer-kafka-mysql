package org.example.demo.domain

import jakarta.annotation.PostConstruct
import org.example.demo.domain.ports.ProductEventPublisher
import org.example.demo.domain.ports.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val productPublisher: ProductEventPublisher
) {

    @PostConstruct
    fun postConstruct() {
        productPublisher.subscribe {
            productRepository.updateProductPrice(it.productCode, it.price)
        }
    }
}