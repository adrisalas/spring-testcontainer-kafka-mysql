package org.example.demo.infrastructure.kafka

import org.example.demo.domain.ProductPriceChanged
import org.example.demo.domain.ports.ProductEventPublisher
import org.example.demo.domain.ports.ProductPriceChangedSubscriber
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class ProductPriceChangedEventHandler : ProductEventPublisher {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    private val subscribers = ArrayList<ProductPriceChangedSubscriber>()

    override fun subscribe(eventAction: ProductPriceChangedSubscriber) {
        subscribers.add(eventAction)
    }

    @KafkaListener(topics = ["product-price-changes"], groupId = "demo")
    fun handle(event: ProductPriceChangedDto) {
        val productPriceChanged = ProductPriceChanged(event.productCode, event.price)

        log.info(
            "Received a ProductPriceChangedEvent with productCode:{}: ",
            productPriceChanged.productCode
        )

        subscribers.forEach {
            it.invoke(productPriceChanged)
        }
    }
}