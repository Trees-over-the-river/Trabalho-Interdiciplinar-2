package app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.DAO;
import dao.FilmeDAO;
import dao.UsuarioDAO;
import org.apache.http.MethodNotSupportedException;
import service.FilmeService;
import service.UsuarioService;

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

    private static final Gson gson = new GsonBuilder().serializeNulls().create();
    private static final UsuarioService usuarioService = new UsuarioService(new UsuarioDAO(dao));
    private static final FilmeService filmeService = new FilmeService(new FilmeDAO(dao));

    private static final int port = 25565;


    public static void main(String[] args) throws IOException {

        Logger logger = Logger.getLogger("Logger");
        FileHandler fileHandler;

        fileHandler = new FileHandler("src/main/resources/logs/Log_%g.log");
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);


        port(port);

        staticFiles.location("/public");


        logger.info("Starting the server at http://localhost:" + port);

        defaultResponseTransformer(gson::toJson);

        path("/api", () -> {
            path("/usuario", () -> {
                before("/*", (request, response) -> {
                   if (usuarioService.getLoggedUser(request.cookie(UsuarioService.COOKIE_NAME)) == -1) halt(403);
                });
                get("/", usuarioService::getUsuario);
                put("/nome", usuarioService::updateNome);
                put("/username", usuarioService::updateUsername);
                put("/email", usuarioService::updateEmail);
                put("/avatar", usuarioService::updateAvatar);
                put("/senha", usuarioService::updateSenha);
                post("/analitics/categoria", usuarioService::insertCategoriaPreferecia);
                delete("/analitics/categodia", usuarioService::deleteCategoriaPreferencia);
                delete("/", usuarioService::deleteUsuario);
            });

            post("/login", usuarioService::login);
            post("/logon", usuarioService::logon);


            path("/filme/:id", () -> {
                get("", filmeService::getFilmeByID);
                get("/avaliacoes", filmeService::getAvaliacoes);
                post("/avaliacao", filmeService::replaceAvaliacao);
                get("/categorias", filmeService::getCategoriasOfFilme);
            });
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