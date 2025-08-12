package rezepte.website.rezept_website.controller.formulare;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

public class RezeptForm {
    @NotNull(message="Bitte wählen Sie eine Kategorie")
    private Kategorie kategorie;

    @NotBlank(message="Bitte nennen Sie einen Namen für das Gericht")
    private String name;
    @NotBlank(message="Bitte wählen Sie eine Kategorie")
    private String zutaten;
    @NotBlank(message="Bitte wählen Sie eine Kategorie")
    private String zubereitung;

    @NotNull(message="Bitte wählen Sie ein Bild")
    private MultipartFile bild;

    private String base64Bild;

    public RezeptForm() {}

    public RezeptForm(Kategorie kategorie,
                  String name,
                  String zutaten,
                  String zubereitung,
                  MultipartFile bild) throws IOException {

        this.kategorie = kategorie;
        this.name = name;
        this.zutaten = zutaten;
        this.zubereitung = zubereitung;
        this.bild = bild;
        base64Bild = base64Bild(bild);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getBild() {
        return bild;
    }

    private String base64Bild(MultipartFile file) throws IOException {
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

    public Kategorie getKategorie() {
        return kategorie;
    }

    public String getZubereitung() {
        return zubereitung;
    }

    public String getZutaten() {
        return zutaten;
    }

    public void setBild(MultipartFile bild) throws IOException {
        this.bild = bild;
        base64Bild = base64Bild(bild);
    }

    public void setKategorie(Kategorie kategorie) {
        this.kategorie = kategorie;
    }

    public void setZubereitung(String zubereitung) {
        this.zubereitung = zubereitung;
    }

    public void setZutaten(String zutaten) {
        this.zutaten = zutaten;
    }
}
