package dao;

import model.Avaliacao;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class FilmeDAOTest {

    private static final DAO dao = new DAO("localhost",
            "portal_de_filmes",
            "ti2cc",
            "ti@cc");


    @Test
    void insertUsuarioAvaliacao() throws SQLException {
        FilmeDAO filmeDAO = new FilmeDAO(dao);

        filmeDAO.insertUsuarioAvaliacao(new Avaliacao(1, 2, true, 3, "asdasdasd"));
    }
}