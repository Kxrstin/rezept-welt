package rezepte.website.rezept_website.service.management;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.TestPropertySource;
import rezepte.website.rezept_website.controller.formulare.RezeptForm;
import rezepte.website.rezept_website.persistence.RezeptRepository;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static rezepte.website.rezept_website.object_mother.TestRezeptData.vorspeise;

@DataJdbcTest
@TestPropertySource(properties = {
        "spring.flyway.enabled=true",
        "spring.flyway.locations=classpath:db/migration"
})
public class AddRecipeServiceTest {
    final private AddRecipeService service;
    final private SpeisenPagesService speisenService;

    @Autowired
    public AddRecipeServiceTest(RezeptRepository repo) {
        service = new AddRecipeService(repo);
        speisenService = new SpeisenPagesService(repo);
    }

    @Test
    @DisplayName("RezeptService fügt Rezepte erfolgreich hinzu")
    void test_add_rezept() throws IOException {
        service.addRezept(vorspeise());

        Optional<RezeptForm> rezept = speisenService.getVorspeisen().stream().findFirst();

        assertThat(rezept.get().getName()).isEqualTo("Caesar Salad");
    }
}
