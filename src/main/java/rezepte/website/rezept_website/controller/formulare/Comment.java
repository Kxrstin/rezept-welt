package rezepte.website.rezept_website.controller.formulare;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

public class Comment {
    @Id
    private String id;

    @Column("author")
    private String username;

    @Transient
    private Author author;

    private String content;

    private LocalDateTime created;

    public Comment() {}

    public Comment(
            String commentId,
            Author author,
            String content,
            LocalDateTime createdAt
    ) {
        this.id = commentId;
        this.author = author;
        this.content = content;
        this.created = createdAt;
        this.username = author.getUsername();
    }

    @PersistenceCreator
    public Comment(String id, String username, String content, LocalDateTime created) {
        this.id = id;
        this.username = username;
        this.content = content;
        this.created = created;
    }

    public Author getAuthor() {
        return author;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public String getId() {
        return id;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
