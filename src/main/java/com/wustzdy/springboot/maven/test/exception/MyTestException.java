package com.wustzdy.springboot.maven.test.exception;

public class MyTestException {
    public static void main(String[] args) {
        try {
            int a[] = new int[5];
//            a[0] = 3;
//            a[1] = 1;
            a[1] = 0;//除数为0异常
            a[10] = 7;//数组下标越界异常
            int result = a[0] / a[1];
            System.out.println(result);

        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("数组越界异常");
            ex.printStackTrace();//显示异常的堆栈跟踪信息

        } catch (ArithmeticException ex) {
            System.out.println("算术运算异常");
            ex.printStackTrace();

        } finally {
            System.out.println("finally语句不论是否有异常都会被执行。");

        }
        System.out.println("异常处理结束！");

        Integer n1 = new Integer(47);
        Integer n2 = new Integer(47);
        System.out.print(n1 == n2);
        System.out.print(",");
        System.out.println(n1 != n2);
    }
}
