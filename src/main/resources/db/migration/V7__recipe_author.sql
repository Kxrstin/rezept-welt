ALTER TABLE rezept_form
ADD COLUMN username VARCHAR(250) DEFAULT NULL,
ADD CONSTRAINT fk_rezept_author
FOREIGN KEY (username) REFERENCES author(username) ON DELETE SET NULL;

CREATE TABLE stars (
    id VARCHAR(36) PRIMARY KEY,
    content VARCHAR(250),
    ranking DECIMAL(2, 1) CHECK(ranking IN (1, 2, 3, 4, 5)), /*ranking IN (1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5)),*/
    username VARCHAR(255) DEFAULT NULL,
    created_at TIMESTAMP,
    rezept_id INTEGER,
    FOREIGN KEY(username) REFERENCES author(username) ON DELETE SET NULL,
    FOREIGN KEY(rezept_id) REFERENCES rezept_form(id) ON DELETE CASCADE
);