package com.lfd.soa.common.orm.annontation;

import java.lang.annotation.*;

/**
 * remark as ID
 * @author linfengda
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Id {
	
}
