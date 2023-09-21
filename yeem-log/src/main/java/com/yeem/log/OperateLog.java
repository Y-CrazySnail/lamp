package com.yeem.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperateLog {
    /**
     * 操作模块
     * @return ""
     */
    String operateModule() default "";
    /**
     * 操作类型
     * @return ""
     */
    String operateType() default "";
    /**
     * 操作说明
     * @return ""
     */
    String operateDesc() default "";
}