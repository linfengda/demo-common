package com.lfd.soa.common.orm.annontation;

import java.lang.annotation.*;

/**
 * remark as table on entity
 * @author linfengda
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {

	String name() default "";
}
