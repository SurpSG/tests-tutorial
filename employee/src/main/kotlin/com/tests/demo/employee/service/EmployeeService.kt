package com.tests.demo.employee.service

import com.tests.demo.employee.domain.Employee
import com.tests.demo.employee.exception.PetroNonGrataException
import com.tests.demo.employee.repository.EmployeeRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux

@Service
class EmployeeService(
    private val employeeRepository: EmployeeRepository,
) {

    fun create(employee: Employee): Mono<Unit> {
        return employeeRepository.create(employee)
    }

    fun findByName(name: String): Flux<Employee> {
        log.info("Finding employee by name: {}", name)
        return if (name.compareTo("petro", true) == 0) {
            PetroNonGrataException("$name is not allowed here! $name go away!").toFlux()
        } else {
            employeeRepository.findByName(name)
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(EmployeeService::class.java)
    }
}
