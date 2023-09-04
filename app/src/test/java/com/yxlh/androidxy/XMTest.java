package com.yxlh.androidxy;

import org.junit.Test;

import java.io.File;
import java.util.Stack;

/**
 * @author zwl
 * @date on 2023/8/18
 */
public class XMTest {

    class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

    }


    @Test
    public void test() {


        ListNode result = merge(new ListNode(1, new ListNode(3)), new ListNode(2, new ListNode(4)));

        while (result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }
    }


    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * @param pHead1 ListNode类
     * @param pHead2 ListNode类
     * @return ListNode类
     */
    public ListNode merge(ListNode pHead1, ListNode pHead2) {
        ListNode result = new ListNode(-1);
        ListNode curr = result;
        while (pHead1 != null || pHead2 != null) {
            if (pHead1 == null) {
                curr.next = pHead2;
                pHead2 = pHead2.next;
            } else if (pHead2 == null) {
                curr.next = pHead1;
                pHead1 = pHead1.next;
            } else if (pHead1.val <= pHead2.val) {
                curr.next = pHead1;
                pHead1 = pHead1.next;
            } else {
                curr.next = pHead2;
                pHead2 = pHead2.next;
            }
            curr = curr.next;
        }
        return result.next;
    }


    private void deleteFile(String filePath) {
        if (filePath.isEmpty() || !new File(filePath).exists()) {
            return;
        }

        File file = new File(filePath);
        Stack<File> stack = new Stack();
        stack.push(file);
        while (!stack.isEmpty()) {
            File f = stack.pop();
            if (f.isDirectory()) {
                File[] files = f.listFiles();
                if (files != null && files.length > 0) {
                    for (File file1 : files) {
                        stack.push(file1);
                    }
                }

                f.delete();
            } else {
                f.delete();
            }
        }

    }


}
