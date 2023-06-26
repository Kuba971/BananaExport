package com.bananaexport.service

import com.bananaexport.dao.ClientDao
import com.bananaexport.domain.dto.ClientDto
import com.bananaexport.domain.dto.OrderDto
import com.bananaexport.domain.entity.Client
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ClientService {

    @Autowired
    private lateinit var clientDao: ClientDao

    fun getAllClientWithOrders(): List<ClientDto>? =
        clientDao.findAll().map { client ->
            ClientDto(
                id = client.id,
                firstName = client.firstName,
                lastName = client.lastName,
                adress = client.adress,
                zipcode = client.zipcode,
                town = client.town,
                country = client.country,
                orders = client.orders?.map { order ->
                    OrderDto(
                        id = order.id,
                        deliveryDate = order.deliveryDate,
                        bananaWeight = order.bananaWeight,
                        price = order.price
                    )
                }
            )
        }

    fun findAll(): List<ClientDto>? =
        clientDao.findAll().map { client ->
            ClientDto(
                id = client.id,
                firstName = client.firstName,
                lastName = client.lastName,
                adress = client.adress,
                zipcode = client.zipcode,
                town = client.town,
                country = client.country,
                orders = emptyList()
            )
        }

    fun getClientByIdWithOrders(id: Long): ClientDto? =
        clientDao.findByIdOrNull(id)?.let { client ->
            ClientDto(
                id = client.id,
                firstName = client.firstName,
                lastName = client.lastName,
                adress = client.adress,
                zipcode = client.zipcode,
                town = client.town,
                country = client.country,
                orders = client.orders?.map { order ->
                    OrderDto(
                        id = order.id,
                        deliveryDate = order.deliveryDate,
                        bananaWeight = order.bananaWeight,
                        price = order.price
                    )
                }
            )
        }

    fun updateClientById(existingClient: Client, newClient: ClientDto): ClientDto {
        val updatedClient: Client = existingClient
            .copy(
                firstName = newClient.firstName,
                lastName = newClient.lastName,
                adress = newClient.adress,
                zipcode = newClient.zipcode,
                town = newClient.town,
                country = newClient.country
            )
        return clientDao.save(updatedClient).let { client ->
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
    }

    fun saveClient(clientDto: ClientDto): ClientDto {
        val client = clientDto.let { clientDto ->
            Client(
                firstName = clientDto.firstName,
                lastName = clientDto.lastName,
                adress = clientDto.adress,
                zipcode = clientDto.zipcode,
                town = clientDto.town,
                country = clientDto.country
            )
        }
        return clientDao.save(client).let { client ->
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
    }

    fun existAlready(clientDto: ClientDto) : Boolean {
        val clients = clientDao.findAll().map { client ->
            ClientDto(
                firstName = client.firstName,
                lastName = client.lastName,
                adress = client.adress,
                zipcode = client.zipcode,
                town = client.town,
                country = client.country
            )
        }
        return clients.contains(clientDto)
    }

    fun deleteClient(clientId: Long): ResponseEntity<Void> {
        clientDao.deleteById(clientId)
        return ResponseEntity<Void>(HttpStatus.OK)
    }
}