package rezepte.website.rezept_website.service.selection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rezepte.website.rezept_website.controller.formulare.Kategorie;
import rezepte.website.rezept_website.controller.formulare.RezeptForm;
import rezepte.website.rezept_website.persistence.RezeptRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import rezepte.website.rezept_website.service.helper.Speisen;

@Service
public class FilterRecipeService {
    final private Speisen speisen;
    private final RezeptRepository rezeptRepository;

    @Autowired
    public FilterRecipeService(RezeptRepository repo, RezeptRepository rezeptRepository) {
        this.speisen = new Speisen(repo);
        this.rezeptRepository = rezeptRepository;
    }

    private List<RezeptForm> getFilteredSpeise(Kategorie k, String filter) {
        List<RezeptForm> filteredRecipes = new ArrayList<>();
        List<RezeptForm> allRecipes = speisen.getSpeise(k);

        filteredRecipes.addAll(filterRecipeName(allRecipes, filter));

        // Um Duplikate zu vermeiden
        filteredRecipes.addAll(
                filteredRecipeZutaten(allRecipes, filter).stream()
                        .filter(rezept -> !filteredRecipes.contains(rezept))
                        .toList());

        return filteredRecipes.isEmpty() ? Collections.emptyList() : filteredRecipes;
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

    private List<RezeptForm> filterRecipeName(List<RezeptForm> rezepte, String filter) {
        return rezepte.stream()
                .filter(s -> s.getName().toLowerCase().contains(filter.toLowerCase()))
                .toList();
    }

    private List<RezeptForm> filteredRecipeZutaten(List<RezeptForm> rezepte, String filter) {
        return rezepte.stream()
                .filter(r -> r.getZutaten().contains(filter))
                .toList();
    }

    public List<String> getFilteredSpeisenAPI(String query, String speise) {
        Kategorie kat = getKategorie(speise);
        if(kat == null) return List.of();

        List<RezeptForm> rezepte = rezeptRepository.findAll();

        List<String> filteredRezepte = new ArrayList<>(
                rezepte.stream()
                .filter(r -> r.getKategorie().equals(kat))
                .map(RezeptForm::getName)
                .filter(name -> name.contains(query))
                .toList());

        filteredRezepte.addAll(rezepte.stream()
                .filter(r -> r.getKategorie().equals(kat))
                .map(r -> r.getZutaten().split(", "))
                .flatMap(Arrays::stream)
                .filter(zutat -> zutat.contains(query))
                .toList());

        return filteredRezepte.stream()
                .distinct()
                .toList();
    }

    private Kategorie getKategorie(String kat) {
        return switch (kat) {
            case "VORSPEISE" -> Kategorie.Vorspeise;
            case "HAUPTSPEISE" -> Kategorie.Hauptspeise;
            case "NACHSPEISE" -> Kategorie.Nachspeise;
            default -> null;
        };
    }
}
