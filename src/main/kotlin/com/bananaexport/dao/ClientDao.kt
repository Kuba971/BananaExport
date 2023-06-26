package com.bananaexport.dao

import com.bananaexport.domain.entity.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientDao : JpaRepository<Client, Long>