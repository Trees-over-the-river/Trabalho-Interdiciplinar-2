package dao;

import model.Usuario;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class UsuarioDAOTest {

    private static final DAO dao = new DAO("localhost",
            "portal_de_filmes",
            "ti2cc",
            "ti@cc");


    @Test
    void hashPassword() {
        System.out.println(UsuarioDAO.hashPassword("1234"));
    }


    @Test
    void addUser() throws SQLException {
        UsuarioDAO usuarioDAO = new UsuarioDAO(dao);

        System.out.println(usuarioDAO.addUser(
                new Usuario(
                        "test@gmail.com",
                        "naru",
                        "123",
                        "eu",
                        "mesmo",
                        null)));

    }
}