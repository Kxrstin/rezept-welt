package rezepte.website.rezept_website.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import rezepte.website.rezept_website.service.RezeptService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(MainPageController.class)
public class MainPageControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    RezeptService service;

    @Test
    @DisplayName("Die Startseite ist unter / erreichbar.")
    void test_main_page() throws Exception{
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("main_page"));
    }

    @Test
    @DisplayName("Die AddRezept Seite ist unter /add/rezept erreichbar.")
    void test_add_rezept() throws Exception{
        mvc.perform(get("/add/rezept"))
                .andExpect(status().isOk())
                .andExpect(view().name("add_rezept"));
    }

    @Test
    @DisplayName("Beim erfolgreichen Hinzufügen eines Rezeptes erscheint eine Success Message auf der Main Page")
    void test_success() throws Exception{
        mvc.perform(get("/?success=true"))
                .andExpect(status().isOk())
                .andExpect(view().name("main_page"));
    }

    @Test
    @DisplayName("Beim Verstoß gegen die Validierung (NotBlank, etc.) soll man zur gleichen Seite zurückgeleitet werden")
    void test_validation() throws Exception{
        mvc.perform(post("/add/rezept"))
                .andExpect(status().isOk())
                .andExpect(view().name("add_rezept"));
    }
}
