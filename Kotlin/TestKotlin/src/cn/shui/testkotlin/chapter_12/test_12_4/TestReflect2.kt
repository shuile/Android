package cn.shui.testkotlin.chapter_12.test_12_4

import kotlin.reflect.KTypeParameter

open class BaseContainer<T>

class Container<T : Comparable<T>> : BaseContainer<Int> {
    var elements: MutableList<T>

    constructor(elements: MutableList<T>) {
        this.elements = elements
    }

    fun sort(): Container<T> {
        elements.sort()
        return this
    }

    override fun toString(): String {
        return "Container(elements=$elements)"
    }
}

fun main(args: Array<String>) {
    val container = Container(mutableListOf<Int>(1, 3, 2, 5, 4, 7, 6))
    val kClass = container::class // 获取KClass对象
    val typeParameters = kClass.typeParameters // 获取类型参数typeParameters信息，也即泛型信息
    val kTypeParameter: KTypeParameter = typeParameters[0]
    println(kTypeParameter.isReified)
    println(kTypeParameter.name)
    println(kTypeParameter.upperBounds)
    println(kTypeParameter.variance)
    val constructors = kClass.constructors
    for (KFunction in constructors) {
        KFunction.parameters.forEach {
            val name = it.name
            val type = it.type
            println("name = $name")
            println("type = $type")
            for (KTypeProjection in type.arguments) {
                println(KTypeProjection.type)
            }
        }
    }
}