package com.tests.demo.employee.repository

import com.tests.demo.employee.domain.Department
import com.tests.demo.employee.domain.Employee
import com.tests.demo.employee.repository.model.MongoEmployee
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import reactor.kotlin.test.test

@DbIntegrationTest
class EmployeeRepositoryIT {

    @Autowired
    private lateinit var mongoTemplate: ReactiveMongoTemplate

    @Autowired
    private lateinit var employeeRepository: EmployeeRepository

    @Test
    fun `should create employee`() {
        // GIVEN
        val employeeName = "create-employee-name-${System.nanoTime()}"
        val employeeToCreate = Employee(employeeName, 100.0, Department.HR)

        // WHEN // THEN
        employeeRepository.create(employeeToCreate)
            .test()
            .expectNext(Unit)
            .verifyComplete()

        // AND THEN
        val actualEmployeeFromDb = mongoTemplate.findOne(
            Criteria.where(MongoEmployee::name.name).`is`(employeeName).let(Query::query),
            MongoEmployee::class.java
        ).block()!!
        Assertions.assertEquals(employeeName, actualEmployeeFromDb.name)
    }

    @Test
    fun `should find employee by name when employee exists with provided name`() {
        // GIVEN
        val expectedEmployeeName = "find-employee-name-${System.nanoTime()}"
        mongoTemplate.save(MongoEmployee(expectedEmployeeName)).block()

        // WHEN // THEN
        employeeRepository.findByName(expectedEmployeeName)
            .test()
            .assertNext { actualEmployee ->
                Assertions.assertEquals(expectedEmployeeName, actualEmployee.name)
            }
            .verifyComplete()
    }

    @Test
    fun `should return all employees when multiple employees exist with the same name`() {
        // GIVEN
        val expectedEmployeeName = "multiple-employees-name-${System.nanoTime()}"
        val employeesWithSameName = 7
        repeat(employeesWithSameName) {
            mongoTemplate.save(MongoEmployee(expectedEmployeeName)).block()
        }

        // WHEN // THEN
        employeeRepository.findByName(expectedEmployeeName)
            .test()
            .expectNextCount(employeesWithSameName.toLong())
            .verifyComplete()
    }

    @Test
    fun `should return empty flux when no employees found by name`() {
        // WHEN // THEN
        employeeRepository.findByName("unknown-${System.nanoTime()}")
            .test()
            .verifyComplete()
    }
}
