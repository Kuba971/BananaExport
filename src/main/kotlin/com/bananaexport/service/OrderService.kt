package com.bananaexport.service

import com.bananaexport.dao.OrderDao
import com.bananaexport.domain.dto.ClientDto
import com.bananaexport.domain.dto.OrderDto
import com.bananaexport.domain.entity.Client
import com.bananaexport.domain.entity.Order
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class OrderService {

    @Autowired
    private lateinit var orderDao: OrderDao

    @Autowired
    private lateinit var clientService: ClientService

    fun getAllOrder(): List<OrderDto>? =
        orderDao.findAll().map { order ->
            OrderDto(
                id = order.id,
                deliveryDate = order.deliveryDate,
                bananaWeight = order.bananaWeight,
                price = order.price,
                client = order.client?.let { client ->
                    ClientDto(
                        id = client.id,
                        firstName = client.firstName,
                        lastName = client.lastName,
                        adress = client.adress,
                        zipcode = client.zipcode,
                        town = client.town,
                        country = client.country
                    )
                }
            )
        }

    fun findById(id: Long): OrderDto? =
        orderDao.findByIdOrNull(id)?.let { order ->
            OrderDto(
                id = order.id,
                deliveryDate = order.deliveryDate,
                bananaWeight = order.bananaWeight,
                price = order.price,
                client = order.client?.let { client ->
                    ClientDto(
                        id = client.id,
                        firstName = client.firstName,
                        lastName = client.lastName,
                        adress = client.adress,
                        zipcode = client.zipcode,
                        town = client.town,
                        country = client.country
                    )
                }
            )
        }

    fun updateOrderById(existingOrder: Order, newOrder: OrderDto): OrderDto {
        val updatedOrder: Order = existingOrder
            .copy(
                deliveryDate = newOrder.deliveryDate,
                bananaWeight = newOrder.bananaWeight,
                price = newOrder.bananaWeight * PRICE_PER_KILO
            )
        return orderDao.save(updatedOrder).let { order ->
            OrderDto(
                id = order.id,
                deliveryDate = order.deliveryDate,
                bananaWeight = order.bananaWeight,
                price = order.price,
                client = order.client?.let { client ->
                    ClientDto(
                        id = client.id,
                        firstName = client.firstName,
                        lastName = client.lastName,
                        adress = client.adress,
                        zipcode = client.zipcode,
                        town = client.town,
                        country = client.country
                    )
                }
            )
        }
    }

    fun saveOrder(orderDto: OrderDto): OrderDto {
        val order = orderDto.let { orderDto ->
            Order(
                deliveryDate = orderDto.deliveryDate,
                bananaWeight = orderDto.bananaWeight,
                price = orderDto.bananaWeight * PRICE_PER_KILO,
                client = orderDto.client?.let { clientDto ->
                    Client(
                        id = clientDto.id,
                        firstName = clientDto.firstName,
                        lastName = clientDto.lastName,
                        adress = clientDto.adress,
                        zipcode = clientDto.zipcode,
                        town = clientDto.town,
                        country = clientDto.country
                    )
                }
            )
        }
        return orderDao.save(order).let { order ->
            OrderDto(
                id = order.id,
                deliveryDate = order.deliveryDate,
                bananaWeight = order.bananaWeight,
                price = order.price,
                client = order.client?.let { client ->
                    ClientDto(
                        id = client.id,
                        firstName = client.firstName,
                        lastName = client.lastName,
                        adress = client.adress,
                        zipcode = client.zipcode,
                        town = client.town,
                        country = client.country
                    )
                }
            )
        }
    }

    fun deleteOrder(orderId: Long): ResponseEntity<Void> {
        orderDao.deleteById(orderId)
        return ResponseEntity<Void>(HttpStatus.OK)
    }

    companion object {
        const val PRICE_PER_KILO = 2.50
    }
}