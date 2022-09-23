package dao;

import model.Usuario;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;

public class UsuarioDAO  {

    private DAO dao;

    public UsuarioDAO(DAO dao) {
        this.dao = dao;
    }


    public List<Usuario> getAll() throws SQLException {
        PreparedStatement statement = dao.conexao.prepareStatement("SELECT * FROM usuario");

        ArrayList<Usuario> usuarios = parseUsuario(statement.executeQuery());

        statement.close();


        return usuarios;
    }



    public int update(Usuario usuario) throws SQLException {



        PreparedStatement statement = dao.conexao.prepareStatement(
            "UPDATE usuario " +
            "SET email = ?, username = ?, senha = ?, nome = ?, sobrenome = ?, avatar = ?" +
            "WHERE id = ?"
        );

        statement.setString(1, usuario.getEmail());
        statement.setString(2, usuario.getUsername());
        statement.setString(3, hashPassword(usuario.getSenha()));
        statement.setString(4, usuario.getNome());
        statement.setString(5, usuario.getSobrenome());
        statement.setBytes(6, usuario.getAvatar());
        statement.setInt(7, usuario.getID());

        return statement.executeUpdate();
    }

    public List<Usuario> getByID(int id) throws SQLException {
        PreparedStatement statement = dao.conexao.prepareStatement("SELECT * FROM usuario WHERE id = ?");
        statement.setInt(1, id);
        ArrayList<Usuario> usuarios = parseUsuario(statement.executeQuery());

        return usuarios;
    }

    private static ArrayList<Usuario> parseUsuario(ResultSet resultSet) throws SQLException {
        ArrayList<Usuario> usuarios = new ArrayList<>();

        while (resultSet.next()) {
            usuarios.add(new Usuario(
                    resultSet.getInt("id"),
                    resultSet.getString("email"),
                    resultSet.getString("username"),
                    resultSet.getString("senha"),
                    resultSet.getString("nome"),
                    resultSet.getString("sobrenome"),
                    resultSet.getBytes("avatar")));
        }
        return usuarios;
    }

    public int insert(Usuario usuario) throws SQLException {
        PreparedStatement statement = dao.conexao.prepareStatement(
                "INSERT INTO usuario (email, username, senha, nome, sobrenome, avatar) VALUES(?, ?, ?, ?, ?, ?)"
        );
        statement.setString(1, usuario.getEmail());
        statement.setString(2, usuario.getUsername());
        statement.setString(3, hashPassword(usuario.getSenha()));
        statement.setString(4, usuario.getNome());
        statement.setString(5, usuario.getSobrenome());
        statement.setBytes(6, usuario.getAvatar());

        return statement.executeUpdate();
    }


    public String hashPassword(String senha) {
        MessageDigest m= null;
        try {
            m = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        m.update("SALT".getBytes());
        return new BigInteger(m.digest(senha.getBytes())).abs().toString(16);

    }
}
