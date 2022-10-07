package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Avaliacao {
    private Integer usuarioID;
    private Integer filmeID;
    private Boolean gostou;
    private Integer pontuacao;
    private String feedback;

    public Avaliacao(Integer usuarioID, Integer filmeID, Boolean gostou, Integer pontuacao, String feedback) {
        this.usuarioID = usuarioID;
        this.filmeID = filmeID;
        this.gostou = gostou;
        this.pontuacao = pontuacao;
        this.feedback = feedback;
    }

    public Avaliacao() {
    }

    public static List<Avaliacao> parseAvaliacoes(ResultSet executeQuery) throws SQLException {
        List<Avaliacao> avaliacoes = new ArrayList<>();

        while (executeQuery.next()) {
            avaliacoes.add(new Avaliacao(
                    executeQuery.getInt("usuario_id"),
                    executeQuery.getInt("filme_id"),
                    executeQuery.getBoolean("gostou"),
                    executeQuery.getInt("pontuacao"),
                    executeQuery.getString("feedback")
            ));
        }
        return avaliacoes;
    }

    public Integer getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(Integer usuarioID) {
        this.usuarioID = usuarioID;
    }

    public Integer getFilmeID() {
        return filmeID;
    }

    public void setFilmeID(Integer filmeID) {
        this.filmeID = filmeID;
    }

    public Boolean getGostou() {
        return gostou;
    }

    public void setGostou(Boolean gostou) {
        this.gostou = gostou;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
