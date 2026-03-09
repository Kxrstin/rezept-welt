package rezepte.website.rezept_website.service.helper;

import rezepte.website.rezept_website.controller.formulare.RezeptForm;

import java.util.List;

public class ContainsRecipe {
    public static long containsRecipe(List<RezeptForm> rezepte, List<String> recipe) {
        long counter = 0;
        for(String recipeName: recipe) {
            counter += rezepte.stream()
                    .filter(r -> r.getName().equals(recipeName))
                    .count();
        }
        return counter;
    }
}
