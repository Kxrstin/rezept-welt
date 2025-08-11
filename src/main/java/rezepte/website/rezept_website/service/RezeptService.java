package rezepte.website.rezept_website.service;

import org.springframework.stereotype.Service;
import rezepte.website.rezept_website.controller.formulare.Kategorie;
import rezepte.website.rezept_website.controller.formulare.RezeptForm;

import java.util.LinkedList;
import java.util.List;


@Service
public class RezeptService {
    final private List<RezeptForm> rezepte = new LinkedList<>();

    public void addRezept(RezeptForm rezept) {
        rezepte.add(rezept);
    }

    public List<RezeptForm> getVorspeisen() {
        return rezepte.stream()
                .filter(a -> a.getKategorie() == Kategorie.VORSPEISE)
                .toList();
    }

    public List<RezeptForm> getHauptspeisen() {
        return rezepte.stream()
                .filter(a -> a.getKategorie() == Kategorie.HAUPTSPEISE)
                .toList();
    }

    public List<RezeptForm> getNachspeisen() {
        return rezepte.stream()
                .filter(a -> a.getKategorie() == Kategorie.NACHSPEISE)
                .toList();
    }

    public boolean removeRezept(RezeptForm rezept) {
        return rezepte.remove(rezept);
    }
}
