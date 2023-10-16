package com.tests.demo.employee.nats

import com.tests.demo.employee.service.EmployeeService
import io.nats.client.Connection
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Component
class GetEmployeeNatsController(
    private val connection: Connection,
    private val employeeService: EmployeeService,
) {

    @PostConstruct
    private fun initialize() {
        connection.createDispatcher { message ->
            val parsedData: String = message.data.decodeToString()

            handler(parsedData)
                .onErrorResume {
                    Mono.just("Error:\n${it.message}")
                }
                .doOnNext { response ->
                    connection.publish(message.replyTo, response.toByteArray())
                }
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe()

        }.subscribe(SUBJECT)
    }

    fun handler(request: String): Mono<String> {
        return employeeService.findByName(request)
            .reduce(StringBuilder("Success:")) { builder, employee ->
                builder.append("\n").append(employee.name)
            }
            .map { it.toString() }
    }

    companion object {
        const val SUBJECT = "GET_ALL_EMPLOYEES_SUBJECT"
    }
}
