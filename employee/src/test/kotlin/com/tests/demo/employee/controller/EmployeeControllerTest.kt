package com.tests.demo.employee.controller

import com.tests.demo.employee.domain.Department
import com.tests.demo.employee.domain.Employee
import com.tests.demo.employee.exception.PetroNonGrataException
import com.tests.demo.employee.service.EmployeeService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito.anyString
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux

@ControllerTest
class EmployeeControllerTest {

    @MockBean
    private lateinit var employeeService: EmployeeService

    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    fun `should respond 200 with employees by name`() {
        // GIVEN
        val expectedEmployees = listOf(EMPLOYEE_1, EMPLOYEE_2)
        `when`(employeeService.findByName(anyString())).thenReturn(
            Flux.fromIterable(expectedEmployees)
        )

        // WHEN
        val response = webClient.get()
            .uri { builder ->
                builder.path("/employees")
                    .queryParam("name", "World")
                    .build()
            }
            .exchange()

        // THEN
        response
            .expectStatus().isOk
            .expectBodyList(Employee::class.java)
            .hasSize(expectedEmployees.size).contains(
                *expectedEmployees.toTypedArray()
            )
    }

    @Test
    fun `should respond 406 when employee name is forbidden`() {
        // GIVEN
        val expectedError = "forbidden name controller test"
        `when`(employeeService.findByName(anyString())).thenReturn(
            Flux.error(PetroNonGrataException(expectedError))
        )

        // WHEN
        val response = webClient.get()
            .uri { builder ->
                builder.path("/employees")
                    .queryParam("name", "forbidden name")
                    .build()
            }
            .exchange()

        // THEN
        response
            .expectStatus().isEqualTo(HttpStatus.NOT_ACCEPTABLE)
            .expectBody(String::class.java).consumeWith {
                Assertions.assertThat(it.responseBody).contains(
                    "Reason",
                    expectedError
                )
            }
    }

    private companion object {
        val EMPLOYEE_1 = Employee("controller-test-employee-1", 1.2, Department.HR)
        val EMPLOYEE_2 = Employee("controller-test-employee-2", 3.3, Department.IT)
    }
}
