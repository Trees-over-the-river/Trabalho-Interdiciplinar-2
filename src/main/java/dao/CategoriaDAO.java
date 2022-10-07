package dao;

import model.Categoria;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    final DAO dao;


    public CategoriaDAO(DAO dao) {
        this.dao = dao;
    }

    public void insert(int id, int categoria_id) throws SQLException {

        // TODO: Auto add categories if not present
        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "INSERT INTO usuario_prefere_categoria (categoria_id, usuario_id) VALUES(?, ?)"
        )) {
            statement.setInt(1, id);
            statement.setInt(2, categoria_id);
            statement.executeUpdate() ;
        }
    }

    public void delete(int id, int categoria_id) throws SQLException {
        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "DELETE FROM usuario_prefere_categoria WHERE categoria_id = ? AND usuario_id = ?"
        )) {
            statement.setInt(1, id);
            statement.setInt(2, categoria_id);
            statement.executeUpdate();
        }
    }

    private List<Categoria> parseCategorias(ResultSet resultSet) throws SQLException {
        List<Categoria> categorias = new ArrayList<>();


        while (resultSet.next()) {
            categorias.add(new Categoria(
                    resultSet.getInt(1),
                    resultSet.getString(2)
                    ));
        }

        return categorias;
    }

    public List<Categoria> list(int id) throws SQLException {
        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "SELECT categoria_id, nome FROM usuario_prefere_categoria " +
                        "JOIN categoria ON categoria.id = usuario_prefere_categoria.categoria_id " +
                        "WHERE usuario_prefere_categoria.usuario_id = ?"
        )) {
            statement.setInt(1, id);
            return parseCategorias(statement.executeQuery());
        }
    }
}
