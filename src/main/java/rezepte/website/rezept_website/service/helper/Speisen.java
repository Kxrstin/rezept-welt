package rezepte.website.rezept_website.service.helper;

import rezepte.website.rezept_website.controller.formulare.Kategorie;
import rezepte.website.rezept_website.controller.formulare.RezeptForm;
import rezepte.website.rezept_website.persistence.RezeptRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Speisen {
    final private RezeptRepository repo;

    public Speisen(RezeptRepository repo) {
        this.repo = repo;
    }

    public List<RezeptForm> getSpeise(Kategorie kategorie) {
        List<RezeptForm> rezepte = repo.findAll().stream()
                .filter(a -> a.getKategorie() == kategorie)
                .sorted(Comparator.comparing(RezeptForm::getName))
                .toList();
        return rezepte.isEmpty() ? Collections.emptyList() : rezepte;
    }
}
