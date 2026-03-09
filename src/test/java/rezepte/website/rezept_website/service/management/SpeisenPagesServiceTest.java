package rezepte.website.rezept_website.service.management;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.TestPropertySource;
import rezepte.website.rezept_website.controller.formulare.RezeptForm;
import rezepte.website.rezept_website.persistence.RezeptRepository;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static rezepte.website.rezept_website.object_mother.TestRezeptData.*;
import static rezepte.website.rezept_website.service.helper.ContainsRecipe.containsRecipe;

@DataJdbcTest
@TestPropertySource(properties = {
        "spring.flyway.enabled=true",
        "spring.flyway.locations=classpath:db/migration"
})
public class SpeisenPagesServiceTest {
    final private AddRecipeService service;
    final private SpeisenPagesService speisenService;
    final private AddRecipeService addRecipeService;

    @Autowired
    public SpeisenPagesServiceTest(RezeptRepository repo) {
        service = new AddRecipeService(repo);
        speisenService = new SpeisenPagesService(repo);
        addRecipeService = new AddRecipeService(repo);
    }

    @Test
    @DisplayName("getVorspeisen gibt alle Vorspeisen zurück")
    void test_get_vorspeisen() throws IOException {
        List<RezeptForm> rezepte = vorspeisen();
        for(int i = 0; i < 3; i++)
            service.addRezept(rezepte.get(i));

        List<RezeptForm> vorspeisen = speisenService.getVorspeisen();

        List<String> recipeNames = List.of(
                "Caesar Salad",
                "Bruschetta Test",
                "Linsensalat mit Petersilie"
        );

        assertThat(containsRecipe(vorspeisen, recipeNames)).isEqualTo(3);
    }

    @Test
    @DisplayName("getHauptspeisen gibt alle Hauptspeisen zurück")
    void test_get_hauptspeisen() throws IOException {
        List<RezeptForm> rezepte = hauptspeisen();
        for(int i = 0; i < 3; i++)
            service.addRezept(rezepte.get(i));

        List<RezeptForm> hauptspeisen = speisenService.getHauptspeisen();

        List<String> recipeNames = List.of(
                "Chili con Carne klassisch",
                "Hähnchen-Geschnetzeltes in Rahmsauce",
                "Ofengemüse mediterran"
        );
        for(RezeptForm rezept: hauptspeisen) {
            System.out.println(rezept.getName());
        }
        assertThat(containsRecipe(hauptspeisen, recipeNames)).isEqualTo(3);
    }

    @Test
    @DisplayName("getNachspeisen gibt alle Nachspeisen zurück")
    void test_get_nachspeisen() throws IOException {
        List<RezeptForm> rezepte = nachspeisen();
        for(int i = 0; i < 3; i++)
            addRecipeService.addRezept(rezepte.get(i));

        List<RezeptForm> nachspeisen = speisenService.getNachspeisen();

        List<String> recipeNames = List.of(
                "Schoko-Bananen-Pfannkuchen",
                "Zitronen-Joghurt-Creme",
                "Vanille-Milchreis mit Zimt"
        );
        assertThat(containsRecipe(nachspeisen, recipeNames)).isEqualTo(3);
    }

}
