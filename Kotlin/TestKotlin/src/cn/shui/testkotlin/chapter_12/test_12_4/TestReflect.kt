package cn.shui.testkotlin.chapter_12.test_12_4

import java.lang.reflect.ParameterizedType

class A<T>
open class C<T>
class B<T> : C<Int>() // 继承父类C<Int>()

fun fooA() {
    // 无法在此处获得运行时T的具体类型！下面的代码运行时会报错
    val parameterizedType = A<Int>()::class.java.genericSuperclass as ParameterizedType
    val actualTypeArguments = parameterizedType.actualTypeArguments
    for (type in actualTypeArguments) {
        val typeName = type.typeName
        println("typeName=$typeName") // 运行时报错：java.lang.Class cannot be cast to java.lang.reflect.ParameterizedType
    }
}

fun fooB() {
    // 当继承了父类C<Int>的时候，在次数能够获得运行时genericSuperclass T的具体类型
    val parameterizedType = B<Int>()::class.java.genericSuperclass as ParameterizedType
    val actualTypeArguments = parameterizedType.actualTypeArguments
    for (type in actualTypeArguments) {
        val typeName = type.typeName
        println("type = $typeName") // 输出：typeName = java.lang.Integer
    }
}

fun main(args: Array<String>) {
//    fooA()
    fooB()
}