package org.example.demo

import org.assertj.core.api.Assertions.assertThat
import org.example.demo.domain.Product
import org.example.demo.infrastructure.database.ProductRepositoryService
import org.example.demo.infrastructure.kafka.ProductPriceChangedDto
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.TestPropertySource
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.shaded.org.awaitility.Awaitility.await
import org.testcontainers.utility.DockerImageName
import java.math.BigDecimal
import java.time.Duration
import java.util.concurrent.TimeUnit

@SpringBootTest
@TestPropertySource(
    properties = [
        "spring.kafka.consumer.auto-offset-reset=earliest",
        "spring.datasource.url=jdbc:tc:mysql:8.0.32:///db"
    ]
)
@Testcontainers
class ProductPriceChangedHandlerTest {

    companion object {
        @Container
        @JvmStatic
        val kafkaContainer = KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:7.3.3")
        )

        @DynamicPropertySource
        @JvmStatic
        fun overrideProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.kafka.bootstrap-servers") { kafkaContainer.bootstrapServers }
        }
    }


    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, Any>

    @Autowired
    private lateinit var productRepositoryService: ProductRepositoryService

    @BeforeEach
    fun setUp() {
        val product = Product(null, "P100", "Product One", BigDecimal.TEN)
        productRepositoryService.save(product)
    }

    @Test
    fun shouldHandleProductPriceChangedEvent() {
        val productPriceChange = ProductPriceChangedDto("P100", BigDecimal("14.50"))
        kafkaTemplate.send("product-price-changes", productPriceChange.productCode, productPriceChange)

        await()
            .pollInterval(Duration.ofSeconds(3))
            .atMost(10, TimeUnit.SECONDS)
            .untilAsserted {
                val nullableProduct: Product? = productRepositoryService.findByCode("P100")
                assertThat(nullableProduct).isNotNull()
                assertThat(nullableProduct?.code).isEqualTo("P100")
                assertThat(nullableProduct?.price).isEqualTo(BigDecimal("14.50"))
            }
    }

}