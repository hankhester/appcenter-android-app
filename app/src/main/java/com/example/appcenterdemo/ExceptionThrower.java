package com.example.appcenterdemo;

public class ExceptionThrower {
    static void throwException() {
//        RuntimeException innerException = new RuntimeException("this is an inner exception");
//        RuntimeException outerException = new RuntimeException("this is an outer exception", innerException);
//        throw outerException;
        throw new RuntimeException("exception!");
    }
}
