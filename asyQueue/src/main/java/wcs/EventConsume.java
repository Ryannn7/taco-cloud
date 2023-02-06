package wcs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liuzongshuai
 * @date 2022/12/22 10:23
 * 定义消费者注释*
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventConsume {

    /**
     * 事件标识
     */
    String identifier() default "default";
}
