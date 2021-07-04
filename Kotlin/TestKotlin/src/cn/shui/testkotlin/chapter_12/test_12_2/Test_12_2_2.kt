package cn.shui.testkotlin.chapter_12.test_12_2

@Run
class SwordTest1 {}

@Run
class SwordTest2 {
    @TestCase(id = "1")
    fun testCase(testId: String) {
        println("Run SwordTest2 ID = $testId")
    }
}