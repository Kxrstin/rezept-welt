package rezepte.website.rezept_website.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rezepte.website.rezept_website.controller.formulare.Kategorie;
import rezepte.website.rezept_website.controller.formulare.RezeptForm;
import rezepte.website.rezept_website.persistence.RezeptRepository;

import java.io.IOException;
import java.util.*;


@Service
public class RezeptService {
    final private RezeptRepository repo;

    @Autowired
    public RezeptService(RezeptRepository repo) {
        this.repo = repo;
    }

    public RezeptForm addRezept(RezeptForm rezept) {
        try {
            if (rezept.getBildMultiPart() != null && !rezept.getBildMultiPart().isEmpty()) {
                rezept.setBild(rezept.getBildMultiPart().getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException("Bild konnte nicht gespeichert werden", e);
        }
        rezept.setId(null);
        return repo.save(rezept);
    }


    public List<RezeptForm> getVorspeisen() {
        return getSpeise(Kategorie.Vorspeise);
    }

    public List<RezeptForm> getHauptspeisen() {
        return getSpeise(Kategorie.Hauptspeise);
    }

    public List<RezeptForm> getNachspeisen() {
        return getSpeise(Kategorie.Nachspeise);
    }

    private List<RezeptForm> getSpeise(Kategorie k) {
        List<RezeptForm> r = repo.findAll().stream()
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
        Optional<RezeptForm> r = repo.findById(id);
        r.ifPresent(repo::delete);
    }

    public RezeptForm getZubereitung(int id) {
        Optional<RezeptForm> opt = repo.findById(id);
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
        old.setBildMultiPart(rezeptForm.getBildMultiPart());
        old.setBild(rezeptForm.getBildMultiPart().getBytes());
        repo.save(old);
    }

    public List<RezeptForm> getFilteredVorspeisen(String filter) {
        return getFilteredSpeise(Kategorie.Vorspeise, filter);
    }

    public List<RezeptForm> getFilteredHauptspeisen(String filter) {
        return getFilteredSpeise(Kategorie.Hauptspeise, filter);
    }

    public List<RezeptForm> getFilteredNachspeisen(String filter) {
        return getFilteredSpeise(Kategorie.Nachspeise, filter);
    }
}
