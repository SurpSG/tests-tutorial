package com.example.demo.elementary.tests.badtest

import com.example.demo.elementary.tests.RoundStrategy
import com.example.demo.elementary.tests.divide
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MultiActionTest {

    @Test
    fun `should return correct result`() {
        Assertions.assertEquals(divide(1, 2, RoundStrategy.DOWN), 0)
        Assertions.assertEquals(divide(2, 2, RoundStrategy.DOWN), 1)
        Assertions.assertEquals(divide(4, 2, RoundStrategy.DOWN), 2)
        Assertions.assertEquals(divide(6, 2, RoundStrategy.DOWN), 3)
        Assertions.assertEquals(divide(8, 2, RoundStrategy.DOWN), 4)
        Assertions.assertEquals(divide(10, 2, RoundStrategy.DOWN), 5)
        Assertions.assertEquals(divide(12, 2, RoundStrategy.DOWN), 6)
        Assertions.assertEquals(divide(14, 2, RoundStrategy.DOWN), 7)
        Assertions.assertEquals(divide(16, 2, RoundStrategy.DOWN), 8)
    }
}
