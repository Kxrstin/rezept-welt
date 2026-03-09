package rezepte.website.rezept_website.service.selection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.TestPropertySource;
import rezepte.website.rezept_website.controller.formulare.RezeptForm;
import rezepte.website.rezept_website.persistence.RezeptRepository;
import rezepte.website.rezept_website.service.management.AddRecipeService;
import rezepte.website.rezept_website.service.management.AddRecipeServiceTest;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static rezepte.website.rezept_website.object_mother.TestRezeptData.nachspeisen;
import static rezepte.website.rezept_website.service.helper.ContainsRecipe.containsRecipe;

@DataJdbcTest
@TestPropertySource(properties = {
        "spring.flyway.enabled=true",
        "spring.flyway.locations=classpath:db/migration"
})
public class FilterRecipeServiceTest {
    final private FilterRecipeService service;
    final private AddRecipeService addRecipeService;

    @Autowired
    public FilterRecipeServiceTest(RezeptRepository repo) {
        service = new FilterRecipeService(repo);
        addRecipeService = new AddRecipeService(repo);
    }



    @Test
    @DisplayName("Filtert die Rezeptnamen korrekt")
    void test_filter_name() throws IOException {
        List<RezeptForm> rezepte = nachspeisen();
        for(int i = 0; i < 3; i++)
            addRecipeService.addRezept(rezepte.get(i));

        List<RezeptForm> nachspeisen = service.getFilteredNachspeisen("Zitronen-Joghurt-Creme");

        List<String> recipeName = List.of(
                "Zitronen-Joghurt-Creme"
        );
        assertThat(containsRecipe(nachspeisen, recipeName)).isEqualTo(1);
    }

    @Test
    @DisplayName("Gibt alle Rezepte mit den gefilterten Zutaten aus")
    void test_filter_zutaten() throws IOException {
        List<RezeptForm> rezepte = nachspeisen();
        for(int i = 0; i < 3; i++)
            addRecipeService.addRezept(rezepte.get(i));

        List<RezeptForm> nachspeisen = service.getFilteredNachspeisen("Zucker Milch");

        List<String> recipeName = List.of(
                "Zitronen-Joghurt-Creme",
                "Vanille-Milchreis mit Zimt"
        );
        assertThat(containsRecipe(nachspeisen, recipeName)).isEqualTo(2);
        assertThat(containsRecipe(nachspeisen, List.of("Schoko-Bananen-Pfannkuchen")))
                .isEqualTo(0);
    }


}
