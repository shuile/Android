# Chapter 12 元编程、注解与反射

反射(Reflection)是在运行时获取类的函数(方法)、属性、父类、接口、注解元数据、泛型信息等类的内部信息的机制。  
注解(Annotation)是我们给代码添加的元数据。

## 12.1 元编程简介

元编程(Meta-programming)是指用代码在编译期或运行期生成或改变代码的一种编程形式。  
元编程通常有两种方式：一种是通过应用程序接口（API）来暴露运行时系统的内部信息；另一种方法是在运行时动态执行包含编程命令的字符串。  
注解是把编程中的元数据直接写在源代码中，而不是保存在外部文件中。  
注解是将元数据附加到代码的方法。而反射可以在运行时把代码中的注解元数据获取到，并在目标代码执行之前进行动态代理，实现业务逻辑的动态注入，这其实就是AOP（Aspect Oriented Programming，面向切面编程（也叫面向方面））的核心思想--通过运行期动态代理（和预编译方式）实现在不修改源代码的情况下，给程序动态添加新功能的一种技术。  

## 12.2 注解

### 12.2.1 声明注解

元注解说明：  
@Target：指定这个注解可被用于哪些元素（这些元素定义在kotlin.annotation.AnnotationTarget枚举类中。）它们是：类CLASS、注解类ANNOTATION_CLASS、泛型参数TYPE_PARAMETER、函数FUNCTION、属性PROPERTY，用于描述域成员变量的FIELD、局部变量LOCAL_VARIABLE、VALUE_PARAMETER、CONSTRUCTOR、PROPERTY_GETTER、PROPERTY_SETTER，以及用于描述类、接口（包括注解类型）或enum声明的TYPE、表达式EXPRESSION、文件FILE、类型别名TYPEALIAS等  
@Retention：指定这个注解的信息是否被保存到编辑后的class文件中，以及在运行时是否可以通过反射访问它。可取的枚举值有3个，分别是：SOURCE（注解数据不存在二进制输出）、BINARY（注解数据存储在二进制输出中，但反射不可见）、RUNTIME（注解数据存储在二进制数据中，可用于反射（默认值））  
@Repeatable：允许在单个元素上多次使用同一个注解  
@MustBeDocumented：表示这个注解是公开API的一部分，在自动产生的API文档的类或者函数签名中，应该包含这个注解的信息

### 12.2.2 使用注解

注解可以有带参数的构造器。注解参数可支持的数据类型如下：
基于数据类型（Int、Float、Boolean、Byte、Double、Char、Long、Short）
String类型
KClass类型
enum类型
Annotation类型

### 12.2.3 处理注解

注解信息的获取主要是使用反射API。目标测试类是：
```kotlin
@Run
class SwordTest {
    @TestCase(id = "1")
    fun testCase(testId : String) {
        println("Run SwordTest ID = ${testId}")
    }
}
```
这里我们主要介绍@TestCase注解作用在函数上的处理过程
1. ::class -> 通过这个变量获取对象的类的信息。
```kotlin
val sword = SwordTest()
val kClass1 = sword::class
val kClass2 : KCLass<out SwordTest> = sword::class
```
2. declaredFunctions扩展属性 -> 获取类中声明的所有函数（对应的反射主句类型是KFunction）
```kotlin
val declaredFunctions = kClass.declaredFunctions
```
3. annotations属性
KFunction类型继承了KCallable，KCallable又继承了KAnnotatedElement。KAnnotatedElement中有个public val annotations:List属性里存储了该函数所有的注解信息。通过遍历这个存储Annotation的List，可以获取到TestCase注解。
```kotlin
for (f in declaredFunctions) {
    // 处理TestCase注解没使用其中的元数据
    f.annotations.forEach {
        if (it is TestCase) {
            val id = it.id      // TestCase注解的属性ID
            doSomething(id)     // 注解处理逻辑
        }
    }
}
```   
4. call函数
通过反射来调用函数，可以直接使用call()函数
```kotlin
f.call(sword, id)
        ||
f.javaMethod?.invoke(sword, id)
```
## 12.3 反射

反射是指在运行时(Run time)，程序可以访问、检测和修改它本身状态或行为的一种能力.  
Kotlin中有两种方式来实现反射的功能：一种是调用Java的反射包java.lang.reflect下面的API，另一种是直接调用Kotlin提供的kotlin.reflect包下面的API。

### 12.3.1 类引用

```kotlin
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
```
反射是在运行时获取一个类引用。
```kotlin
val container = Container(mutableListOf<Int>(1, 3, 2, 5, 4, 7, 6))
val kClass = container::class // 获取KClass对象
```
需要注意的是，Kotlin中类引用和Java中的类引用是不同的，要获得Java类的引用，可以直接使用javaClass这个扩展属性
```kotlin
val jClass = container.javaClass // 获取Java Class对象
```
javaClass扩展属性在Kotlin中的实现源码如下：
```kotlin
public inline val <T: Any> T.javaClass : Class<T>
    @Suppress("UsePropertyAccessSyntax")
    get() = (this as java.lang.Object).getClass() as Class<T>
```
或者使用KClass实例的java属性
```kotlin
val jClass = kClass.java
```
KClass.java的扩展属性实现源码如下：
```kotlin
@Suppress("UPPER_BOUND_VIOLATED")
public val <T> KClass<T>.java: Class<T>
    @JvmName("getJavaClass")
    get() = (this as ClassBasedDeclarationContainer).jClass as Class<T>
```

### 12.3.2 函数引用
```kotlin
fun isOdd(x: Int) = x % 2 != 0
```
高阶函数中如果想把该函数当作一个参数来使用，可以使用"::"操作符。
```kotlin
val nums = listOf(1, 2, 3)
val filteredNums = nums.filter(::isOdd)
println(filteredNums)
```

### 12.3.3 属性引用
在Kotlin中，访问属性属于第一级对象，可以使用"::"操作符
```kotlin
var one = 1
fun testReflectProperty() {
    println(::one.get())
    ::one.set(2)
    println(one)
}

fun main()
```
表达式::one等价于类型为KProperty的一个属性，它可以允许我们通过get()函数获取值::gone.get()  
对于可变属性 var one = 1，返回类型为KMutableProperty的值，并且还有set方法::one.set(2)

### 12.3.4 绑定函数和属性引用
```kotlin
val digitRegex = "\\d+".toregex()
digitRegex.mathces("7")
digitRegex.mathces("6")
digitRegex.mathces("5")
digitRegex.mathces("X")
```
其中,digitRegex.matches重复出现，在Kotlin中可以直接引用digitRegex对象实例的matches()方法。
```kotlin
val isDigit = digitRegex::mathes
isDigit("7")
isDigit("6")
isDigit("5")
isDigit("X")
```

## 12.4 使用反射获取泛型信息

Java，使用反射的一个代码实例
```java
package com.shui.kotlin;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
interface StudentService<T> {
    List<T> findStudents(String name, Integer age);
}

abstract class BaseService<T> {
    abstract int save(T t);
}

class Student {
    Strng name;
    Integer age;
    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
}

class StudentServiceImpl extends BaseService<Student> implements StudentService<Student> {
    public List<Student> findStudents(String name, Integer age) {
        return Arrays.asList(new Student{}{new Student()
    }
}
public class ReflectDemo {
    public static void main(String[] args) {
        
    }
}
```