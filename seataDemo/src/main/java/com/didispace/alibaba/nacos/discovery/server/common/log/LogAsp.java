package com.didispace.alibaba.nacos.discovery.server.common.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class LogAsp {

    @Pointcut(value = "@annotation(com.didispace.alibaba.nacos.discovery.server.common.log.BussinessLog)")
    public void cutService(){

    }

    @Around("cutService()")
    public Object recordsysLog(ProceedingJoinPoint point) throws Throwable {
        //先执行业务操作
      Object res = point.proceed();
      try{
       handle(point);
      }catch (Exception e){
          log.error("日志记录出错！", e);
      }
      return res ;
    }

    public void handle(ProceedingJoinPoint point) throws  Exception{
        //拦截方法名
        Signature  signature = point.getSignature();
        MethodSignature sig = null ;
        if (!(signature instanceof  MethodSignature)){
            throw new IllegalAccessException("该注解只能用于方法");
        }
        sig = (MethodSignature) signature;
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(sig.getName(),sig.getParameterTypes());
        String methodName = currentMethod.getName(); //方法名

        //用户信息

        //获取方法参数
        String className = point.getTarget().getClass().getName();
        Object[] params =point.getArgs();

        //获取操作名称
        BussinessLog bussinessLog = currentMethod.getAnnotation(BussinessLog.class);
        String value = bussinessLog.value();
        String key = bussinessLog.key();
        String desc =bussinessLog.desc();

        StringBuilder sb = new StringBuilder();
        for (Object param : params){
            sb.append(param);
            sb.append(" & ");
        }

        //写入日志操作
        System.out.println(methodName + "/" + className + "/" + value + "/" + key  + "/" + sb );


    }


}
