package rezepte.website.rezept_website.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import rezepte.website.rezept_website.service.RezeptService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static rezepte.website.rezept_website.object_mother.TestRezeptData.*;

@WebMvcTest
public class SpeisenControllerTest {
    @Autowired
    MockMvc mvc;

    @MockitoBean
    RezeptService service;

    @Test
    @DisplayName("Beim Aufrufen des 'Vorspeise'-Links wird die Vorspeisenseite gezeigt")
    void test_vorspeise() throws Exception {
        when(service.getVorspeisen()).thenReturn(List.of(vorspeise()));

        String result = mvc.perform(get("/vorspeise"))
                .andExpect(status().isOk())
                .andExpect(view().name("speisen/vorspeise_page"))
                .andReturn()
                .getResponse().getContentAsString();

        assertThat(result).contains("Caesar Salad");
    }


    @Test
    @DisplayName("Beim Aufrufen des Hauptspeise'-Links wird die Hauptspeisenseite gezeigt")
    void test_hauptspeise() throws Exception {
        when(service.getHauptspeisen()).thenReturn(List.of(hauptspeise()));

        String result = mvc.perform(get("/hauptspeise"))
                .andExpect(status().isOk())
                .andExpect(view().name("speisen/hauptspeise_page"))
                .andReturn()
                .getResponse().getContentAsString();

        assertThat(result).contains("Ofengemüse mediterran");
    }

    @Test
    @DisplayName("Beim Aufrufen des 'Nachspeise'-Links wird die Nachspeisenseite gezeigt")
    void test_nachspeise() throws Exception {
        when(service.getNachspeisen()).thenReturn(List.of(nachspeise()));

        String result = mvc.perform(get("/nachspeise"))
                .andExpect(status().isOk())
                .andExpect(view().name("speisen/nachspeise_page"))
                .andReturn()
                .getResponse().getContentAsString();

        assertThat(result).contains("Schoko-Bananen-Pfannkuchen");
    }

    @Test
    @DisplayName("Beim Aufrufen des 'Remove'-Links wird die Remove Page gezeigt")
    void test_remove_page() throws Exception {
        when(service.getZubereitung(0)).thenReturn(vorspeise());

        mvc.perform(get("/get/zubereitung/0/remove"))
                .andExpect(status().isOk())
                .andExpect(view().name("change/remove"));
    }

    @Test
    @DisplayName("Beim Aufrufen des 'Remove'-Buttons wird die Main Page gezeigt und das Rezept ist gelöscht")
    void test_remove() throws Exception {
        when(service.getZubereitung(0)).thenReturn(vorspeise());

        mvc.perform(post("/get/zubereitung/0/remove"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andReturn().getResponse().getContentAsString();

        verify(service).removeRezept(0);
    }


    @Test
    @DisplayName("Beim Aufrufen des 'Edit'-Links wird die Add Rezept Page gezeigt")
    void test_edit_page() throws Exception {
        when(service.getZubereitung(0)).thenReturn(nachspeise());

        mvc.perform(get("/get/zubereitung/0/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("add_rezept"));
    }

    @Test
    @DisplayName("Beim Aufrufen des 'Edit'-Buttons wird die Main Page gezeigt und das Rezept ist verändert")
    void test_edit() throws Exception {
        when(service.getZubereitung(0)).thenReturn(nachspeise());

        mvc.perform(multipart("/get/zubereitung/0/edit")
                        .file(demoFile())
                        .param("kategorie", nachspeise().getKategorie().name())
                        .param("name", nachspeise().getName())
                        .param("zutaten", nachspeise().getZutaten())
                        .param("zubereitung", nachspeise().getZubereitung()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(service).edit(eq(0), any());
    }

    @Test
    @DisplayName("Die Suchfunktion zeigt das gewünschte Rezept der Vorspeisen")
    void test_filter_vorspeise() throws Exception {
        when(service.getFilteredVorspeisen("Caesar")).thenReturn(List.of(vorspeise()));

        String result = mvc.perform(get("/vorspeise/filter?filter=Caesar"))
                .andExpect(status().isOk())
                .andExpect(view().name("speisen/vorspeise_page"))
                .andReturn().getResponse().getContentAsString();

        assertThat(result).contains("Caesar Salad");
    }

    @Test
    @DisplayName("Die Suchfunktion zeigt das gewünschte Rezept der Nachspeisen")
    void test_filter_nachspeise() throws Exception {
        when(service.getFilteredNachspeisen("Schoko")).thenReturn(List.of(nachspeise()));

        String result = mvc.perform(get("/nachspeise/filter?filter=Schoko"))
                .andExpect(status().isOk())
                .andExpect(view().name("speisen/nachspeise_page"))
                .andReturn().getResponse().getContentAsString();

        assertThat(result).contains("Schoko-Bananen-Pfannkuchen");
    }

    @Test
    @DisplayName("Die Suchfunktion zeigt das gewünschte Rezept der Hauptspeisen")
    void test_filter_hauptspeise() throws Exception {
        when(service.getFilteredHauptspeisen("Ofengemüse")).thenReturn(List.of(hauptspeise()));

        String result = mvc.perform(get("/hauptspeise/filter?filter=Ofengemüse"))
                .andExpect(status().isOk())
                .andExpect(view().name("speisen/hauptspeise_page"))
                .andReturn().getResponse().getContentAsString();

        assertThat(result).contains("Ofengemüse mediterran");
    }
}
