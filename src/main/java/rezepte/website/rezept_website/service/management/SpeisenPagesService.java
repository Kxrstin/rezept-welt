package rezepte.website.rezept_website.service.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rezepte.website.rezept_website.controller.formulare.Kategorie;
import rezepte.website.rezept_website.controller.formulare.RezeptForm;
import rezepte.website.rezept_website.persistence.RezeptRepository;
import rezepte.website.rezept_website.service.helper.Speisen;

import java.util.List;

@Service
public class SpeisenPagesService {
    final private Speisen speisen;

    @Autowired
    public SpeisenPagesService(RezeptRepository repo) {
        this.speisen = new Speisen(repo);
    }


    public List<RezeptForm> getVorspeisen() {
        return speisen.getSpeise(Kategorie.Vorspeise);
    }

    public List<RezeptForm> getHauptspeisen() {
        return speisen.getSpeise(Kategorie.Hauptspeise);
    }

    public List<RezeptForm> getNachspeisen() {
        return speisen.getSpeise(Kategorie.Nachspeise);
    }
}
