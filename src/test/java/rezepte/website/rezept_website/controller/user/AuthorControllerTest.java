package rezepte.website.rezept_website.controller.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import rezepte.website.rezept_website.controller.formulare.Author;
import rezepte.website.rezept_website.service.user.AuthorService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static rezepte.website.rezept_website.object_mother.TestRezeptData.demoFile;
import static rezepte.website.rezept_website.object_mother.TestRezeptData.demoFile2;

@WebMvcTest(AuthorController.class)
public
class AuthorControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private AuthorService service;

    private final Author author = new Author("Kiki", "123", demoFile());
    private final Author author2 = new Author("Kiki", "1234", demoFile2());

    @Test
    @DisplayName("Successfully add an author")
    void test_01() throws Exception {
        when(service.exists(any())).thenReturn(false);

        mvc.perform(multipart("/api/author/add")
                .file(demoFile())
                .param("username", "Kerstin")
                .param("description", "Toll"))
                .andExpect(status().is(200));
    }

    @Test
    @DisplayName("Throws 409 when author already exists")
    void test_02() throws Exception {
        when(service.exists(any())).thenReturn(true);

        mvc.perform(multipart("/api/author/add")
                        .file(demoFile())
                        .param("username", "Kerstin")
                        .param("description", "Toll"))
                        .andExpect(status().is(409));
    }

    @Test
    @DisplayName("Updates a known author")
    void test_03() throws Exception {
        when(service.findByUsername(any())).thenReturn(author);

        mvc.perform(multipart("/api/author/update")
                        .file(demoFile2())
                        .param("username", author.getUsername())
                        .param("description", author2.getDescription()))
                .andExpect(status().isOk());    }
}