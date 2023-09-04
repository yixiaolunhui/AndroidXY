package com.yxlh.androidxy;

import org.junit.Test;

/**
 * @author zwl
 */
public class XYTest {

    class Three {
        int val;
        Three left;
        Three right;

        public Three(int val) {
            this.val = val;
        }

        public Three(int val, Three left, Three right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     *         1
     *     2      3
     * 4     5   6   7
     */
    @Test
    public void test() {
        Three root = new Three(1, new Three(2, new Three(4), new Three(5)), new Three(3, new Three(6), new Three(7)));
        Three result = getResult(root, new Three(4), new Three(6));
        if (result != null) {
            System.out.println(result.val);
        } else {
            System.out.println("没找到");
        }

    }

    private Three getResult(Three root, Three num1, Three num2) {
        if (root == null) {
            return null;
        }
        if (root.val == num1.val || root.val == num2.val) {
            return root;
        }

        Three left = getResult(root.left, num1, num2);
        Three right = getResult(root.right, num1, num2);

        if (left != null && right != null) {
            return root;
        } else if (left != null) {
            return left;
        } else if (right != null) {
            return right;
        } else {
            return null;
        }
    }


}
