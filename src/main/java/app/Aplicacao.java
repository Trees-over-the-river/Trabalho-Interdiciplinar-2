package app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.CategoriaDAO;
import dao.DAO;
import dao.FilmeDAO;
import dao.UsuarioDAO;
import org.apache.http.MethodNotSupportedException;
import responses.StandardSuccessResponse;
import service.CategoriaService;
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
            "portal_de_filmes",
            "ti2cc",
            "ti@cc");

    private static final Gson gson = new GsonBuilder().serializeNulls().create();
    private static final UsuarioService usuarioService = new UsuarioService(new UsuarioDAO(dao), new CategoriaDAO(dao));
    private static final FilmeService filmeService = new FilmeService(new FilmeDAO(dao));

    private static final CategoriaService categoriaService = new CategoriaService(new CategoriaDAO(dao));

    private static final int port = 25565;


    public static void main(String[] args) throws IOException {

        Logger logger = Logger.getLogger("Logger");
        FileHandler fileHandler;

        fileHandler = new FileHandler("src/main/resources/logs/Log_%g.log");
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);


        port(port);

        staticFiles.location("/public");


        get("/",(request, response) -> {
            response.redirect("/front-end/index.html");
            return new StandardSuccessResponse(null);
        });


        logger.info("Starting the server at http://localhost:" + port);

        defaultResponseTransformer(gson::toJson);

        path("/api", () -> {
            path("/usuario", () -> {
                before("/*", (request, response) -> {
                   if (request.session().attribute("id") == null) halt(403);
                });
                get("/", usuarioService::getUsuario);
                put("/nome", usuarioService::updateNome);
                put("/username", usuarioService::updateUsername);
                put("/email", usuarioService::updateEmail);
                put("/avatar", usuarioService::updateAvatar);
                put("/senha", usuarioService::updateSenha);
                delete("/", usuarioService::deleteUsuario);
                path("/categoria", () -> {
                   get("/", categoriaService::listPreferidas);
                   path("/:id", () -> {
                       post("/", categoriaService::insertPreferida);
                       delete("/", categoriaService::deletePrefererida);
                   });
                });
                path("/filmes", () -> {
                    path("/assistidos", () -> {
                        get("/", filmeService::usuarioAssistidos);
                        post("/:id", filmeService::insertUsuarioAssistidos);
                        delete("/:id", filmeService::deleteUsuarioAssistidos);
                    });
                    path("/avaliados", () -> {
                        path("/:id", () -> {
                            get("/", filmeService::usuarioAvaliacao);
                            delete("/", filmeService::usuarioDeleteAvaliacao);
                            put("/rating", filmeService::usuarioAvaliacaoRating);
                            put("/like", filmeService::usuarioAvaliacaoLike);
                            put("/feedback", filmeService::usuarioAvaliacaoFeedBack);
                            post("/", filmeService::insertUsuarioAvaliacao);
                        });
                    });
                });
            });

            post("/login", usuarioService::login);
            post("/logon", usuarioService::logon);
            post("/logout", usuarioService::logout);

            path("/filme/:id", () -> {
                get("/avaliacoes", filmeService::listAvaliacoes);
                delete("/", filmeService::deleteFilme);
                get("/categorias", filmeService::listCategorias);
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