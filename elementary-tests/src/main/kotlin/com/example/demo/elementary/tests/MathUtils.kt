package com.example.demo.elementary.tests

import kotlin.reflect.full.functions

fun divide(
    a: Int,
    b: Int,
    roundStrategy: RoundStrategy = RoundStrategy.DOWN,
): Int {
    require(a > 0 && b > 0)
    return when (roundStrategy) {
        RoundStrategy.DOWN -> a / b
        RoundStrategy.UP -> (a - 1) / b + 1
    }
}

enum class RoundStrategy {
    UP,
    DOWN,
}

fun main() {
    val testSuite = TestSuite()
    testSuite::class.functions.asSequence()
        .filter { it.name.startsWith("test ") }
        .forEach {
            println(it.name)
            it.call(testSuite)
        }
}

class TestSuite {

    fun `test divide 3 by 2 should return 1`() {
        val actual: Int = divide(3, 2)

        assert(actual == 2) { "Expected 1, but got $actual" }
    }

    fun `test divide 8 by 2 should return 4`() {
        val actual: Int = divide(8, 2)

        assert(actual == 4) { "Expected 4, but got $actual" }
    }
}

