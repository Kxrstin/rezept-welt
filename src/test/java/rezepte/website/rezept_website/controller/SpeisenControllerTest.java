package rezepte.website.rezept_website.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import rezepte.website.rezept_website.controller.formulare.Kategorie;
import rezepte.website.rezept_website.controller.formulare.RezeptForm;
import rezepte.website.rezept_website.service.RezeptService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class SpeisenControllerTest {
    @Autowired
    MockMvc mvc;

    @MockitoBean
    RezeptService service;

    final private MockMultipartFile file =
            new MockMultipartFile("bildMultiPart", "demo.txt", "text/plain", "Hello World".getBytes());
    final private RezeptForm demoRezept = new RezeptForm(Kategorie.VORSPEISE, "", "Salz, ...", "Zunächst ...", file);
    final private RezeptForm demoRezept2 = new RezeptForm(Kategorie.NACHSPEISE, "Dessert", "Zucker, ...", "Erstmal ...", file);


    @Test
    @DisplayName("Beim aufrufen des 'Vorspeise'-Links wird die Vorspeisenseite gezeigt")
    void test_vorspeise() throws Exception {
        demoRezept.setKategorie(Kategorie.VORSPEISE);
        demoRezept.setName("Gurkensalat");
        when(service.getVorspeisen()).thenReturn(List.of(demoRezept));

        String result = mvc.perform(get("/vorspeise"))
                .andExpect(status().isOk())
                .andExpect(view().name("speisen/vorspeise_page"))
                .andReturn()
                .getResponse().getContentAsString();

        assertThat(result).contains("Gurkensalat");
    }


    @Test
    @DisplayName("Beim aufrufen des Hauptspeise'-Links wird die Hauptspeisenseite gezeigt")
    void test_hauptspeise() throws Exception {
        demoRezept.setKategorie(Kategorie.HAUPTSPEISE);
        demoRezept.setName("Spagetti Bolognese");
        when(service.getHauptspeisen()).thenReturn(List.of(demoRezept));

        String result = mvc.perform(get("/hauptspeise"))
                .andExpect(status().isOk())
                .andExpect(view().name("speisen/hauptspeise_page"))
                .andReturn()
                .getResponse().getContentAsString();

        assertThat(result).contains("Spagetti Bolognese");
    }

    @Test
    @DisplayName("Beim aufrufen des 'Nachspeise'-Links wird die Nachspeisenseite gezeigt")
    void test_nachspeise() throws Exception {
        demoRezept.setKategorie(Kategorie.NACHSPEISE);
        demoRezept.setName("Eiscreme");
        when(service.getNachspeisen()).thenReturn(List.of(demoRezept));

        String result = mvc.perform(get("/nachspeise"))
                .andExpect(status().isOk())
                .andExpect(view().name("speisen/nachspeise_page"))
                .andReturn()
                .getResponse().getContentAsString();

        assertThat(result).contains("Eiscreme");
    }

    @Test
    @DisplayName("Beim aufrufen des 'Remove'-Links wird die Remove Page gezeigt")
    void test_remove_page() throws Exception {
        demoRezept.setKategorie(Kategorie.NACHSPEISE);
        demoRezept.setName("Eiscreme");
        demoRezept.setId(0);
        when(service.getZubereitung(0)).thenReturn(demoRezept);

        mvc.perform(get("/get/zubereitung/0/remove"))
                .andExpect(status().isOk())
                .andExpect(view().name("change/remove"));
    }

    @Test
    @DisplayName("Beim aufrufen des 'Remove'-Buttons wird die Main Page gezeigt und das Rezept ist gelöscht")
    void test_remove() throws Exception {
        demoRezept.setKategorie(Kategorie.NACHSPEISE);
        demoRezept.setName("Eiscreme");
        demoRezept.setId(0);
        when(service.getZubereitung(0)).thenReturn(demoRezept);

        mvc.perform(post("/get/zubereitung/0/remove"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andReturn().getResponse().getContentAsString();

        verify(service).removeRezept(0);
    }


    @Test
    @DisplayName("Beim aufrufen des 'Edit'-Links wird die Add Rezept Page gezeigt")
    void test_edit_page() throws Exception {
        demoRezept.setKategorie(Kategorie.NACHSPEISE);
        demoRezept.setName("Eiscreme");
        demoRezept.setId(0);
        when(service.getZubereitung(0)).thenReturn(demoRezept);

        mvc.perform(get("/get/zubereitung/0/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("add_rezept"));
    }

    @Test
    @DisplayName("Beim aufrufen des 'Edit'-Buttons wird die Main Page gezeigt und das Rezept ist verändert")
    void test_edit() throws Exception {
        demoRezept.setKategorie(Kategorie.NACHSPEISE);
        demoRezept.setName("Eiscreme");
        demoRezept.setId(0);
        when(service.getZubereitung(0)).thenReturn(demoRezept);

        mvc.perform(multipart("/get/zubereitung/0/edit")
                        .file(file)
                        .param("kategorie", demoRezept2.getKategorie().name())
                        .param("name", demoRezept2.getName())
                        .param("zutaten", demoRezept2.getZutaten())
                        .param("zubereitung", demoRezept2.getZubereitung()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(service).edit(eq(0), any());
    }

    @Test
    @DisplayName("Die Suchfunktion zeigt das gewünschte Rezept der Vorspeisen")
    void test_filter_vorspeise() throws Exception {
        demoRezept.setKategorie(Kategorie.VORSPEISE);
        demoRezept.setName("Caesarsalat");
        demoRezept.setId(0);
        when(service.getFilteredVorspeisen("Salat")).thenReturn(List.of(demoRezept));

        String result = mvc.perform(get("/vorspeise/filter?filter=Salat"))
                .andExpect(status().isOk())
                .andExpect(view().name("speisen/vorspeise_page"))
                .andReturn().getResponse().getContentAsString();

        assertThat(result).contains("Caesarsalat");
    }

    @Test
    @DisplayName("Die Suchfunktion zeigt das gewünschte Rezept der Nachspeisen")
    void test_filter_nachspeise() throws Exception {
        demoRezept.setKategorie(Kategorie.NACHSPEISE);
        demoRezept.setName("Eiscreme");
        demoRezept.setId(0);
        when(service.getFilteredNachspeisen("Creme")).thenReturn(List.of(demoRezept));

        String result = mvc.perform(get("/nachspeise/filter?filter=Creme"))
                .andExpect(status().isOk())
                .andExpect(view().name("speisen/nachspeise_page"))
                .andReturn().getResponse().getContentAsString();

        assertThat(result).contains("Eiscreme");
    }

    @Test
    @DisplayName("Die Suchfunktion zeigt das gewünschte Rezept der Hauptspeisen")
    void test_filter_hauptspeise() throws Exception {
        demoRezept.setKategorie(Kategorie.HAUPTSPEISE);
        demoRezept.setName("Spicy Burger");
        demoRezept.setId(0);
        when(service.getFilteredHauptspeisen("Burger")).thenReturn(List.of(demoRezept));

        String result = mvc.perform(get("/hauptspeise/filter?filter=Burger"))
                .andExpect(status().isOk())
                .andExpect(view().name("speisen/hauptspeise_page"))
                .andReturn().getResponse().getContentAsString();

        assertThat(result).contains("Spicy Burger");
    }
}
