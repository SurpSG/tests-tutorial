package com.example.demo.elementary.tests

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class MathUtilsTest {

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
        assertThat(actual).isEqualTo(expected)
    }
}
