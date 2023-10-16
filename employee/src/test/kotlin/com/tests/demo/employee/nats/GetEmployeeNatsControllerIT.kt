package com.tests.demo.employee.nats

import com.tests.demo.employee.domain.Employee
import com.tests.demo.employee.exception.PetroNonGrataException
import com.tests.demo.employee.service.EmployeeService
import io.nats.client.Connection
import io.nats.client.Message
import org.assertj.core.api.Assertions
import org.assertj.core.api.InstanceOfAssertFactories.STRING
import org.awaitility.Awaitility
import org.junit.jupiter.api.Test
import org.mockito.Mockito.anyString
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import reactor.core.publisher.Flux
import java.time.Duration
import java.util.concurrent.CompletableFuture

@NatsControllerTest
class GetEmployeeNatsControllerIT {

    @MockBean
    private lateinit var employeeService: EmployeeService

    @Autowired
    private lateinit var connection: Connection

    @Test
    fun `should return success result`() {
        // GIVEN
        val allowedName = "John Doe"

        `when`(employeeService.findByName(anyString())).thenReturn(
            Flux.just(
                Employee(allowedName, 0.0, null)
            )
        )

        // WHEN
        val actualResponse: CompletableFuture<Message> = connection.request(
            GetEmployeeNatsController.SUBJECT,
            allowedName.toByteArray(),
        )

        // THEN
        Awaitility.await().until { actualResponse.isDone }
        Assertions.assertThat(actualResponse.get())
            .extracting { it.data.decodeToString() }
            .isEqualTo("Success:\nJohn Doe")

        Assertions.assertThat(actualResponse)
            .succeedsWithin(Duration.ofSeconds(10))
            .extracting { it.data.decodeToString() }
            .isEqualTo("Success:\nJohn Doe")
    }

    @Test
    fun `should return failure result when passed not allowed name`() {
        // GIVEN
        val notAllowedName = "<find-by-name-nats-tests-not-allowed-name>"

        `when`(employeeService.findByName(anyString())).thenReturn(
            Flux.error(PetroNonGrataException(notAllowedName))
        )

        // WHEN
        val actualResponse: CompletableFuture<Message> = connection.request(
            GetEmployeeNatsController.SUBJECT,
            notAllowedName.toByteArray(),
        )

        // THEN
        Awaitility.await().until { actualResponse.isDone }
        Assertions.assertThat(actualResponse.get())
            .extracting { it.data.decodeToString() }
            .asInstanceOf(STRING)
            .startsWith("Error:")
            .contains(notAllowedName)
    }
}
