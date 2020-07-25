package com.example.springboot.demo.springboottest.exception;

public class TestException {
    public static void main(String[] args) {
        try {                                    //try代码块处理可能出现的异常
            int result = add(-22, 23);                //调用Sum()方法
            System.out.println(result);            //将Sum()方法的返回值输出
        } catch (MyException e) {
            // TODO: handle exception
            System.out.println(e);                //输出异常信息
        }
    }

    private static int add(int num1, int num2) {
        if (num1 < 0 || num2 < 0) {                        //判断方法中参数是否满足条件
            throw new MyException("输入参数为负数"); //错误信息
        }
        if (num1 > 20 || num2 > 20) {                    //判断方法中参数是否满足条件
            throw new MyException("输入参数大于20");//错误信息
        }
        return (num1 + num2);                        //将两个数的和输出
    }
}
