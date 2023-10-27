package org.example.demo.domain.ports

import org.example.demo.domain.ProductPriceChanged

typealias ProductPriceChangedSubscriber = (ProductPriceChanged) -> Unit

/**
 * This is to apply Inversion of Control (IoC).
 * You could just inject the domain into the "infra" layer so the infra layer
 * is using the domain (as a RestController would), but I wanted to do it
 * with IoC
 */
interface ProductEventPublisher {
    fun subscribe(eventAction: ProductPriceChangedSubscriber)
}