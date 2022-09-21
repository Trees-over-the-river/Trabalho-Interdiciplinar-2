package app;

import dao.DAO;
import service.UsuarioService;

import static spark.Spark.port;
import static spark.Spark.staticFiles;


public class Aplicacao {

    private static final DAO dao = new DAO("localhost",
            "Portal_de_Filmes_(teste)",
            "ti2cc",
            "ti@cc");

	private static final UsuarioService usuarioService = new UsuarioService(dao);


	
    public static void main(String[] args) {
        port(6789);

        staticFiles.location("/public");
    }
}