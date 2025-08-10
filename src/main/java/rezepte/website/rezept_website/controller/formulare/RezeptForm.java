package rezepte.website.rezept_website.controller.formulare;

public class RezeptForm {
    private Kategorie kategorie;

    private String name;
    private String zutaten;
    private String zubereitung;

    private byte[] bild;

    public RezeptForm() {}

    public RezeptForm(Kategorie kategorie,
                      String name,
                      String zutaten,
                      String zubereitung) {

        this.kategorie = kategorie;
        this.name = name;
        this.zutaten = zutaten;
        this.zubereitung = zubereitung;
    }

    public RezeptForm(Kategorie kategorie,
                  String name,
                  String zutaten,
                  String zubereitung,
                  byte[] bild) {

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

    public byte[] getBild() {
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

    public void setBild(byte[] bild) {
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

    public String getBildAlsBase64() {
        if (bild != null && bild.length > 0) {
            return java.util.Base64.getEncoder().encodeToString(bild);
        }
        return null;
    }
}
