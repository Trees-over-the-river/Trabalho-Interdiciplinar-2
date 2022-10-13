package dao;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbGenre;
import model.Categoria;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    final DAO dao;
    private static final String TMDB_API_KEY = "881d144cf96ddeddcf2b7cb82f636d69";


    public CategoriaDAO(DAO dao) {
        this.dao = dao;
    }


    public void updateCategorias() throws SQLException {
        TmdbGenre genre = new TmdbApi(TMDB_API_KEY).getGenre();
        var genres = genre.getGenreList("pt-br");

        if (genres.size() > 0) {
            try (PreparedStatement statement = dao.getConexao().prepareStatement("DELETE FROM public.categoria")) {
                statement.executeUpdate();
            }

            List<String> values = new ArrayList<>(genres.size());
            for (int i = 0; i < genres.size(); i++) {
                values.add("(?,?)");
            }


            try (PreparedStatement statement = dao.getConexao().prepareStatement(
                    "INSERT INTO categoria (id, nome) VALUES" + String.join(",", values))) {
                    for (int i = 0; i < genres.size(); i++) {
                        statement.setInt(i*2+1, genres.get(i).getId());
                        statement.setString(i*2+2, genres.get(i).getName());
                    }

                    statement.executeUpdate();
            }
        }
    }

    public void insertPreferencia(int id, int categoria_id) throws SQLException {
        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "INSERT INTO usuario_prefere_categoria (categoria_id, usuario_id) VALUES(?, ?)"
        )) {
            statement.setInt(1, id);
            statement.setInt(2, categoria_id);
            statement.executeUpdate() ;
        }
    }

    public void deletePreferencia(int id, int categoria_id) throws SQLException {
        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "DELETE FROM usuario_prefere_categoria WHERE categoria_id = ? AND usuario_id = ?"
        )) {
            statement.setInt(1, id);
            statement.setInt(2, categoria_id);
            statement.executeUpdate();
        }
    }


    public List<Categoria> listPreferencias(int id) throws SQLException {
        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "SELECT categoria_id, nome FROM usuario_prefere_categoria " +
                        "JOIN categoria ON categoria.id = usuario_prefere_categoria.categoria_id " +
                        "WHERE usuario_prefere_categoria.usuario_id = ?"
        )) {
            statement.setInt(1, id);
            return Categoria.parseCategorias(statement.executeQuery());
        }
    }
}
