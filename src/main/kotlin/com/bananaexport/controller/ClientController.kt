package com.bananaexport.controller

import com.bananaexport.domain.entity.Client
import com.bananaexport.dao.ClientDao
import com.bananaexport.domain.dto.ClientDto
import com.bananaexport.service.ClientService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class ClientController(private val clientDao: ClientDao) {

    @Autowired
    lateinit var clientService: ClientService

    @GetMapping("/clients")
    fun getAllClients(): List<ClientDto>? =
        clientService.findAll()


    @PostMapping("/clients")
    fun createNewClient(@Valid @RequestBody clientDto: ClientDto): ResponseEntity<ClientDto> {
        return if (clientService.existAlready(clientDto)) {
            ResponseEntity(HttpStatus.PRECONDITION_FAILED)
        } else {
            ResponseEntity.ok(clientService.saveClient(clientDto))
        }
    }

    @GetMapping("/clients/{id}")
    fun getClientById(@PathVariable(value = "id") clientId: Long): ResponseEntity<ClientDto> {
        return clientService.getClientByIdWithOrders(clientId).let { client ->
            ResponseEntity.ok(client)
        } ?: (ResponseEntity.notFound().build())
    }

    @PutMapping("/clients/{id}")
    fun updateClientById(
        @PathVariable(value = "id") clientId: Long,
        @Valid @RequestBody newClient: ClientDto
    ): ResponseEntity<ClientDto> {
        return clientDao.findById(clientId).map { existingClient ->
            ResponseEntity.ok().body(clientService.updateClientById(existingClient, newClient))
        }.orElse(ResponseEntity.notFound().build())

    }

    @DeleteMapping("/clients/{id}")
    fun deleteClientById(@PathVariable(value = "id") clientId: Long): ResponseEntity<Void> {
        return clientService.deleteClient(clientId)
    }

}