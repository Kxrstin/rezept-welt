package rezepte.website.rezept_website.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;
import java.util.Arrays;

public class ValidImageValidator implements ConstraintValidator<ValidImage, MultipartFile> {

    private long maxSize;
    private String[] allowedTypes;

    @Override
    public void initialize(ValidImage constraintAnnotation) {
        this.maxSize = constraintAnnotation.maxSize();
        this.allowedTypes = constraintAnnotation.allowedTypes();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true; // @NotNull übernimmt die Prüfung auf Vorhandensein
        }

        if (file.getSize() > maxSize) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Das Bild ist zu groß (maximal "
                            + (maxSize / 1024 / 1024) + " MB)")
                    .addConstraintViolation();
            return false;
        }

        String contentType = file.getContentType();
        if (!isSupportedContentType(contentType)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Nur JPEG oder PNG Bilder sind erlaubt")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }

    private boolean isSupportedContentType(String contentType) {
        return Arrays.asList(allowedTypes).contains(contentType);
    }
}