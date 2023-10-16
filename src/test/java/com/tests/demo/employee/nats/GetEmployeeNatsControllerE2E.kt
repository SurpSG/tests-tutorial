package com.tests.demo.employee.nats

import com.tests.demo.employee.repository.model.MongoEmployee
import io.nats.client.Connection
import io.nats.client.Message
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.test.context.ActiveProfiles
import java.time.Duration
import java.util.concurrent.CompletableFuture

@ActiveProfiles("e2e")
@SpringBootTest
class GetEmployeeNatsControllerE2E {

    @Autowired
    private lateinit var connection: Connection

    @Autowired
    private lateinit var mongoTemplate: ReactiveMongoTemplate

    @Test
    fun `should return success result`() {
        // GIVEN
        val allowedName = "E2E test allowed name"
        mongoTemplate.save(MongoEmployee(allowedName, 0.0, null)).block()

        // WHEN
        val actualResponse: CompletableFuture<Message> = connection.request(
            GetEmployeeNatsController.SUBJECT,
            allowedName.toByteArray(),
        )

        // THEN
        Assertions.assertThat(actualResponse)
            .succeedsWithin(Duration.ofSeconds(10))
            .extracting { it.data.decodeToString() }
            .isEqualTo("Success:\n$allowedName")
    }
}
