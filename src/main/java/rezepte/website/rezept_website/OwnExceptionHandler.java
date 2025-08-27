package rezepte.website.rezept_website;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@ControllerAdvice
public class OwnExceptionHandler {

    @ExceptionHandler(IOException.class)
    public String handleIOException(IOException ex,
                                    RedirectAttributes redirectAttributes,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {

        if (response.isCommitted()) return null;

        try {
            request.getSession(true);
            redirectAttributes.addFlashAttribute("error_message", "Fehler beim Verarbeiten der Datei.");
            return "redirect:/";
        } catch (IllegalStateException e) {
            return null;
        }
    }
}