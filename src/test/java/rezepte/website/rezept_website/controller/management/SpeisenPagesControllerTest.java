package rezepte.website.rezept_website.controller.management;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import rezepte.website.rezept_website.service.management.SpeisenPagesService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static rezepte.website.rezept_website.object_mother.TestRezeptData.*;

@WebMvcTest(SpeisenPagesController.class)
public class SpeisenPagesControllerTest {
    @Autowired
    MockMvc mvc;

    @MockitoBean
    SpeisenPagesService service;

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
}
