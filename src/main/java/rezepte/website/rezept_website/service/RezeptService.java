package rezepte.website.rezept_website.service;

import org.springframework.stereotype.Service;
import rezepte.website.rezept_website.controller.formulare.Kategorie;
import rezepte.website.rezept_website.controller.formulare.RezeptForm;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


@Service
public class RezeptService {
    final private List<RezeptForm> rezepte = new LinkedList<>();

    public void addRezept(RezeptForm rezept) {
        rezepte.add(rezept);
    }

    public List<RezeptForm> getVorspeisen() {
        return getSpeise(Kategorie.VORSPEISE);
    }

    public List<RezeptForm> getHauptspeisen() {
        return getSpeise(Kategorie.HAUPTSPEISE);
    }

    public List<RezeptForm> getNachspeisen() {
        return getSpeise(Kategorie.NACHSPEISE);
    }

    private List<RezeptForm> getSpeise(Kategorie k) {
        return rezepte.stream()
                .filter(a -> a.getKategorie() == k)
                .sorted(Comparator.comparing(RezeptForm::getName))
                .toList();
    }

    public boolean removeRezept(RezeptForm rezept) {
        return rezepte.remove(rezept);
    }
}
