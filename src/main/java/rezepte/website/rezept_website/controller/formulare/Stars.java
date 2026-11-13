package rezepte.website.rezept_website.controller.formulare;

import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;

import java.time.LocalDateTime;


public class Stars {
    private String id;
    private String content;
    private double ranking;
    private String username;
    private Integer rezeptId;
    private LocalDateTime createdAt;

    @Transient
    private Author author;

    @PersistenceCreator
    public Stars(
            String id,
            String content,
            double ranking,
            String username,
            Integer rezeptId,
            LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.ranking = ranking;
        this.username = username;
        this.rezeptId = rezeptId;
        this.createdAt = createdAt;
    }

    public Stars(
            String id,
            String content,
            double ranking,
            String username,
            Integer rezeptId,
            Author author) {
        this.id = id;
        this.content = content;
        this.ranking = ranking;
        this.username = username;
        this.rezeptId = rezeptId;
        this.author = author;
    }

    public boolean isValidRanking(double ranking) {
        int rankingInt = (int) (ranking * 10);
        for(int i = 10; i <= 50; i += 5) if(i == rankingInt) return true;
        return false;
    }

    public Author getAuthor() {
        return author;
    }

    public Integer getRezeptId() {
        return rezeptId;
    }

    public double getRanking() {
        return ranking;
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRanking(double ranking) {
        this.ranking = ranking;
    }

    public void setRezeptId(Integer rezeptId) {
        this.rezeptId = rezeptId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}