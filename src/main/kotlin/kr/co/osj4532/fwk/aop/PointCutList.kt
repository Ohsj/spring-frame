package kr.co.osj4532.fwk.aop

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut

/**
 * 201205 | osj4532 | created
 */

@Aspect
class PointCutList {
    // -----------------------------------------------------------------------------------------------------------------
    // Controller
    // -----------------------------------------------------------------------------------------------------------------
    @Pointcut("allController()")
    fun pointController(){
    }

    @Pointcut("execution(* kr.co.osj4532..controller..*.*(..))")
    fun allController(){
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Service
    // -----------------------------------------------------------------------------------------------------------------
    @Pointcut("allService()")
    fun pointService(){
    }

    @Pointcut("execution(* kr.co.osj4532..service..*.*(..))")
    fun allService(){
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Repository
    // -----------------------------------------------------------------------------------------------------------------
    @Pointcut("allMapper() && allRepo()")
    fun pointRepository(){
    }

    @Pointcut("execution(* kr.co.osj4532..mybatis..*.*(..))")
    fun allMapper(){
    }

    @Pointcut("execution(* kr.co.osj4532..jpa..*.*(..))")
    fun allRepo(){
    }
}