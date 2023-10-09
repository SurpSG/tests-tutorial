package com.tests.demo.employee.repository.model

import org.springframework.data.mongodb.core.mapping.Document

@Document("users")
data class MongoEmployee(
    val name: String = "",
    val salary: Double = 0.0,
    val department: MongoDepartment? = null,
) {
    lateinit var id: String
}

enum class MongoDepartment {
    HR,
    IT,
}
