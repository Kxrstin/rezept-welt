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

import rezepte.website.rezept_website.service.helper.Speisen;

@Service
public class FilterRecipeService {
    final private Speisen speisen;

    @Autowired
    public FilterRecipeService(RezeptRepository repo) {
        this.speisen = new Speisen(repo);
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
        String[] zutaten = filter.split("[^a-zA-ZäöüÄÖÜ0-9]+");

        return rezepte.stream()
                .filter(rezept -> {
                    String alleZutaten = rezept.getZutaten().toLowerCase();
                    return Arrays.stream(zutaten)
                            .allMatch(zutat -> alleZutaten.contains(zutat.toLowerCase()));
                })
                .toList();
    }

}
