package com.tests.demo.employee.repository

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@ActiveProfiles("it")
@EnableAutoConfiguration
@SpringBootTest
@ContextConfiguration(
    classes = [
        MongoRepositoriesAutoConfiguration::class,
        DbIntegrationTest.DbIntegrationTestComponentScan::class,
    ]
)
annotation class DbIntegrationTest {

    @ComponentScan(
        value = [
            "com.tests.demo.employee.repository"
        ]
    )
    class DbIntegrationTestComponentScan
}
