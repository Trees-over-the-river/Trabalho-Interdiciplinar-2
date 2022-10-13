package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Usuario {

    private Integer id;
    private String email;
    private String username;
    private String senha;
    private String nome = null;
    private String sobrenome = null;

    private byte[] avatar;

    public Usuario() {
    }

    public Usuario(String email, String username, String senha) {
        this.email = email;
        this.username = username;
        this.senha = senha;
    }

    public Usuario(String email, String username, String senha, String nome, String sobrenome, byte[] avatar) {
        this.email = email;
        this.username = username;
        this.senha = senha;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.avatar = avatar;
    }

    public Usuario(int id, String email, String username, String senha, String nome, String sobrenome, byte[] avatar) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.senha = senha;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.avatar = avatar;
    }


    public static List<Usuario> parseUsuario(ResultSet resultSet) throws SQLException {
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
    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && email.equals(usuario.email) && username.equals(usuario.username) && senha.equals(usuario.senha) && Objects.equals(nome, usuario.nome) && Objects.equals(sobrenome, usuario.sobrenome) && Arrays.equals(avatar, usuario.avatar);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, email, username, senha, nome, sobrenome);
        result = 31 * result + Arrays.hashCode(avatar);
        return result;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "ID=" + id +
                ", Email='" + email + '\'' +
                ", Username='" + username + '\'' +
                ", Senha='" + senha + '\'' +
                ", Nome='" + nome + '\'' +
                ", Sobrenome='" + sobrenome + '\'' +
                ", Avatar=" + Arrays.toString(avatar) +
                '}';
    }
}
