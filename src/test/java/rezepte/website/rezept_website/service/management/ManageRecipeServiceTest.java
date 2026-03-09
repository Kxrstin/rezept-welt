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
import static rezepte.website.rezept_website.object_mother.TestRezeptData.demoFile2;
import static rezepte.website.rezept_website.service.helper.ContainsRecipe.containsRecipe;

@DataJdbcTest
@TestPropertySource(properties = {
        "spring.flyway.enabled=true",
        "spring.flyway.locations=classpath:db/migration"
})
public class ManageRecipeServiceTest {
    final private ManageRecipeService service;
    final private AddRecipeService addRecipeService;
    final private SpeisenPagesService speisenPagesService;

    @Autowired
    public ManageRecipeServiceTest(RezeptRepository repo) {
        service = new ManageRecipeService(repo);
        addRecipeService = new AddRecipeService(repo);
        speisenPagesService = new SpeisenPagesService(repo);
    }


    @Test
    @DisplayName("removeRezept löscht Speise korrekt")
    void test_remove() throws IOException {
        RezeptForm r = addRecipeService.addRezept(vorspeise());

        service.removeRezept(r.getId());
        assertThat(containsRecipe(speisenPagesService.getVorspeisen(), List.of("Caesar Salad"))).isEqualTo(0);
    }

    @Test
    @DisplayName("edit ändert das Rezept wie gewünscht")
    void test_edit() throws IOException {
        RezeptForm r = addRecipeService.addRezept(vorspeise());
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
}
