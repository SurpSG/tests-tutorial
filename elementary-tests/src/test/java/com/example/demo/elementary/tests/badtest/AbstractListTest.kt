package com.example.demo.elementary.tests.badtest

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

abstract class AbstractListTest {

    abstract fun listProvider(vararg items: Int): List<Int>

    @Test
    fun testList() {
        val list = listProvider(1, 2, 3)

        Assertions.assertEquals(list.size, 3)
        Assertions.assertIterableEquals(list, listOf(1, 2, 3))
    }
}
