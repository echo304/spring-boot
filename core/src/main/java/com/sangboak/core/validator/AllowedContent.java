package com.sangboak.core.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StringContentValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowedContent {

    String message() default "Post content contains forbidden phrases";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

