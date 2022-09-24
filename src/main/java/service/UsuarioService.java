package service;


import dao.UsuarioDAO;
import model.Usuario;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

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
}
