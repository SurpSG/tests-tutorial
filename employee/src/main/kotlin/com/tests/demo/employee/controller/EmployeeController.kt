package com.tests.demo.employee.controller

import com.tests.demo.employee.controller.dto.EmployeeDto
import com.tests.demo.employee.controller.mapper.EmployeeMapper.toDomain
import com.tests.demo.employee.domain.Employee
import com.tests.demo.employee.service.EmployeeService
import jakarta.validation.constraints.NotBlank
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Validated
@RestController
@RequestMapping("/employees")
data class EmployeeController(
    private val employeeService: EmployeeService,
) {

    @PostMapping
    fun create(
        @RequestBody employeeDto: EmployeeDto
    ): Mono<ResponseEntity<Unit>> {
        val employee: Employee = employeeDto.toDomain()
        return employeeService.create(employee)
            .thenReturn(ResponseEntity.ok().build())
    }

    @GetMapping
    fun findByName(
        @RequestParam @NotBlank name: String
    ): Flux<Employee> {
        return employeeService.findByName(name)
    }
}
