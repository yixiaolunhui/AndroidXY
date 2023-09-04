package com.yxlh.androidxy;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Test {


    /**
     * 一亿数据去最大10个数
     */
    @org.junit.Test
    public void minNum() {

        int[] numbers = new int[100000000];
        for (int i = 0; i < 100000000; i++) {
            numbers[i] = i + 1;
        }

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int num : numbers) {
            if (minHeap.size() < 10) {
                minHeap.offer(num);
            } else if (num > minHeap.peek()) {
                minHeap.poll();
                minHeap.offer(num);
            }
        }

        int[] largest10 = new int[10];
        for (int i = 9; i >= 0; i--) {
            largest10[i] = minHeap.poll();
            System.out.println(largest10[i]);
        }



    }


    /**
     * 大位数相乘
     */
    @org.junit.Test
    public void dwsxc() {
        String a = "123456789";
        String b = "987654321";
        BigInteger bigA = new BigInteger(a);
        BigInteger bigB = new BigInteger(b);
        System.out.println(bigA.multiply(bigB));
        List<Integer> temp = new ArrayList<Integer>();
    }

    /**
     * 三等份
     */
    @org.junit.Test
    public void main() {
        int[] A = {0, 9, 1, 2, 0, 1, 2, 1, -6, 6, -7};
        System.out.println(canThreePartsEqualSum(A)); // Output: true
    }


    public boolean canThreePartsEqualSum(int[] A) {
        int totalSum = 0;
        for (int num : A) {
            totalSum += num;
        }
        if (totalSum % 3 != 0) {
            return false;
        }
        int targetSum = totalSum / 3;
        int partSum = 0; // 当前部分的累加和
        int count = 0; // 已找到的符合条件的部分数量

        for (int num : A) {
            partSum += num;
            if (partSum == targetSum) {
                partSum = 0;
                count++;
            }
        }
        return count >= 3;
    }

    @org.junit.Test
    public void strConvert(){
        List<String> result=Permutation("abc");
        for(String s:result){
            System.out.println(s);
        }
    }
    public ArrayList<String> Permutation(String str) {
        ArrayList<String> result = new ArrayList<>();
        char[] chars = str.toCharArray();
        getResult(chars, 0, result);
        return result;
    }
    private void getResult(char[] chars, int start, ArrayList<String> result) {
        if (start == chars.length - 1) {
            String s = new String(chars);
            if (!result.contains(s)) {
                result.add(s);
            }
            return;
        }
        for (int i = start; i < chars.length; i++) {
            swap(chars, start, i);
            getResult(chars, start + 1, result);
            swap(chars, start, i);
        }
    }
    private void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }

}
