package com.example.springboot.demo.springboottest.exception;

public class MyException extends RuntimeException {

    public MyException(String ErrorExceptin) {    //构造方法
        super(ErrorExceptin);        //父类构造方法
    }

}
