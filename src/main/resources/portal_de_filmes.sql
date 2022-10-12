---
---DROP SCHEMA IF EXISTS public CASCADE;
---
---CREATE SCHEMA public;
---

-- -----------------------------------------------------
-- Table 'portal_de_filmes'.'Usuario'
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Usuario (
  ID SERIAL NOT NULL,
  Email VARCHAR(150) NOT NULL UNIQUE,
  Username VARCHAR(80) NOT NULL,
  Senha CHAR(64) NOT NULL,
  Nome VARCHAR(100) NULL,
  Sobrenome VARCHAR(200) NULL,
  Avatar BYTEA NULL,
PRIMARY KEY (ID)
);

-- -----------------------------------------------------
-- Table 'portal_de_filmes'.'Categoria'
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Categoria (
  ID INT NOT NULL,
  Nome VARCHAR(50) NULL,
PRIMARY KEY (ID)
);

-- -----------------------------------------------------
-- Table 'portal_de_filmes'.'Filme'
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Filme (
  ID INT NOT NULL,
PRIMARY KEY (ID)
);


-- -----------------------------------------------------
-- Table 'portal_de_filmes'.'Usuario_prefere_Categoria'
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Usuario_prefere_Categoria (
  Categoria_ID INT NOT NULL,
  Usuario_ID INT NOT NULL,
  PRIMARY KEY (Categoria_ID, Usuario_ID),
CONSTRAINT fk_Categoria_has_Usuario_Categoria
FOREIGN KEY (Categoria_ID)
REFERENCES Categoria (ID)
ON DELETE NO ACTION
ON UPDATE NO ACTION,
CONSTRAINT fk_Categoria_has_Usuario_Usuario1
FOREIGN KEY (Usuario_ID)
REFERENCES Usuario (ID)
ON DELETE NO ACTION
ON UPDATE NO ACTION
);


-- -----------------------------------------------------
-- Table 'portal_de_filmes'.'Filme_possui_Categoria'
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Filme_possui_Categoria (
  Categoria_ID INT NOT NULL,
  Filme_ID INT NOT NULL,
  PRIMARY KEY (Categoria_ID, Filme_ID),
CONSTRAINT fk_Categoria_has_Filme_Categoria1
FOREIGN KEY (Categoria_ID)
REFERENCES Categoria (ID)
ON DELETE NO ACTION
ON UPDATE NO ACTION,
CONSTRAINT fk_Categoria_has_Filme_Filme1
FOREIGN KEY (Filme_ID)
REFERENCES Filme (ID)
ON DELETE NO ACTION
ON UPDATE NO ACTION
);


-- -----------------------------------------------------
-- Table 'portal_de_filmes'.'Usuario_avalia_Filme'
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Usuario_avalia_Filme (
  Usuario_ID INT NOT NULL UNIQUE,
  Filme_ID INT NOT NULL UNIQUE,
  Gostou BOOLEAN NULL,
  Pontuacao INT NULL,
  FeedBack VARCHAR(250) NULL,
  PRIMARY KEY (Usuario_ID, Filme_ID),
CONSTRAINT fk_Usuario_has_Filme_Usuário1
FOREIGN KEY (Usuario_ID)
REFERENCES Usuario (ID)
ON DELETE NO ACTION
ON UPDATE NO ACTION,
CONSTRAINT fk_Usuario_has_Filme_Filme1
FOREIGN KEY (Filme_ID)
REFERENCES Filme (ID)
ON DELETE NO ACTION
ON UPDATE NO ACTION
);


-- -----------------------------------------------------
-- Table 'portal_de_filmes'.'Usuario_assistiu_Filme'
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Usuario_assistiu_Filme (
  Usuario_ID INT NOT NULL,
  Filme_ID INT NOT NULL,
  PRIMARY KEY (Usuario_ID, Filme_ID),
CONSTRAINT Filme_ID
FOREIGN KEY (Filme_ID)
REFERENCES Filme (ID)
ON DELETE CASCADE
ON UPDATE CASCADE,
CONSTRAINT Usuario_ID
FOREIGN KEY (Usuario_ID)
REFERENCES Usuario (ID)
ON DELETE CASCADE
ON UPDATE CASCADE)
