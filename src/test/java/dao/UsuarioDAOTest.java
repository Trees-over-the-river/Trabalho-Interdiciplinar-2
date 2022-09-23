package dao;

import model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

class UsuarioDAOTest {

    public DAO dao = new DAO("localhost",
            "Portal_de_Filmes_(teste)",
            "ti2cc",
            "ti@cc");

    public UsuarioDAO usuarioDAO = new UsuarioDAO(dao);

    @BeforeEach
    void setUp() throws SQLException {
        dao.conectar();
    }

    @AfterEach
    void tearDown() throws SQLException {
        dao.close();
    }

    @Test
    void getID() throws SQLException {
        usuarioDAO.getByID(3);
    }

    @Test
    void insert() throws SQLException {
        if (usuarioDAO.insert(new Usuario("test@exaple.com", "test", "Senha padrão")) == 0) throw new AssertionError();
    }

    @Test
    void hashPassword() throws NoSuchAlgorithmException {
        if (usuarioDAO.hashPassword("test").length() > 64) throw new AssertionError();
    }

    @Test
    void getAll() throws SQLException {
        System.out.println(usuarioDAO.getAll());
    }
}