package rezepte.website.rezept_website.controller.management;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import rezepte.website.rezept_website.controller.formulare.RezeptForm;
import rezepte.website.rezept_website.service.management.AddRecipeService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static rezepte.website.rezept_website.object_mother.TestRezeptData.demoFile;
import static rezepte.website.rezept_website.object_mother.TestRezeptData.vorspeise;

@WebMvcTest(AddRecipeController.class)
public class AddRecipeControllerTest {
    @Autowired
    MockMvc mvc;

    @MockitoBean
    AddRecipeService service;

    @Test
    @DisplayName("Die AddRezept Seite ist unter /add/rezept erreichbar.")
    void test_add_rezept() throws Exception{
        mvc.perform(get("/add/rezept"))
                .andExpect(status().isOk())
                .andExpect(view().name("add_rezept"));
    }

    @Test
    @DisplayName("Beim Verstoß gegen die Validierung (NotBlank, etc.) soll man zur gleichen Seite zurückgeleitet werden")
    void test_validation() throws Exception{
        mvc.perform(multipart("/add/rezept")
                        .file(demoFile()))
                .andExpect(status().isOk())
                .andExpect(view().name("add_rezept"));
    }

    @Test
    @DisplayName("Rezepte werden erfolgreich hinzugefügt")
    void test_add() throws Exception {
        RezeptForm rezept = vorspeise();

        mvc.perform(multipart("/add/rezept")
                        .file(demoFile())
                        .param("kategorie", rezept.getKategorie().name())
                        .param("name", rezept.getName())
                        .param("zutaten", rezept.getZutaten())
                        .param("zubereitung", rezept.getZubereitung()))
                .andExpect(status().is3xxRedirection());

        verify(service).addRezept(any(RezeptForm.class));
    }
}
