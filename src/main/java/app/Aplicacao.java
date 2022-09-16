package app;

import static spark.Spark.*;
import service.ProdutoService;


public class Aplicacao {
	
	private static final ProdutoService produtoService = new ProdutoService();
	
    public static void main(String[] args) {
        port(6789);

        staticFiles.location("/public");

        post("/produto/insert", produtoService::insert);

        get("/produto/:id", produtoService::get);

        get("/produto/list/:orderby", produtoService::getAll);

        get("/produto/update/:id", produtoService::getToUpdate);

        post("/produto/update/:id", produtoService::update);

        get("/produto/delete/:id", produtoService::delete);


    }
}