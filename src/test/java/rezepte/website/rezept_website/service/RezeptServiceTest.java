package rezepte.website.rezept_website.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.TestPropertySource;
import rezepte.website.rezept_website.controller.formulare.RezeptForm;
import rezepte.website.rezept_website.persistence.RezeptRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static rezepte.website.rezept_website.object_mother.TestRezeptData.*;

@DataJdbcTest
@TestPropertySource(properties = {
        "spring.flyway.enabled=true",
        "spring.flyway.locations=classpath:db/migration"
})
public class RezeptServiceTest {

    final private RezeptService service;

    @Autowired
    public RezeptServiceTest(RezeptRepository repo) {
        service = new RezeptService(repo);
    }

    @Test
    @DisplayName("RezeptService fügt Rezepte erfolgreich hinzu")
    void test_add_rezept() {
        service.addRezept(vorspeise());

        Optional<RezeptForm> rezept = service.getVorspeisen().stream().findFirst();

        assertThat(rezept.get().getName()).isEqualTo("Caesar Salad");
    }

    @Test
    @DisplayName("getVorspeisen gibt alle Vorspeisen zurück")
    void test_get_vorspeisen() {
        List<RezeptForm> rezepte = vorspeisen();
        for(int i = 0; i < 3; i++)
            service.addRezept(rezepte.get(i));

        List<RezeptForm> vorspeisen = service.getVorspeisen();

        List<String> recipeNames = List.of(
                "Caesar Salad",
                "Bruschetta Test",
                "Linsensalat mit Petersilie"
        );

        assertThat(containsRecipe(vorspeisen, recipeNames)).isEqualTo(3);
    }

    @Test
    @DisplayName("getHauptspeisen gibt alle Hauptspeisen zurück")
    void test_get_hauptspeisen() {
        List<RezeptForm> rezepte = hauptspeisen();
        for(int i = 0; i < 3; i++)
            service.addRezept(rezepte.get(i));

        List<RezeptForm> hauptspeisen = service.getHauptspeisen();

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
    void test_get_nachspeisen() {
        List<RezeptForm> rezepte = nachspeisen();
        for(int i = 0; i < 3; i++)
            service.addRezept(rezepte.get(i));

        List<RezeptForm> nachspeisen = service.getNachspeisen();

        List<String> recipeNames = List.of(
                "Schoko-Bananen-Pfannkuchen",
                "Zitronen-Joghurt-Creme",
                "Vanille-Milchreis mit Zimt"
        );
        assertThat(containsRecipe(nachspeisen, recipeNames)).isEqualTo(3);
    }

    @Test
    @DisplayName("removeRezept löscht Speise korrekt")
    void test_remove() {
        RezeptForm r = service.addRezept(vorspeise());

        service.removeRezept(r.getId());
        assertThat(containsRecipe(service.getVorspeisen(), List.of("Caesar Salad"))).isEqualTo(0);
    }

    @Test
    @DisplayName("edit ändert das Rezept wie gewünscht")
    void test_edit() throws IOException {
        RezeptForm r = service.addRezept(vorspeise());
        r.setName("Caesar Salat");
        r.setZutaten("Römersalat, 100g Parmesan, Croutons, 2 Eier, Senf, Zitronensaft, Öl");
        r.setZubereitung("Salat waschen und klein schneiden. Aus Eiern, Senf und Zitronensaft ...");
        r.setBildMultiPart(demoFile2());

        service.edit(r.getId(), r);

        RezeptForm rezeptNew = service.getZubereitung(r.getId());
        assertThat(rezeptNew.getName()).isEqualTo("Caesar Salat");
        assertThat(rezeptNew.getZutaten())
                .isEqualTo("Römersalat, 100g Parmesan, Croutons, 2 Eier, Senf, Zitronensaft, Öl");
        assertThat(rezeptNew.getZubereitung())
                .isEqualTo("Salat waschen und klein schneiden. Aus Eiern, Senf und Zitronensaft ...");
        assertThat(rezeptNew.getBild()).isEqualTo(demoFile2().getBytes());
    }


    long containsRecipe(List<RezeptForm> rezepte, List<String> recipe) {
        long counter = 0;
        for(String recipeName: recipe) {
            counter += rezepte.stream()
                    .filter(r -> r.getName().equals(recipeName))
                    .count();
            System.out.println(counter + " " + recipeName);
        }
        return counter;
    }
}
