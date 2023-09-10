package com.yxlh.androidxy

import org.junit.Test

class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
    }

    class ListNode(var value: Int) {
        var next: ListNode? = null
    }

    fun hasCycle(head: ListNode?): Boolean {
        if (head == null) {
            return false
        }
        var fast = head
        var slow = head
        while (fast?.next != null) {
            fast = fast.next?.next
            slow = slow?.next
            if (fast == slow) {
                return true
            }
        }
        return false

    }
}