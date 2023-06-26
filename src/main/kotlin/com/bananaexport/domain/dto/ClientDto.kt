package com.bananaexport.domain.dto

data class ClientDto(
    var id: Long = 0,
    var firstName: String,
    var lastName: String,
    var adress: String,
    var zipcode: String,
    var town: String,
    var country: String,
    var orders: List<OrderDto>? = null
)