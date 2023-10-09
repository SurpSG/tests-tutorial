package com.tests.demo.employee.controller.mapper

import com.tests.demo.employee.controller.dto.EmployeeDto
import com.tests.demo.employee.domain.Department
import com.tests.demo.employee.domain.Employee

object EmployeeMapper {

    fun EmployeeDto.toDomain() = Employee(
        name = name,
        salary = salary,
        department = department.asDepartment(),
    )

    fun String.asDepartment(): Department = when (this) {
        "HR" -> Department.HR
        "IT" -> Department.IT
        else -> throw IllegalArgumentException("Invalid department: $this")
    }
}
