package service;


import com.google.gson.Gson;
import dao.CategoriaDAO;
import dao.UsuarioDAO;
import model.Usuario;
import responses.StandardErrorResponse;
import responses.StandardSuccessResponse;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;

public class UsuarioService {


    private final UsuarioDAO usuarioDAO;
    private final CategoriaDAO categoriaDAO;

    public UsuarioService(UsuarioDAO usuarioDAO, CategoriaDAO categoriaDAO) {
        this.usuarioDAO = usuarioDAO;
        this.categoriaDAO = categoriaDAO;
    }



    public Object getUsuario(Request request, Response response) throws SQLException {
        int id = request.session().attribute("id");

        Usuario usuario = usuarioDAO.getByID(id);

        if (usuario == null) {
            return new StandardErrorResponse("Usuário not found");
        }

        usuario.setSenha(null);
        usuario.setId(null);
        return new StandardSuccessResponse(usuario);
    }

    public Object updateNome(Request request, Response response) throws SQLException {
        int id = request.session().attribute("id");
        var body = new Gson().fromJson(request.body(), HashMap.class);

        try {
            usuarioDAO.updateNomeAndSobrenome(id, (String) body.get("nome"), (String) body.get("sobrenome"));
        } catch (RuntimeException e) {
            response.status(400);
            return new StandardErrorResponse("Requisição com formato inválido");
        }


        return new StandardSuccessResponse("Usuário alterado");

    }

    public Object updateUsername(Request request, Response response) throws SQLException {
        int id = request.session().attribute("id");

        try {
            usuarioDAO.updateUsername(id, (String) new Gson().fromJson(request.body(), HashMap.class).get("username"));
        } catch (RuntimeException e) {
            response.status(400);
            return new StandardErrorResponse("Requisição com formato inválido");
        }


        return new StandardSuccessResponse("Usuário alterado");
    }

    public Object updateEmail(Request request, Response response) throws SQLException {
        int id = request.session().attribute("id");

        try {
            usuarioDAO.updateEmail(id, (String) new Gson().fromJson(request.body(), HashMap.class).get("email"));
        } catch (RuntimeException e) {
            response.status(400);
            return new StandardErrorResponse("Requisição com formato inválido");
        }


        return new StandardSuccessResponse("Usuário alterado");
    }

    public Object updateAvatar(Request request, Response response) throws SQLException {
        int id = request.session().attribute("id");

        try {
            usuarioDAO.updateAvatar(id, Base64.getDecoder().decode((String) new Gson().fromJson(request.body(), HashMap.class).get("avatar")));
        } catch (RuntimeException e) {
            response.status(400);
            return new StandardErrorResponse("Requisição com formato inválido");
        }


        return new StandardSuccessResponse("Usuário alterado");
    }

    public Object updateSenha(Request request, Response response) throws SQLException {
        int id = request.session().attribute("id");

        try {
            usuarioDAO.updatePassword(id, (String) new Gson().fromJson(request.body(), HashMap.class).get("senha"));
        } catch (RuntimeException e) {
            response.status(400);
            return new StandardErrorResponse("Requisição com formato inválido");
        }


        return new StandardSuccessResponse("Usuário alterado");
    }

    public Object deleteUsuario(Request request, Response response) throws SQLException {
        int id = request.session().attribute("id");
        usuarioDAO.delete(id);
        request.session(false);

        return new StandardSuccessResponse("Usuário removido com sucesso");
    }

    public Object login(Request request, Response response) throws SQLException {
        var body = new Gson().fromJson(request.body(), HashMap.class);

        Usuario usuario = usuarioDAO.authenticate(body.get("email").toString(), body.get("senha").toString());

        if (usuario != null) {
            request.session().attribute("id", usuario.getId());
            return new StandardSuccessResponse("Logado");
        }

        response.status(404);
        return new StandardErrorResponse("Usuário inexistente");
    }

    public Object logon(Request request, Response response) {
        var usuario = new Gson().fromJson(request.body(), Usuario.class);

        try  {
            usuarioDAO.insert(usuario);
            return new StandardSuccessResponse("Sucesso ao criar usuário");
        } catch (SQLException e){
            response.status(400);
            return new StandardErrorResponse("Erro ao criar usuário");
        }

    }
}
