package rezepte.website.rezept_website.controller.formulare;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

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

    public RezeptForm() {}

    public RezeptForm(Kategorie kategorie,
                  String name,
                  String zutaten,
                  String zubereitung,
                  MultipartFile bild) {

        this.kategorie = kategorie;
        this.name = name;
        this.zutaten = zutaten;
        this.zubereitung = zubereitung;
        this.bild = bild;
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

    public Kategorie getKategorie() {
        return kategorie;
    }

    public String getZubereitung() {
        return zubereitung;
    }

    public String getZutaten() {
        return zutaten;
    }

    public void setBild(MultipartFile bild) {
        this.bild = bild;
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
