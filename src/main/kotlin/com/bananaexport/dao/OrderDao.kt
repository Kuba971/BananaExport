package com.bananaexport.dao

import com.bananaexport.domain.entity.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderDao : JpaRepository<Order, Long>