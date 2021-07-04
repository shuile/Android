package cn.shui.testkotlin.chapter_12.test_12_2

import java.util.*
import kotlin.reflect.full.declaredFunctions

fun testAnnoProcessing() {
    val sword = SwordTest3()
//    val kClass: KClass<out SwordTest> = sword::class // 类型声明可省略
    val kClass = sword::class

    val declaredFunctions = kClass.declaredFunctions // 获取sword对象类型所声明的所有函数

    println(declaredFunctions)

    for (f in declaredFunctions) {
        // 处理TestCase注解，使用其中的元数据
        f.annotations.forEach {
            if (it is TestCase) {
                val id = it.id
                doSomething(id) // 注解处理逻辑
                f.call(sword, id) // 等价于 f.javaMethod?.invoke(sword, id)
            }
        }
    }
}

private fun doSomething(id: String) {
    println("Do Something in Annotation Processing ${id} ${Date()}")
}

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.VALUE_PARAMETER
)
@Retention(AnnotationRetention.RUNTIME)
@Repeatable
@MustBeDocumented
annotation class TestCase(val id: String)

class SwordTest3 {
    @TestCase(id = "1")
    fun testCase(testId: String) {
        println("Run SwordTest ID = ${testId}")
    }
}

fun main(args: Array<String>) {
    testAnnoProcessing()
}