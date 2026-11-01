package rezepte.website.rezept_website.controller.formulare;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Transient;
import org.springframework.web.multipart.MultipartFile;
import rezepte.website.rezept_website.validation.ValidImage;

public class Author {
    @NotBlank
    private String username;

    @NotBlank
    private String description;

    @ValidImage(maxSize = 3 * 1024 * 1024) // Limit: 3 MB
    @Transient
    private MultipartFile bildMultiPart;

    private byte[] profilePicture;

    @Transient
    private String base64Bild;

    public String getUsername() {
        return username;
    }
}
