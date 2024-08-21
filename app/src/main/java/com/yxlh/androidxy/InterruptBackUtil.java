package com.yxlh.androidxy;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.junit.After;

@Aspect
public class InterruptBackUtil {
    @Around("execution(* android.app.Activity+.finish(..))")
    public void aroundFinish(ProceedingJoinPoint joinPoint) {
        Log.e("InterruptBackUtil","aroundFinish------");
    }

    @Before("execution(* android.app.Activity+.onCreate(..))")
    public void beforeCreate(JoinPoint joinPoint) {
        Log.d("InterruptBackUtil", "activity create before");
    }

}
