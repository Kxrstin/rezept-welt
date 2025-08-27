package rezepte.website.rezept_website.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.mock.web.MockMultipartFile;
import rezepte.website.rezept_website.controller.formulare.Kategorie;
import rezepte.website.rezept_website.controller.formulare.RezeptForm;
import rezepte.website.rezept_website.persistence.RezeptRepository;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
public class RezeptServiceTest {

    final private RezeptService service;

    @Autowired
    public RezeptServiceTest(RezeptRepository repo) {
        service = new RezeptService(repo);
    }

    final private MockMultipartFile file =
            new MockMultipartFile("bildMultiPart", "demo.txt", "text/plain", "Hello World".getBytes());
    final private RezeptForm salat_rezept = new RezeptForm(Kategorie.VORSPEISE, "Caesar Salat", "Salz, Gurke, Tomate, ...", "Zunächst ...", file);
    final private RezeptForm burger_rezept = new RezeptForm(Kategorie.HAUPTSPEISE, "Spicy Burger", "Patties, Buns, Tomate, ...", "Erstmal ...", file);
    final private RezeptForm torte_rezept = new RezeptForm(Kategorie.NACHSPEISE, "Erdbeertorte", "Erdbeeren, Mehl, Zucker, ...", "Zuallererst ...", file);

    @Test
    @DisplayName("RezeptService fügt Rezepte erfolgreich hinzu")
    void test_add_rezept() {
        salat_rezept.setId(null);
        service.addRezept(salat_rezept);

        Optional<RezeptForm> rezept = service.getVorspeisen().stream().findFirst();

        assertThat(rezept.get().getName()).isEqualTo("Caesar Salat");
        assertThat(service.getVorspeisen().size()).isEqualTo(1);
        assertThat(service.getNachspeisen().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("getVorspeisen gibt alle Vorspeisen zurück")
    void test_get_vorspeisen() {
        salat_rezept.setId(null);
        burger_rezept.setId(null);
        torte_rezept.setId(null);

        service.addRezept(salat_rezept);
        service.addRezept(burger_rezept);
        service.addRezept(torte_rezept);


        Optional<RezeptForm> vorspeise = service.getVorspeisen().stream().findFirst();


        assertThat(vorspeise.get().getName()).isEqualTo("Caesar Salat");
        assertThat(service.getVorspeisen().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("getHauptspeisen gibt alle Hauptspeisen zurück")
    void test_get_hauptspeisen() {
        salat_rezept.setId(null);
        burger_rezept.setId(null);
        torte_rezept.setId(null);

        service.addRezept(salat_rezept);
        service.addRezept(burger_rezept);
        service.addRezept(torte_rezept);


        Optional<RezeptForm> hauptspeise = service.getHauptspeisen().stream().findFirst();


        assertThat(hauptspeise.get().getName()).isEqualTo("Spicy Burger");
        assertThat(service.getHauptspeisen().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("getNachspeisen gibt alle Nachspeisen zurück")
    void test_get_nachspeisen() {
        salat_rezept.setId(null);
        burger_rezept.setId(null);
        torte_rezept.setId(null);

        service.addRezept(salat_rezept);
        service.addRezept(burger_rezept);
        service.addRezept(torte_rezept);


        Optional<RezeptForm> nachspeise = service.getNachspeisen().stream().findFirst();


        assertThat(nachspeise.get().getName()).isEqualTo("Erdbeertorte");
        assertThat(service.getNachspeisen().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("removeRezept löscht Speise korrekt")
    void test_remove() {
        salat_rezept.setId(null);
        RezeptForm r = service.addRezept(salat_rezept);

        service.removeRezept(r.getId());
        assertThat(service.getVorspeisen().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("edit ändert das Rezept wie gewünscht")
    void test_edit() throws IOException {
        salat_rezept.setId(null);
        RezeptForm r = service.addRezept(salat_rezept);
        r.setName("Bla");

        service.edit(r.getId(), r);

        assertThat(service.getZubereitung(r.getId()).getName()).isEqualTo("Bla");
    }
}
