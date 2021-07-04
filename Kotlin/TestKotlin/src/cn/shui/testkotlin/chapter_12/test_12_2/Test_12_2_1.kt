package cn.shui.testkotlin.chapter_12.test_12_2

//@Target(
//    AnnotationTarget.CLASS,
//    AnnotationTarget.FUNCTION,
//    AnnotationTarget.VALUE_PARAMETER,
//    AnnotationTarget.EXPRESSION
//)
//@Retention(AnnotationRetention.SOURCE)
//@Repeatable
//@MustBeDocumented
//annotation class TestCase(val id: String)

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.EXPRESSION
)
@Retention(AnnotationRetention.SOURCE)
@Repeatable
@MustBeDocumented
annotation class Run