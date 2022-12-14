package service;


import com.google.gson.Gson;
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

    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }



    public Object getUsuario(Request request, Response ignoredResponse) throws SQLException {
        int id = request.session().attribute("id");

        System.out.println("[UsuarioService] Obtendo informações do usuário ID=" + id);
        Usuario usuario = usuarioDAO.getByID(id);

        if (usuario == null) {
            System.out.println("[UsuarioService] Usuário inexistente");
            return new StandardErrorResponse("Usuário not found");
        }
        System.out.println("[UsuarioService] Devolvendo informações do usuário");
        usuario.setSenha(null);
        usuario.setId(null);

        return new StandardSuccessResponse(usuario);
    }

    public Object getUsuarioID(Request request, Response ignoredResponse) throws SQLException {
        int userID = Integer.parseInt(request.params(":id"));

        Usuario usuario = usuarioDAO.getByID(userID);

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


        return new StandardSuccessResponse("Nome alterado");

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


        return new StandardSuccessResponse("Email alterado");
    }

    public Object updateAvatar(Request request, Response response) throws SQLException {
        int id = request.session().attribute("id");

        try {
            usuarioDAO.updateAvatar(id, Base64.getDecoder().decode((String) new Gson().fromJson(request.body(), HashMap.class).get("avatar")));
        } catch (RuntimeException e) {
            response.status(400);
            return new StandardErrorResponse("Requisição com formato inválido");
        }


        return new StandardSuccessResponse("Avatar alterado");
    }

    public Object updateSenha(Request request, Response response) throws SQLException {
        int id = request.session().attribute("id");

        try {
            usuarioDAO.updatePassword(id, (String) new Gson().fromJson(request.body(), HashMap.class).get("senha"));
        } catch (RuntimeException e) {
            response.status(400);
            return new StandardErrorResponse("Requisição com formato inválido");
        }


        return new StandardSuccessResponse("Senha alterada");
    }

    public Object deleteUsuario(Request request, Response ignoredResponse) throws SQLException {
        int id = request.session().attribute("id");
        usuarioDAO.delete(id);
        request.session(false);

        return new StandardSuccessResponse("Usuário removido com sucesso");
    }

    public Object login(Request request, Response response) throws SQLException {
        var body = new Gson().fromJson(request.body(), HashMap.class);

        System.out.println("[UsuarioService] Usuário \""+ body.get("email") + "\" tentando conectar");

        Usuario usuario = usuarioDAO.authenticate(body.get("email").toString(), body.get("senha").toString());


        if (usuario != null) {
            System.out.println("[UsuarioService] Retornando cookie de sessão");
            request.session().attribute("id", usuario.getId());
            return new StandardSuccessResponse("Logado");
        }

        System.out.println("[UsuarioService] Falha no login");
        response.status(404);
        return new StandardErrorResponse("Usuário inexistente");
    }

    public Object logon(Request request, Response response) {
        var usuario = new Gson().fromJson(request.body(), Usuario.class);

        try  {
            usuarioDAO.addUser(usuario);
            return new StandardSuccessResponse("Sucesso ao criar usuário");
        } catch (SQLException e){
            response.status(400);
            return new StandardErrorResponse("Erro ao criar usuário");
        }

    }

    public Object logout(Request request, Response ignoredResponse) {
        request.session().invalidate();
        return new StandardSuccessResponse("Logout efetuado!");
    }
}
