package com.bananaexport.controller

import com.bananaexport.dao.OrderDao
import com.bananaexport.domain.dto.OrderDto
import com.bananaexport.domain.entity.Order
import com.bananaexport.service.ClientService
import com.bananaexport.service.OrderService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Duration
import java.time.Instant

@RestController
@RequestMapping("/api")
class OrderController(private val orderDao: OrderDao) {

    @Autowired
    lateinit var orderService: OrderService

    @Autowired
    lateinit var clientController: ClientController

    @GetMapping("/orders")
    fun getAllOrders(): List<OrderDto>? =
        orderService.getAllOrder()


    @PostMapping("/orders")
    fun createNewOrder(@Valid @RequestBody order: OrderDto): ResponseEntity<OrderDto> {
        val duration = Duration.between(Instant.now(), order.deliveryDate)
        return if (order.bananaWeight in 1..9999
            && order.bananaWeight % 25 == 0
            && duration.toDays() > 7) {
            order.client = order.client?.let { clientController.createNewClient(it) }?.body
            ResponseEntity.ok(orderService.saveOrder(order))
        } else {
            ResponseEntity(HttpStatus.PRECONDITION_FAILED)
        }
    }


    @GetMapping("/orders/{id}")
    fun getOrderById(@PathVariable(value = "id") orderId: Long): ResponseEntity<OrderDto> {
        return orderService.findById(orderId).let { order ->
            ResponseEntity.ok(order)
        } ?: (ResponseEntity.notFound().build())
    }

    @PutMapping("/orders/{id}")
    fun updateOrderById(
        @PathVariable(value = "id") orderId: Long,
        @Valid @RequestBody newOrder: OrderDto
    ): ResponseEntity<OrderDto> {
        return orderDao.findById(orderId).map { existingClient ->
            ResponseEntity.ok().body(orderService.updateOrderById(existingClient, newOrder))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/orders/{id}")
    fun deleteOrderById(@PathVariable(value = "id") orderId: Long): ResponseEntity<Void> {
        return orderService.deleteOrder(orderId)
    }
}