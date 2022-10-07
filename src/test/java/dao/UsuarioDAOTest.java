package dao;

import org.junit.jupiter.api.Test;

class UsuarioDAOTest {

    @Test
    void hashPassword() {
        System.out.println(UsuarioDAO.hashPassword("1234"));
    }
}