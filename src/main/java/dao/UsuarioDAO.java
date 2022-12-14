package dao;

import model.Usuario;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UsuarioDAO  {

    private final DAO dao;

    public UsuarioDAO(DAO dao) {
        this.dao = dao;
    }


    public List<Usuario> getAll() throws SQLException {
        List<Usuario> usuarios;
        try (PreparedStatement statement = dao.getConexao().prepareStatement("SELECT * FROM usuario")) {
            usuarios = Usuario.parseUsuario(statement.executeQuery());
        }

        return usuarios;
    }



    public boolean update(Usuario usuario) throws SQLException {

        int updatedRows;

        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "UPDATE usuario " +
                        "SET email = ?, username = ?, senha = ?, nome = ?, sobrenome = ?, avatar = ? " +
                        "WHERE id = ?"
        )) {

            statement.setString(1, usuario.getEmail());
            statement.setString(2, usuario.getUsername());
            statement.setString(3, hashPassword(usuario.getSenha()));
            statement.setString(4, usuario.getNome());
            statement.setString(5, usuario.getSobrenome());
            statement.setBytes(6, usuario.getAvatar());
            statement.setInt(7, usuario.getId());
            updatedRows = statement.executeUpdate();

        }
        return updatedRows == 1;
    }

    public Usuario getByID(int id) throws SQLException {
        List<Usuario> usuarios;
        System.out.println("[UsuarioDAO] Pesquisando usuário no servidor");
        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "SELECT * FROM usuario WHERE id = ? LIMIT 1")) {
            statement.setInt(1, id);
            usuarios = Usuario.parseUsuario(statement.executeQuery());
        }

        System.out.println("[UsuarioDAO]" + ((usuarios.size() > 0)? "Usuário encontrado" : "Usuário não encontrado"));
        return (usuarios.size() > 0)? usuarios.get(0) : null;
    }


    public Integer addUser(Usuario usuario) throws SQLException {
        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "INSERT INTO usuario (email, username, senha, nome, sobrenome, avatar) VALUES(?, ?, ?, ?, ?, ?) RETURNING id;"
        )) {
            statement.setString(1, usuario.getEmail());
            statement.setString(2, usuario.getUsername());
            statement.setString(3, hashPassword(usuario.getSenha()));
            statement.setString(4, usuario.getNome());
            statement.setString(5, usuario.getSobrenome());
            statement.setBytes(6, usuario.getAvatar());

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("id");
        }
    }


    public static String hashPassword(String senha) {
        MessageDigest m;
        try {
            m = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        m.update("SALT".getBytes());
        return new BigInteger(m.digest(senha.getBytes())).abs().toString(16);

    }

    public Usuario authenticate(String email, String password) throws SQLException {
        List<Usuario> usuarios;
        System.out.println("[UsuarioDAO]tentando autenticar usuario");
        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "SELECT * FROM usuario WHERE email = ? AND senha = ? LIMIT 1"
        )) {
            statement.setString(1, email);
            statement.setString(2, hashPassword(password));

            usuarios = Usuario.parseUsuario(statement.executeQuery());
        }

        System.out.println("[UsuarioDAO]"+ ((usuarios.size() > 0)? "Usuário \"" + usuarios.get(0).getEmail() + "\" autenticado" : "Usuário não autenticado"));

        return (usuarios.size() > 0)? usuarios.get(0) : null;
    }

    public boolean updateNomeAndSobrenome(int id, String nome, String sobrenome) throws SQLException {
        int updatedRows;

        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "UPDATE usuario " +
                        "SET nome = ?, sobrenome = ? " +
                        "WHERE id = ?"
        )) {

            statement.setString(1, nome);
            statement.setString(2, sobrenome);
            statement.setInt(3, id);
            updatedRows = statement.executeUpdate();

        }
        return updatedRows == 1;
    }

    public boolean updateUsername(int id, String username) throws SQLException {
        int updatedRows;

        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "UPDATE usuario " +
                        "SET username = ?" +
                        "WHERE id = ?"
        )) {

            statement.setString(1, username);
            statement.setInt(2, id);
            updatedRows = statement.executeUpdate();

        }
        return updatedRows == 1;
    }

    public boolean updateEmail(int id, String email) throws SQLException {
        int updatedRows;

        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "UPDATE usuario " +
                        "SET email = ? " +
                        "WHERE id = ?"
        )) {

            statement.setString(1, email);
            statement.setInt(2, id);
            updatedRows = statement.executeUpdate();

        }
        return updatedRows == 1;
    }

    public boolean updateAvatar(int id, byte[] avatar) throws SQLException {
        int updatedRows;

        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "UPDATE usuario " +
                        "SET avatar = ? " +
                        "WHERE id = ?"
        )) {

            statement.setBytes(1, avatar);
            statement.setInt(2, id);
            updatedRows = statement.executeUpdate();

        }
        return updatedRows == 1;
    }

    public boolean updatePassword(int id, String password) throws SQLException {
        int updatedRows;

        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "UPDATE usuario " +
                        "SET senha = ? " +
                        "WHERE id = ?"
        )) {

            statement.setString(1, hashPassword(password));
            statement.setInt(2, id);
            updatedRows = statement.executeUpdate();

        }
        return updatedRows == 1;
    }

    public void delete(int id) throws SQLException {
        try (PreparedStatement statement = dao.getConexao().prepareStatement(
                "DELETE FROM usuario WHERE id = ?"
        )) {

            statement.setInt(1, id);
             statement.executeUpdate();
        }
    }

}
