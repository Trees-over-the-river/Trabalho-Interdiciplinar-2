package model;

import java.util.Arrays;
import java.util.Objects;

public class Usuario {

    private int ID;
    private String Email;
    private String Username;
    private String Senha;
    private String Nome;
    private String Sobrenome;

    private byte[] Avatar;

    public Usuario() {
    }

    public Usuario(String email, String username, String senha) {
        Email = email;
        Username = username;
        Senha = senha;
    }

    public Usuario(int ID, String email, String username, String senha, String nome, String sobrenome, byte[] avatar) {
        this.ID = ID;
        Email = email;
        Username = username;
        Senha = senha;
        Nome = nome;
        Sobrenome = sobrenome;
        Avatar = avatar;
    }

    public byte[] getAvatar() {
        return Avatar;
    }

    public void setAvatar(byte[] avatar) {
        Avatar = avatar;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getSobrenome() {
        return Sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        Sobrenome = sobrenome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return ID == usuario.ID && Email.equals(usuario.Email) && Username.equals(usuario.Username) && Senha.equals(usuario.Senha) && Objects.equals(Nome, usuario.Nome) && Objects.equals(Sobrenome, usuario.Sobrenome) && Arrays.equals(Avatar, usuario.Avatar);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(ID, Email, Username, Senha, Nome, Sobrenome);
        result = 31 * result + Arrays.hashCode(Avatar);
        return result;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "ID=" + ID +
                ", Email='" + Email + '\'' +
                ", Username='" + Username + '\'' +
                ", Senha='" + Senha + '\'' +
                ", Nome='" + Nome + '\'' +
                ", Sobrenome='" + Sobrenome + '\'' +
                ", Avatar=" + Arrays.toString(Avatar) +
                '}';
    }
}
