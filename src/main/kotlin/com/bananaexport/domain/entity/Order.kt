package com.bananaexport.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "ORDERS")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0,
    @Column(name = "deliveryDate")
    var deliveryDate: Instant,
    @Column(name = "bananaWeight")
    var bananaWeight: Int,
    @Column(name = "price")
    var price: Double = 0.0,
    @JsonIgnore
    @OneToOne(optional = false)
    @JoinColumn(name = "client_id")
    var client: Client? = null
    )