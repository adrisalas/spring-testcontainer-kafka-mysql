package org.example.demo.infrastructure.kafka

import org.example.demo.domain.ProductPriceChanged
import org.example.demo.domain.ProductService
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class ProductPriceChangedEventHandler(private val productService: ProductService) {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @KafkaListener(topics = ["product-price-changes"], groupId = "demo")
    fun handleChangeOfProductPrice(dto: ProductPriceChangedDto) {
        val productPriceChanged = ProductPriceChanged(dto.productCode, dto.price)

        log.info(
            "Received a ProductPriceChangedEvent with productCode:{}: ",
            productPriceChanged.productCode
        )

        productService.handleEvent(productPriceChanged)
    }
}