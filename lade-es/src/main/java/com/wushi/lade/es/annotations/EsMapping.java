package com.wushi.lade.es.annotations;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EsMapping {

    String mappingPath() default "";

}
