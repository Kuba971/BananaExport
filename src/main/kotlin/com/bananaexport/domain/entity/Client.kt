package com.bananaexport.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "CLIENT")
data class Client(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0,
    @Column(name = "firstName")
    var firstName: String,
    @Column(name = "lastName")
    var lastName: String,
    @Column(name = "adress")
    var adress: String,
    @Column(name = "zipcode")
    var zipcode: String,
    @Column(name = "town")
    var town: String,
    @Column(name = "country")
    var country: String,
    @OneToMany(mappedBy="client", cascade = [CascadeType.ALL])
    var orders: List<Order>? = null
    )