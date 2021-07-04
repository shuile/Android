package cn.shui.testkotlin.chapter_12.test_12_2

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AnnotationClassNoteTest {
    @Test
    fun testAnno() {
        val sword = SwordTest2()
        sword.testCase("10000")
    }
}