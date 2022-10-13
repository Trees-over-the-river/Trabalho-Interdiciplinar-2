package service;

import com.google.gson.Gson;
import dao.FilmeDAO;
import model.Avaliacao;
import responses.StandardErrorResponse;
import responses.StandardSuccessResponse;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class FilmeService {

    private final FilmeDAO filmeDAO;


    public FilmeService(FilmeDAO filmeDao) {
        this.filmeDAO = filmeDao;
    }


    public Object listAvaliacoes(Request request, Response response) throws SQLException {
        int filmeID = Integer.parseInt(request.params(":id"));

        List<Avaliacao> avaliacoes = filmeDAO.listAvaliacoes(filmeID);

        return new StandardSuccessResponse(avaliacoes);
    }

    public Object listMelhoresAvaliacoes(Request request, Response response) throws SQLException {
        List<Avaliacao> avaliacoes = filmeDAO.listMelhoresAvaliacoes();

        return new StandardSuccessResponse(avaliacoes);
    }

    public Object deleteFilme(Request request, Response response) throws SQLException {
        int filmeID = Integer.parseInt(request.params(":id"));

        filmeDAO.deleteFilme(filmeID);

        return new StandardSuccessResponse("Filme deletado");
    }

    public Object listCategorias(Request request, Response response) throws SQLException {
        int filmeID = Integer.parseInt(request.params(":id"));

        return new StandardSuccessResponse(filmeDAO.getCategorias(filmeID));
    }

    public Object usuarioAssistidos(Request request, Response response) throws SQLException {
        int usuarioID = request.session().attribute("id");

        return new StandardSuccessResponse(filmeDAO.usuarioAssistidos(usuarioID));
    }

    public Object insertUsuarioAssistidos(Request request, Response response) throws SQLException {
        int usuarioID = request.session().attribute("id");
        int filmeID = Integer.parseInt(request.params(":id"));

        if (filmeDAO.insertUsuarioAssistidos(usuarioID, filmeID) > 0) {
            return new StandardSuccessResponse("Filme assistido adicionado");
        } else {
            response.status(400);
            return new StandardErrorResponse("Erro ao adicionar filme assistido");
        }
    }

    public Object deleteUsuarioAssistidos(Request request, Response response) throws SQLException {
        int usuarioID = request.session().attribute("id");
        int filmeID = Integer.parseInt(request.params(":id"));

        if (filmeDAO.deleteUsuarioAssistidos(usuarioID, filmeID) > 0) {
            return new StandardSuccessResponse("Filme assistido adicionado");
        } else {
            response.status(400);
            return new StandardErrorResponse("Erro ao adicionar filme assistido");
        }
    }

    public Object usuarioAvaliacao(Request request, Response response) throws SQLException {
        int usuarioID = request.session().attribute("id");
        int filmeID = Integer.parseInt(request.params(":id"));

        return new StandardSuccessResponse(filmeDAO.usuarioAvaliacao(usuarioID, filmeID));
    }

    public Object usuarioDeleteAvaliacao(Request request, Response response) throws SQLException {
        int usuarioID = request.session().attribute("id");
        int filmeID = Integer.parseInt(request.params(":id"));


        if (filmeDAO.deleteUsuarioAvaliacao(usuarioID, filmeID) > 0) {
            return new StandardSuccessResponse("Avaliação deletada");
        } else {
            response.status(400);
            return new StandardErrorResponse("Impossível deletar");
        }
    }

    public Object usuarioAvaliacaoRating(Request request, Response response) throws SQLException {
        int usuarioID = request.session().attribute("id");
        int filmeID = Integer.parseInt(request.params(":id"));

        int rating = (int) new Gson().fromJson(request.body(), HashMap.class).get("rating");

        if (filmeDAO.updateAvaliacaoRating(usuarioID, filmeID, rating)) {
            return new StandardSuccessResponse("Atualizada a pontuação do filme");
        } else {
            response.status(400);
            return new StandardErrorResponse("Erro ao atualizar o filme");
        }

    }

    public Object usuarioAvaliacaoLike(Request request, Response response) throws SQLException {
        int usuarioID = request.session().attribute("id");
        int filmeID = Integer.parseInt(request.params(":id"));

        boolean rating = (boolean) new Gson().fromJson(request.body(), HashMap.class).get("like");

        if (filmeDAO.updateAvaliacaoLike(usuarioID, filmeID, rating)) {
            return new StandardSuccessResponse("Atualizada a opção de gostar do filme");
        } else {
            response.status(400);
            return new StandardErrorResponse("Erro ao atualizar o filme");
        }
    }

    public Object usuarioAvaliacaoFeedBack(Request request, Response response) throws SQLException {
        int usuarioID = request.session().attribute("id");
        int filmeID = Integer.parseInt(request.params(":id"));

        String feedback = (String) new Gson().fromJson(request.body(), HashMap.class).get("feedback");

        if (filmeDAO.updateAvaliacaoFeedback(usuarioID, filmeID, feedback)) {
            return new StandardSuccessResponse("Atualizada a opção de gostar do filme");
        } else {
            response.status(400);
            return new StandardErrorResponse("Erro ao atualizar o filme");
        }
    }

    public Object insertUsuarioAvaliacao(Request request, Response response) throws SQLException {
        int usuarioID = request.session().attribute("id");
        int filmeID = Integer.parseInt(request.params(":id"));

        Avaliacao avaliacao = new Gson().fromJson(request.body(), Avaliacao.class);

        System.out.println(avaliacao);

        avaliacao.setFilmeID(filmeID);
        avaliacao.setUsuarioID(usuarioID);

        if (filmeDAO.insertUsuarioAvaliacao(avaliacao)) {
            return  new StandardSuccessResponse("Nova avaliação adicionada");
        } else {
            response.status(400);
            return new StandardErrorResponse("Erro ao adicionar jogo");
        }

    }
}
