package dao;

import model.Avaliacao;
import model.Categoria;
import responses.StandardSuccessResponse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmeDAO {

    private final DAO dao;

    public FilmeDAO(DAO dao) {
        this.dao = dao;
    }




    private void addFilme(int id) throws SQLException {
        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "INSERT INTO filme VALUES(?) ON CONFLICT DO NOTHING"
        )) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public List<Avaliacao> listAvaliacoes(int filmeID) throws SQLException {

        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "SELECT * FROM usuario_avalia_filme " +
                "WHERE filme_id = ?")) {
            statement.setInt(1, filmeID);
            return Avaliacao.parseAvaliacoes(statement.executeQuery());
        }

    }

    public void deleteFilme(int filmeID) throws SQLException {
        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "DELETE FROM filme WHERE id = ?")) {
            statement.setInt(1, filmeID);
            statement.executeUpdate();
        }
    }

    public List<Categoria> getCategorias(int filmeID) throws SQLException {
        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "SELECT categoria.id, categoria.nome FROM categoria " +
                "JOIN filme_possui_categoria ON filme_possui_categoria.categoria_id = categoria.id " +
                "JOIN filme ON filme.id = filme_possui_categoria.filme_id " +
                "WHERE filme.id = ?")) {

            statement.setInt(1, filmeID);
            return Categoria.parseCategorias(statement.executeQuery());
        }
    }

    public Object usuarioAssistidos(int usuarioID) throws SQLException {
        List<Integer> filmes = new ArrayList<>();

        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "SELECT filme_id " +
                        "FROM usuario_assistiu_filme " +
                        "WHERE usuario_id = ?")) {
            statement.setInt(1, usuarioID);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                filmes.add(resultSet.getInt(1));
            }

            return new StandardSuccessResponse(filmes);
        }
    }

    public int insertUsuarioAssistidos(int usuarioID, int filmeID) throws SQLException {
        addFilme(filmeID);

        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "INSERT INTO usuario_assistiu_filme (usuario_id, filme_id) " +
                        "VALUES (?,?)")) {
            statement.setInt(1, usuarioID);
            statement.setInt(2, filmeID);
            return  statement.executeUpdate();
        }
    }

    public int deleteUsuarioAssistidos(int usuarioID, int filmeID) throws SQLException {
        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "DELETE FROM usuario_assistiu_filme " +
                        "WHERE usuario_id = ? AND filme_id = ?")) {
            statement.setInt(1, usuarioID);
            statement.setInt(2, filmeID);
            return statement.executeUpdate();
        }

    }

    public Avaliacao usuarioAvaliacao(int usuarioID, int filmeID) throws SQLException {
        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "SELECT * FROM usuario_avalia_filme " +
                        "WHERE filme_id = ? AND usuario_id = ?")) {
            statement.setInt(1, filmeID);
            statement.setInt(2, usuarioID);

            List<Avaliacao> avaliacoes = Avaliacao.parseAvaliacoes(statement.executeQuery());
            return (avaliacoes.size() > 0)? avaliacoes.get(0) : null;
        }
    }

    public int deleteUsuarioAvaliacao(int usuarioID, int filmeID) throws SQLException {
        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "DELETE FROM usuario_avalia_filme " +
                        "WHERE filme_id = ? AND usuario_id = ?")) {
            statement.setInt(1, filmeID);
            statement.setInt(2, usuarioID);

            return statement.executeUpdate();
        }

    }

    public boolean updateAvaliacaoRating(int usuarioID, int filmeID, int rating) throws SQLException {
        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "UPDATE usuario_avalia_filme" +
                        "SET pontuacao = ?" +
                        "WHERE filme_id = ? AND usuario_id = ?")) {
            statement.setInt(1, rating);
            statement.setInt(2, filmeID);
            statement.setInt(3, usuarioID);
            return statement.executeUpdate() > 0;
        }
    }

    public boolean updateAvaliacaoLike(int usuarioID, int filmeID, boolean rating) throws SQLException {
        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "UPDATE usuario_avalia_filme" +
                        "SET gostou = ?" +
                        "WHERE filme_id = ? AND usuario_id = ?")) {
            statement.setBoolean(1, rating);
            statement.setInt(2, filmeID);
            statement.setInt(3, usuarioID);
            return statement.executeUpdate() > 0;
        }
    }

    public boolean updateAvaliacaoFeedback(int usuarioID, int filmeID, String feedback) throws SQLException {
        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "UPDATE usuario_avalia_filme" +
                        "SET feedback = ?" +
                        "WHERE filme_id = ? AND usuario_id = ?")) {
            statement.setString(1, feedback);
            statement.setInt(2, filmeID);
            statement.setInt(3, usuarioID);
            return statement.executeUpdate() > 0;
        }
    }

    public boolean insertUsuarioAvaliacao(int usuarioID, int filmeID, Avaliacao avaliacao) throws SQLException {
        addFilme(filmeID);

        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "INSERT INTO usuario_avalia_filme (filme_id, gostou, pontuacao, feedback, usuario_id) " +
                        "VALUES (?,?,?,?,?)")) {
            statement.setInt(1, filmeID);
            statement.setBoolean(2, avaliacao.getGostou() != null && avaliacao.getGostou());
            statement.setInt(3, (avaliacao.getPontuacao() == null)? 0 : avaliacao.getPontuacao());
            statement.setString(4, (avaliacao.getFeedback() == null)? "": avaliacao.getFeedback());
            statement.setInt(5, usuarioID);

            return statement.executeUpdate() > 0;
        }
    }
}
