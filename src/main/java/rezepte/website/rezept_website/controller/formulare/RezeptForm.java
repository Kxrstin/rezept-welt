package rezepte.website.rezept_website.controller.formulare;

public class RezeptForm {
    private Kategorie kategorie;

    private UnterkategorieVor vor;
    private UnterkategorieHaupt haupt;
    private UnterkategorieNach nach;

    private String name;
    private String zutaten;
    private String zubereitung;

    private byte[] bild;

    public RezeptForm() {}

    public RezeptForm(Kategorie kategorie,
                  UnterkategorieVor vor,
                  UnterkategorieHaupt haupt,
                  UnterkategorieNach nach,
                  String name,
                  String zutaten,
                  String zubereitung,
                  byte[] bild) {

        this.kategorie = kategorie;
        if(kategorie == Kategorie.VORSPEISE) this.vor = vor;
        if(kategorie == Kategorie.VORSPEISE) this.haupt = haupt;
        if(kategorie == Kategorie.VORSPEISE) this.nach = nach;

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

    public UnterkategorieHaupt getHaupt() {
        return haupt;
    }

    public UnterkategorieNach getNach() {
        return nach;
    }

    public UnterkategorieVor getVor() {
        return vor;
    }

    public void setBild(byte[] bild) {
        this.bild = bild;
    }

    public void setHaupt(UnterkategorieHaupt haupt) {
        this.haupt = haupt;
    }

    public void setKategorie(Kategorie kategorie) {
        this.kategorie = kategorie;
    }

    public void setNach(UnterkategorieNach nach) {
        this.nach = nach;
    }

    public void setVor(UnterkategorieVor vor) {
        this.vor = vor;
    }

    public void setZubereitung(String zubereitung) {
        this.zubereitung = zubereitung;
    }

    public void setZutaten(String zutaten) {
        this.zutaten = zutaten;
    }
}
