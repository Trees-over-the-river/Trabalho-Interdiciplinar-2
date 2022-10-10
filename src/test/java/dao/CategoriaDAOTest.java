package dao;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class CategoriaDAOTest {

    @Test
    void updateCategorias() throws SQLException {
        CategoriaDAO categoriaDAO = new CategoriaDAO(new DAO("localhost",
                "Portal_de_Filmes_(teste)",
                "ti2cc",
                "ti@cc"));
        categoriaDAO.updateCategorias();
    }
}