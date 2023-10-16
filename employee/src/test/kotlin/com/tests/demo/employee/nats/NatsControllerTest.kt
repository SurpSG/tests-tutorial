package com.tests.demo.employee.nats

import com.tests.demo.employee.config.InfrastructureConfig
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@ActiveProfiles("it")
@SpringBootTest
@ContextConfiguration(
    classes = [
        NatsControllerTest.ControllerComponentScan::class,
    ]
)
annotation class NatsControllerTest {

    @ComponentScan(
        value = [
            "com.tests.demo.employee.nats"
        ],
        basePackageClasses = [InfrastructureConfig::class]
    )
    class ControllerComponentScan
}
