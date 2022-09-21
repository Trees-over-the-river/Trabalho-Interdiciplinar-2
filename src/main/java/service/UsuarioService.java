package service;

import dao.DAO;
import dao.UsuarioDAO;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public UsuarioService(DAO dao) {this.usuarioDAO = new UsuarioDAO(dao);}
}
