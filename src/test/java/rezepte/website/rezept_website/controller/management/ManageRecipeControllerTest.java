package rezepte.website.rezept_website.controller.management;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import rezepte.website.rezept_website.service.management.ManageRecipeService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static rezepte.website.rezept_website.object_mother.TestRezeptData.*;
import static rezepte.website.rezept_website.object_mother.TestRezeptData.nachspeise;

@WebMvcTest(ManageRecipeController.class)
public class ManageRecipeControllerTest {
    @Autowired
    MockMvc mvc;

    @MockitoBean
    ManageRecipeService service;

    @Test
    @DisplayName("Die Startseite ist unter / erreichbar.")
    void test_main_page() throws Exception{
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("main_page"));
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

}
