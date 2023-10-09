package com.tests.demo.employee.repository

import com.tests.demo.employee.domain.Department
import com.tests.demo.employee.domain.Employee
import com.tests.demo.employee.repository.model.MongoDepartment
import com.tests.demo.employee.repository.model.MongoEmployee
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class EmployeeRepository(
    private val mongoTemplate: ReactiveMongoTemplate,
) {

    fun create(employee: Employee): Mono<Unit> {
        return mongoTemplate.insert(employee.toMongoEmployee())
            .thenReturn(Unit)
    }

    fun findByName(name: String): Flux<Employee> {
        val query = Criteria.where("name").`is`(name).let(::Query)
        return mongoTemplate.find(query, MongoEmployee::class.java)
            .map { it.toDomainEmployee() }
            .doOnNext {
                println(it)
            }
    }

    private fun Employee.toMongoEmployee() = MongoEmployee(
        name = name,
        salary = salary,
        department = department?.let { MongoDepartment.valueOf(it.name) },
    )

    private fun MongoEmployee.toDomainEmployee() = Employee(
        name = name,
        salary = salary,
        department = department?.let { com.tests.demo.employee.domain.Department.valueOf(it.name) },
    )
}
