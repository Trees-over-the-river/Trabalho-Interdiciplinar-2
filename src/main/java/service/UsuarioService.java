package service;


import com.google.gson.Gson;
import dao.UsuarioDAO;
import model.Usuario;
import org.apache.http.MethodNotSupportedException;
import responses.StandardErrorResponse;
import responses.StandardSuccessResponse;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UsuarioService {


    public static final String COOKIE_NAME = "auth-token";
    private final UsuarioDAO usuarioDAO;
    private final Map<String, Integer> auth_tokens = new HashMap<>(1024);

    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }



    public Object getUsuario(Request request, Response response) throws SQLException {
        int id = getLoggedUser(request.cookie(COOKIE_NAME));

        Usuario usuario = usuarioDAO.getByID(id);

        if (usuario == null) {
            return new StandardErrorResponse("Usuário not found");
        }

        usuario.setSenha(null);
        usuario.setID(null);
        return new StandardSuccessResponse(usuario);
    }

    public Object updateNome(Request request, Response response) throws SQLException {
        int id = getLoggedUser(request.cookie(COOKIE_NAME));
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
        int id = getLoggedUser(request.cookie(COOKIE_NAME));

        try {
            usuarioDAO.updateUsername(id, (String) new Gson().fromJson(request.body(), HashMap.class).get("username"));
        } catch (RuntimeException e) {
            response.status(400);
            return new StandardErrorResponse("Requisição com formato inválido");
        }


        return new StandardSuccessResponse("Usuário alterado");
    }

    public Object updateEmail(Request request, Response response) throws SQLException {
        int id = getLoggedUser(request.cookie(COOKIE_NAME));

        try {
            usuarioDAO.updateEmail(id, (String) new Gson().fromJson(request.body(), HashMap.class).get("email"));
        } catch (RuntimeException e) {
            response.status(400);
            return new StandardErrorResponse("Requisição com formato inválido");
        }


        return new StandardSuccessResponse("Usuário alterado");
    }

    public Object updateAvatar(Request request, Response response) throws SQLException {
        int id = getLoggedUser(request.cookie(COOKIE_NAME));

        try {
            usuarioDAO.updateAvatar(id, Base64.getDecoder().decode((String) new Gson().fromJson(request.body(), HashMap.class).get("avatar")));
        } catch (RuntimeException e) {
            response.status(400);
            return new StandardErrorResponse("Requisição com formato inválido");
        }


        return new StandardSuccessResponse("Usuário alterado");
    }

    public Object updateSenha(Request request, Response response) throws SQLException {
        int id = getLoggedUser(request.cookie(COOKIE_NAME));

        try {
            usuarioDAO.updatePassword(id, (String) new Gson().fromJson(request.body(), HashMap.class).get("senha"));
        } catch (RuntimeException e) {
            response.status(400);
            return new StandardErrorResponse("Requisição com formato inválido");
        }


        return new StandardSuccessResponse("Usuário alterado");
    }

    public Object insertCategoriaPreferecia(Request request, Response response) throws MethodNotSupportedException {
        throw new MethodNotSupportedException("");
    }

    public Object deleteUsuario(Request request, Response response) throws SQLException {
        int id = auth_tokens.remove(request.cookie(COOKIE_NAME));
        usuarioDAO.delete(id);

        return new StandardSuccessResponse("Usuário removido com sucesso");
    }

    public Object login(Request request, Response response) throws SQLException {
        var body = new Gson().fromJson(request.body(), HashMap.class);

        Usuario usuario = usuarioDAO.authenticate(body.get("email").toString(), body.get("senha").toString());

        if (usuario != null) {
            UUID uuid = UUID.randomUUID();
            response.cookie(COOKIE_NAME, uuid.toString());
            auth_tokens.put(uuid.toString(), usuario.getID());
            return new StandardSuccessResponse("Logado");
        }

        response.status(404);
        return new StandardErrorResponse("Usuário inexistente");
    }

    public Object logon(Request request, Response response) throws SQLException {
        var usuario = new Gson().fromJson(request.body(), Usuario.class);

        if (usuarioDAO.insert(usuario)) {
            return new StandardSuccessResponse("Sucesso ao criar usuário");
        } else {
            return new StandardErrorResponse("Erro ao criar usuário");
        }

    }

    public int getLoggedUser(String login_token) {
        return auth_tokens.getOrDefault(login_token, -1);
    }

    public Object deleteCategoriaPreferencia(Request request, Response response) throws MethodNotSupportedException {
        throw new MethodNotSupportedException("");
    }
}
