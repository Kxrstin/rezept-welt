package rezepte.website.rezept_website.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidImageValidator.class)
@Documented
public @interface ValidImage {
    String message() default "Ungültiges Bild";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    long maxSize() default 2 * 1024 * 1024;
    String[] allowedTypes() default {"image/jpeg", "image/jpg", "image/png"};
}