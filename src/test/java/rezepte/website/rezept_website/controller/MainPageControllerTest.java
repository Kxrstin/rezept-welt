package rezepte.website.rezept_website.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(MainPageController.class)
public class MainPageControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Die Startseite ist unter / erreichbar.")
    void test_main_page() throws Exception{
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("main_page"));
    }

    // TODO: Fixen
    @Test
    @DisplayName("Die AddRezept Seite ist unter /add/rezept erreichbar.")
    void test_add_rezept() throws Exception{
        mvc.perform(get("/add/rezept"))
                .andExpect(status().isOk())
                .andExpect(view().name("add_rezept"));
    }
}
