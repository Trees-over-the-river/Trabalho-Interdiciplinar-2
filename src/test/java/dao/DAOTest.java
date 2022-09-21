package dao;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class DAOTest {

    @Test
    void conectar() throws SQLException {
        DAO dao = new DAO("localhost",
                "postgres",
                "ti2cc",
                "ti@cc");

        if (!dao.conectar()) throw new AssertionError();
    }
}