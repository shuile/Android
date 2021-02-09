# 《Kotlin从入门到进阶实战》

## 第一章 Kotlin是什么

### 1.1 初识Kotlin

Kotlin发展简史如下：

* 2011年7月，JetBrains推出Kotlin项目。
* 2012年2月，JetBrains以Apache 2许可证开源此项目。
* 2016年2月15日，Kotlin v1.0（第一个官方稳定版本）发布。
* 2017年Google I/O大会上，Kotlin“转正”。

Kotlin具备类型推断、多范式支持、可空性表达、扩展函数、模式匹配等诸多下一代编程语言特性。  
Kotlin的编译器kompiler可以被独立出来并嵌入到Maven、Ant或Gradle工具链中。
与C、C++、Java语言一样，Kotlin程序的入口点是一个名为main()的函数，它传递一个包含任何命令行参数的数组。代码示例如下：

```Kotlin
package com.easy.kotlin             // (1)
fun main(args: Array<String>) {     // (2)
    val name = "World"
    println("Hello, $name!")        // (3)
}
```

代码说明如下。  
(1)：Koltin中包package的使用与Java基本相同。有一点不同的是Kotlin的package命名可以与包路径不同。  
(2)：Kotlin变量声明args:Array类似于Pascal，先写变量名args，冒号隔开，再在后面写变量的类型Array。与Scala和Groovy一样，代码行末的分号是可选的，在大多数情况下，编译器根据换行符就能够推断语句已经结束。Kotlin中使用fun关键字声明函数（方法），充满乐趣的fun。  
(3)：Kotlin中的打印函数时println()（虽然背后封装的仍然是Java的System.out.println()方法）。Kotlin中支持字符串模板$name，如果是表达式，则使用${expression}语法。

### 1.2 语言特性

Kotlin语言的特性可以简单概括为以下几个方面。

1. 实用主义（Pragmatic）
    务实、注重工程实践性。Kotlin是一门偏重工程实践、编程上有极简风格的语言。
2. 极简主义（Minimalist）
    Kotlin语法简介优雅不啰嗦，类型系统中一切皆是引用（reference）。
3. 空安全（Null Safety）
    Kotlin中有一个完备的类型系统来支持空安全。
4. 多范式（multi-paradigm）
    Kotlin同时支持OOP与FP编程范式。
5. 可扩展
    Kotlin可直接扩展类的函数与属性（extension functions & properties）。
6. 高阶函数与闭包（higher-order functions & closures）
    Kotlin的类型中，函数类型（function type）也是一等类型（first class type）。在Kotlin中可以把函数当成值进行传递，这直接赋予了Kotlin函数式编程的特性。
7. 支持快速实现DSL
   有了扩展函数、闭包等特性的支持，使用Kotlin实现一个DSL将会想当简单、方便。

#### 1.2.1 Kotlin与Java完全互操作

Kotlin是基于JVM平台的静态编程语言，同时在设计之初就把与Java的互操作性当作中套目标。代码示例如下：

```Kotlin
fun getArrayList(): List<String> {          // (1)函数声明
    val arrayList = ArrayList<String>()     // (2)Kotlin中直接调用Java的API
    arrayList.add("A");
    arrayList.add("B");
    arrayList.add("C");
    return arrayList
}
```

代码说明如下。  
(1)：声明了一个返回List<String>的函数。  
(2)：创建了一个ArrayList<String>对象。  
下面使用JUnit框架进行单元测试。

```Kotlin
package com.easy.kotlin                     // (3)包声明

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runner.JUnit4              // (4)导入JUnit的类

@RunWith(JUnit4::class)       // (5)直接使用Java生态库JUnit中的注解@RunWith
class FullJavaInteroperabilityTest {
    @Test                                   // (6)标记这是一个测试方法
    fun test() {
        val list = getArrayList()           // (7)调用被测试函数
        Assert.assertTrue(list.size == 3)   // (8)断言
    }
}
```

代码说明如下。  
(3)：是包声明，使用package关键字。  
(4)：使用import带入JUnit4类。  
(5)：Kotlin中使用@RunWith注解，方法与Java语法类似。注解中的参数是JUnit4::class，是JUnit4类的引用。
(6)：使用JUnit的@Test注解来标注这是一个测试方法。
(7)：调用被测函数getArrayList()。
(8)：使用JUnit的Assert类的API进行断言操作。

### 1.2.2 扩展函数与扩展属性

扩展函数与扩展属性的“好玩”之处在于，可以在不修改原来类的条件下自定义函数和属性，使它们表现得就像是属于这个类一样。例如，我们给String类型扩展一个返回字符串首字母的firstChar()函数，代码如下：

``` Kotlin
fun String.firstChar(): String {    // 给String类扩展一个firstChar()函数
    if (this.length == 0) {         // 这里的this代表调用者对象
        return ""
    }
    return this[0].toString()       // 返回下标为0的字符并转成String类型
}
```

然后就可以在代码中直接调用该函数：

```Kotlin
"abc".firstChar()       // 调用我们自定义的扩展函数
```

### 1.2.3 不可空类型与空安全
