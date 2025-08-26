package rezepte.website.rezept_website.service;

import org.springframework.stereotype.Service;
import rezepte.website.rezept_website.controller.formulare.Kategorie;
import rezepte.website.rezept_website.controller.formulare.RezeptForm;

import java.io.IOException;
import java.util.*;


@Service
public class RezeptService {
    final private List<RezeptForm> rezepte = new LinkedList<>();
    private int current_id = 0;

    public void addRezept(RezeptForm rezept) {
        rezept.setId(current_id);
        current_id++;
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
        List<RezeptForm> r = rezepte.stream()
                .filter(a -> a.getKategorie() == k)
                .sorted(Comparator.comparing(RezeptForm::getName))
                .toList();
        return r.isEmpty() ? Collections.emptyList() : r;
    }

    private List<RezeptForm> getFilteredSpeise(Kategorie k, String filter) {
        List<RezeptForm> r = getSpeise(k).stream()
                .filter(s -> s.getName().toLowerCase().contains(filter.toLowerCase()))
                .toList();

        return r.isEmpty() ? Collections.emptyList() : r;
    }

    public void removeRezept(int id) {
        Optional<RezeptForm> r = rezepte.stream().filter(e -> e.getId() == id).findFirst();
        r.ifPresent(rezepte::remove);
    }

    public RezeptForm getZubereitung(int id) {
        Optional<RezeptForm> opt = rezepte.stream().filter(e -> e.getId() == id).findFirst();
        return opt.orElse(null);
    }

    public void edit(int id, RezeptForm rezeptForm) throws IOException {
        RezeptForm old = getZubereitung(id);
        if(old == null) return;
        old.setName(rezeptForm.getName());
        old.setKategorie(rezeptForm.getKategorie());
        old.setName(rezeptForm.getName());
        old.setZubereitung(rezeptForm.getZubereitung());
        old.setZutaten(rezeptForm.getZutaten());
        old.setBild(rezeptForm.getBild());
    }

    public List<RezeptForm> getFilteredVorspeisen(String filter) {
        return getFilteredSpeise(Kategorie.VORSPEISE, filter);
    }

    public List<RezeptForm> getFilteredHauptspeisen(String filter) {
        return getFilteredSpeise(Kategorie.HAUPTSPEISE, filter);
    }

    public List<RezeptForm> getFilteredNachspeisen(String filter) {
        return getFilteredSpeise(Kategorie.NACHSPEISE, filter);
    }
}
