package rezepte.website.rezept_website.controller.selection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import rezepte.website.rezept_website.service.selection.FilterRecipeService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static rezepte.website.rezept_website.object_mother.TestRezeptData.*;

@WebMvcTest(FilterRecipeController.class)
public class FilterRecipeControllerTest {
    @Autowired
    MockMvc mvc;

    @MockitoBean
    FilterRecipeService service;
    
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
