package app;

import com.google.gson.Gson;
import dao.DAO;
import dao.FilmeDAO;
import dao.UsuarioDAO;
import org.apache.http.MethodNotSupportedException;
import service.FilmeService;
import service.UsuarioService;
import spark.Spark;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static spark.Spark.*;

public class Aplicacao {

    private static final DAO dao = new DAO("localhost",
            "Portal_de_Filmes_(teste)",
            "ti2cc",
            "ti@cc");

    private static final Gson gson = new Gson();
    private static final UsuarioService usuarioService = new UsuarioService(new UsuarioDAO(dao));
    private static final FilmeService filmeService = new FilmeService(new FilmeDAO(dao));

    private static final int port = 6789;


    public static void main(String[] args) throws IOException {

        Logger logger = Logger.getLogger("Logger");
        FileHandler fileHandler;

        fileHandler = new FileHandler("src/main/resources/Log.log");
        logger.addHandler(fileHandler);
        fileHandler.setFormatter(new SimpleFormatter());


        port(port);



        logger.info("Starting the server at http://localhost:" + port);


        Spark.defaultResponseTransformer(gson::toJson);


       path("/usuario/", () -> {
           path(":id", () -> {
               get("/", usuarioService::getUsuarioByID);
               put("nome", usuarioService::updateNome);
               put("username", usuarioService::updateUsername);
               put("email", usuarioService::updateEmail);
               put("avatar", usuarioService::updateAvatar);
               put("senha", usuarioService::updateSenha);
               post("analitics/categoria", usuarioService::insertCategoriaPreferecia);
               delete("/", usuarioService::deleteUsuario);
           });

          post("login", usuarioService::login);
          post("logon", usuarioService::logon);
       });

       path("/filme/:id", () -> {
          get("/", filmeService::getFilmeByID);
          get("avaliacoes", filmeService::getAvaliacoes);
          post("avaliacao", filmeService::replaceAvaliacao);
          get("categorias", filmeService::getCategoriasOfFilme);
       });

        exception(IllegalArgumentException.class, (e, request, response) ->
                response.status(400));

        exception(MethodNotSupportedException.class, (e, request, response) -> {
            response.status(501);
            logger.warning(e.toString());
        });

        exception(SQLException.class, (e, request, response) -> {
            response.status(503);
            logger.warning(e.toString());
        } );

        exception(RuntimeException.class, (e, request, response) -> {
            response.status(500);
            logger.severe(e.toString() + "\n" + Arrays.toString(e.getStackTrace()));
        });

    }
}