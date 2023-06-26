package com.bananaexport.domain.dto

import java.time.Instant

data class OrderDto(
    var id: Long = 0,
    var deliveryDate: Instant,
    var bananaWeight: Int,
    var price: Double = 0.0,
    var client: ClientDto? = null
    )