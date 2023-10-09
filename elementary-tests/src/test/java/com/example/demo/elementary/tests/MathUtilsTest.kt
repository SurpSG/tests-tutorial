package com.example.demo.elementary.tests

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.Timeout
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread

@ExtendWith
class MathUtilsTest {

    @Test
    fun `logic in tests`(
    ) {
        val actual: Int = divide(1, 2, RoundStrategy.DOWN)

        val expected = 1 / 2
        Assertions.assertEquals(expected, actual)
    }

    @Disabled
    @Test
    fun `should 2`() {
        // GIVEN
        val lock = ReentrantLock()

        thread {
            lock.lock()
            Thread.sleep(4000)
            lock.unlock()
        }
        Thread.sleep(1000)

        lock.lock()
    }

//    @Disabled("reason here. when it well be fixed, linked ticket")
    @ParameterizedTest
    @CsvSource(
        "DOWN, 1, 2, 0",
        "DOWN, 3, 2, 1",
        "DOWN, 8, 2, 4",
        "DOWN, 123, 2, 61",

        "UP, 1, 2, 1",
        "UP, 3, 2, 2",
        "UP, 8, 2, 4",
        "UP, 123, 2, 62",
    )
    fun `should divide two numbers with round down when strategy is round down`(
        // GIVEN
        roundStrategy: RoundStrategy,
        a: Int,
        b: Int,
        expected: Int,
    ) {
        // WHEN
        val actual: Int = divide(a, b, roundStrategy)

        // THEN
        Assertions.assertEquals(expected, actual)
    }

//    @Disabled
    @ParameterizedTest
    @CsvSource(
        "0, 0",
        "0, 1",
        "1, 0",
        "1, -1",
        "-1, 1",
    )
    fun `should throw when arguments equal or less than zero`(
        // GIVEN
        a: Int,
        b: Int,
    ) {
        // WHEN // THEN
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            divide(a, b, RoundStrategy.DOWN)
        }
    }
}
