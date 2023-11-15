package com.tests.demo.employee.intellij

fun main() {
    allocations()
    heavyCpu()
}

fun heavyCpu() {
    val start = System.currentTimeMillis()
    var res = 0L
    for (i in 0L..0x3_FFFF_FFFF) {
        res = res xor i
    }
    println("Took ${System.currentTimeMillis() - start} ms. Res = $res")
}


private fun allocations() {
    `allocate 200 Mb`()
    `allocate 2 Mb`()
}


fun `allocate 2 Mb`() {
    val size = 2 * 1_000_000
    val array = ByteArray(size)
    println("Allocated $size Mb: $array")
}

fun `allocate 200 Mb`() {
    val size = 200 * 1_000_000
    val array = ByteArray(size)
    println("Allocated $size Mb: $array")
}
