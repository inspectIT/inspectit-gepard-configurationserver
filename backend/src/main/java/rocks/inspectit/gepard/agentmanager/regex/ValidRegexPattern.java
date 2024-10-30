/* (C) 2024 */
package rocks.inspectit.gepard.agentmanager.regex;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RegexPatternValidator.class)
public @interface ValidRegexPattern {
  String message() default "Invalid regex pattern";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}