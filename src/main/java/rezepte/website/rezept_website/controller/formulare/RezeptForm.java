package rezepte.website.rezept_website.controller.formulare;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

public class RezeptForm {

    @Id
    private Integer id;

    @NotNull(message="Bitte wählen Sie eine Kategorie")
    private Kategorie kategorie;

    @NotBlank(message="Bitte nennen Sie einen Namen für das Gericht")
    private String name;
    @NotBlank(message="Bitte wählen Sie eine Kategorie")
    private String zutaten;
    @NotBlank(message="Bitte wählen Sie eine Kategorie")
    private String zubereitung;

    @NotNull(message="Bitte wählen Sie ein Bild")
    @Transient
    private MultipartFile bildMultiPart;

    private byte[] bild;

    @Transient
    private String base64Bild;

    public RezeptForm() {}

    public RezeptForm(Kategorie kategorie,
                  String name,
                  String zutaten,
                  String zubereitung,
                  MultipartFile multipartFile) {
        this.kategorie = kategorie;
        this.name = name;
        this.zutaten = zutaten;
        this.zubereitung = zubereitung;
        this.bildMultiPart = multipartFile;
        try {
            base64Bild = base64Bild(multipartFile);
            this.bild = multipartFile.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PersistenceCreator
    public RezeptForm(Integer id, Kategorie kategorie,
                      String name,
                      String zutaten,
                      String zubereitung,
                      byte[] bild) {
        this.id = id;
        this.kategorie = kategorie;
        this.name = name;
        this.zutaten = zutaten;
        this.zubereitung = zubereitung;
        setBild(bild);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getBildMultiPart() {
        return bildMultiPart;
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

    public void setBildMultiPart(MultipartFile bildMultiPart) throws IOException {
        this.bildMultiPart = bildMultiPart;
        base64Bild = base64Bild(bildMultiPart);
        bild = bildMultiPart.getBytes();
    }

    public void setBild(byte[] bild) {
        this.bild = bild;

        if (bild != null && bild.length > 0) {
            String encoded = Base64.getEncoder().encodeToString(bild);
            this.base64Bild = "data:;base64," + encoded;
        } else {
            this.base64Bild = null;
        }
    }


    public byte[] getBild() {
        return bild;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String[] getZutatenListe() {
        return this.zutaten.split(", ");
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o.getClass() != RezeptForm.class) return false;
        return ((RezeptForm) o).getName().equals(this.getName());
    }
}
