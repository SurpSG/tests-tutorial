package com.tests.demo.employee.service

import com.tests.demo.employee.domain.Department
import com.tests.demo.employee.domain.Employee
import com.tests.demo.employee.exception.PetroNonGrataException
import com.tests.demo.employee.repository.EmployeeRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.test.test
import reactor.test.StepVerifier

@ExtendWith(MockKExtension::class)
class EmployeeServiceTest {

    @MockK
    private lateinit var employeeRepository: EmployeeRepository

    @InjectMockKs
    private lateinit var employeeService: EmployeeService

    @Test
    fun `should throw if name is petro`() {
        // GIVEN
        val notAllowedName = "petro"

        // WHEN // THEN
        StepVerifier.create(employeeService.findByName(notAllowedName))
            .expectError(PetroNonGrataException::class.java)
            .verify()
    }

    @ParameterizedTest
    @EnumSource(Department::class)
    fun `should return employee by name when employee found`(
        department: Department
    ) {
        // GIVEN
        val expectedName = "any allowed employee"
        val expectedEmployee1 = Employee(expectedName, 1.111, department)
        val expectedEmployee2 = Employee(expectedName, 1.222, department)

        every {
            employeeRepository.findByName(expectedName)
        } returns listOf(expectedEmployee1, expectedEmployee2).toFlux()

        // WHEN // THEN
        employeeService.findByName(expectedName)
            .test()
            .expectNext(expectedEmployee1)
            .expectNext(expectedEmployee2)
            .verifyComplete()
    }
}
