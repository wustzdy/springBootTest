package com.example.springboot.demo.springboottest.temp;

public class TotalTest {
    public static void main(String[] args) {
        int total = 0;
        for (int i = 0; i <= 1000; i++) {
            total = total + i;
        }
        System.out.println("1加到baidu1000的和为total:" + total);
    }
}
