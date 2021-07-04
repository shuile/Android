package cn.shui.testkotlin.chapter_12.test_12_3

var one = 1
fun testReflectProperty() {
    println(::one.get())
    ::one.set(2)
    println(one)
}

fun main(args: Array<String>) {
    testReflectProperty()
}