package com.tests.demo.employee.controller

import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@ActiveProfiles("it")
@WebFluxTest
@ContextConfiguration(
    classes = [
        RestControllerTest.ControllerComponentScan::class,
    ]
)
annotation class RestControllerTest {

    @ComponentScan(
        value = [
            "com.tests.demo.employee.controller"
        ]
    )
    class ControllerComponentScan
}
