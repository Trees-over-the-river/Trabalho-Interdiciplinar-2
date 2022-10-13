package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Categoria {
    private Integer id;
    private String nome;


    public Categoria(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public static List<Categoria> parseCategorias(ResultSet resultSet) throws SQLException {
        List<Categoria> categorias = new ArrayList<>();


        while (resultSet.next()) {
            categorias.add(new Categoria(
                    resultSet.getInt("id"),
                    resultSet.getString("nome")
            ));
        }

        return categorias;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
