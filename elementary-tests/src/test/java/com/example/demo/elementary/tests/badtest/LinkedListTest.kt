package com.example.demo.elementary.tests.badtest

import java.util.LinkedList

class LinkedListTest : AbstractListTest() {
    override fun listProvider(vararg items: Int): List<Int> = LinkedList(items.toList())
}
