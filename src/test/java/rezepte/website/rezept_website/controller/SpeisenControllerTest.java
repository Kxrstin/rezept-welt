package rezepte.website.rezept_website.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import rezepte.website.rezept_website.service.RezeptService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest
public class SpeisenControllerTest {
    @Autowired
    MockMvc mvc;

    @MockitoBean
    RezeptService service;

    @Test
    @DisplayName("Beim aufrufen des 'Vorspeise'-Links wird die Vorspeisenseite gezeigt")
    void test_vorspeise() throws Exception {
        mvc.perform(get("/vorspeise"))
                .andExpect(status().isOk())
                .andExpect(view().name("speisen/vorspeise_page"));
    }


    @Test
    @DisplayName("Beim aufrufen des Hauptspeise'-Links wird die Hauptspeisenseite gezeigt")
    void test_hauptspeise() throws Exception {
        mvc.perform(get("/hauptspeise"))
                .andExpect(status().isOk())
                .andExpect(view().name("speisen/hauptspeise_page"));
    }

    @Test
    @DisplayName("Beim aufrufen des 'Nachspeise'-Links wird die Nachspeisenseite gezeigt")
    void test_nachspeise() throws Exception {
        mvc.perform(get("/nachspeise"))
                .andExpect(status().isOk())
                .andExpect(view().name("speisen/nachspeise_page"));
    }
}
