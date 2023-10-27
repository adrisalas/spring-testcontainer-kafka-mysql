package org.example.demo.infrastructure.kafka

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class ProductPriceChangedDto(
    @JsonProperty("productCode") val productCode: String,
    @JsonProperty("price") val price: BigDecimal
)