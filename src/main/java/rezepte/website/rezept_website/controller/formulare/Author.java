package rezepte.website.rezept_website.controller.formulare;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.web.multipart.MultipartFile;
import rezepte.website.rezept_website.validation.ValidImage;

import java.io.IOException;
import java.util.Base64;

public class Author {
    @Id
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

    public Author(String u,
                  String d,
                  MultipartFile multipartFile) {
        username = u;
        description = d;
        try {
            if (multipartFile != null && !multipartFile.isEmpty()) {
                base64Bild = base64Bild(multipartFile);
                this.profilePicture = multipartFile.getBytes();
            } else {
                this.profilePicture = null;
                this.base64Bild = null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PersistenceCreator
    public Author(
            String username,
            String description,
            byte[] profilePicture
    ) {
        this.username = username;
        this.description = description;
        setProfilePicture(profilePicture);
    }

    public String getUsername() {
        return username;
    }

    public MultipartFile getBildMultiPart() {
        return bildMultiPart;
    }

    public String base64Bild(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }
        String mimeType = file.getContentType();
        byte[] bytes = file.getBytes();
        return "data:" + mimeType + ";base64," + Base64.getEncoder().encodeToString(bytes);
    }

    public String getBase64Bild() {
        return base64Bild;
    }

    public void setBase64Bild(String base64Bild) {
        this.base64Bild = base64Bild;
    }

    public void setProfilePictureMultiPart(MultipartFile bildMultiPart) throws IOException {
        this.bildMultiPart = bildMultiPart;
        base64Bild = base64Bild(bildMultiPart);
        profilePicture = bildMultiPart.getBytes();
    }

    public void setProfilePicture(byte[] bild) {
        this.profilePicture = bild;

        if (bild != null && bild.length > 0) {
            String encoded = Base64.getEncoder().encodeToString(bild);
            this.base64Bild = "data:;base64," + encoded;
        } else {
            this.base64Bild = null;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setBildMultiPart(MultipartFile bildMultiPart) {
        this.bildMultiPart = bildMultiPart;
    }

    public void setDescription(@NotBlank String description) {
        this.description = description;
    }

    public void setUsername(@NotBlank String username) {
        this.username = username;
    }

    public Object getProfilePicture() {
        return profilePicture;
    }
}
