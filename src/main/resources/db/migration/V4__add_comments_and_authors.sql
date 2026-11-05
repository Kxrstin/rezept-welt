-- 1. Tabelle für die Autoren
CREATE TABLE author (
     username VARCHAR(255) PRIMARY KEY,
     description VARCHAR(255),
     profile_picture BYTEA
);

-- 2. Tabelle für die Kommentare
CREATE TABLE comment (
      id VARCHAR(36) PRIMARY KEY,
      content VARCHAR(528) NOT NULL,
      created DATE NOT NULL,
      author VARCHAR(255),
      CONSTRAINT fk_comment_author FOREIGN KEY (author) REFERENCES author(username) ON DELETE CASCADE
);