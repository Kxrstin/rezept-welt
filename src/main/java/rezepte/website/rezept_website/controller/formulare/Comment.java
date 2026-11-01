package rezepte.website.rezept_website.controller.formulare;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.UUID;

public class Comment {
    @Id
    private UUID id;

    private Author author;

    private String content;

    private LocalDate created;

    public Comment() {}

    public Comment(
            UUID commentId,
            Author author,
            String content,
            LocalDate createdAt
    ) {
        this.id = commentId;
        this.author = author;
        this.content = content;
        this.created = createdAt;
    }

    public Author getAuthor() {
        return author;
    }

    public LocalDate getCreated() {
        return created;
    }

    public UUID getId() {
        return id;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
