package com.example.demo.elementary.tests.badtest

class ArrayListTest : AbstractListTest() {
    override fun listProvider(vararg items: Int): List<Int> = ArrayList(items.toList())
}
