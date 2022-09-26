package service;


import dao.UsuarioDAO;
import model.Usuario;
import org.apache.http.MethodNotSupportedException;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO;
    private final Map<String, Integer> auth_tokens = new HashMap<>(1024);

    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }



    public Object getUsuarioByID(Request request, Response response) throws SQLException {
        Map<String, String> params = request.params();

        int id = Integer.parseInt(params.get(":id"));

        List<Usuario> usuarios = usuarioDAO.getByID(id);

        if (usuarios.size() == 1) {
            return usuarios.get(0);
        } else {
            return new HashMap<>();
        }
    }

    public Object updateNome(Request request, Response response) throws MethodNotSupportedException {
        throw new MethodNotSupportedException("");
    }

    public Object updateUsername(Request request, Response response) throws MethodNotSupportedException {
        throw new MethodNotSupportedException("");
    }

    public Object updateEmail(Request request, Response response) throws MethodNotSupportedException {
        throw new MethodNotSupportedException("");
    }

    public Object updateAvatar(Request request, Response response) throws MethodNotSupportedException {
        throw new MethodNotSupportedException("");
    }

    public Object updateSenha(Request request, Response response) throws MethodNotSupportedException {
        throw new MethodNotSupportedException("");
    }

    public Object insertCategoriaPreferecia(Request request, Response response) throws MethodNotSupportedException {
        throw new MethodNotSupportedException("");
    }

    public Object deleteUsuario(Request request, Response response) throws MethodNotSupportedException {
        throw new MethodNotSupportedException("");
    }

    public Object login(Request request, Response response) throws MethodNotSupportedException {
        throw new MethodNotSupportedException("");
    }

    public Object logon(Request request, Response response) throws MethodNotSupportedException {
        throw new MethodNotSupportedException("");
    }

    public boolean isAuthenticated(String login_token) {
        return auth_tokens.containsKey(login_token);
    }
}
