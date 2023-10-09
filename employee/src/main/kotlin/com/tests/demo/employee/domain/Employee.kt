package com.tests.demo.employee.domain

data class Employee(
    val name: String,
    val salary: Double,
    val department: Department?,
)

enum class Department {
    HR,
    IT,
}
