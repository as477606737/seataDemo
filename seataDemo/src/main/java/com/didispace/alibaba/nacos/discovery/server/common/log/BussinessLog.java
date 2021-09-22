package com.didispace.alibaba.nacos.discovery.server.common.log;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface BussinessLog {
    /*
       业务名称，例如"添加数据"
     */
    String value() default "";

    /*
     被修改记录的唯一标识
     */
    String key() default "id";

    String desc() default "";

}
