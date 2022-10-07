package service;

import dao.CategoriaDAO;
import model.Categoria;
import responses.StandardSuccessResponse;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CategoriaService {

    private final CategoriaDAO categoriaDAO;

    public CategoriaService(CategoriaDAO categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
        try {
            updateCategorias();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCategorias() throws SQLException {
        categoriaDAO.updateCategorias();
    }

    public Object deletePrefererida(Request request, Response response) throws SQLException {
        int id = request.session().attribute("id");

        categoriaDAO.deletePreferencia(id, Integer.parseInt(request.params(":id")));
        return new StandardSuccessResponse("Categoria inserida com sucesso");
    }


    public Object listPreferidas(Request request, Response response) throws SQLException {
        int id = request.session().attribute("id");

        Map<Integer, String> categorias = new HashMap<>();

        for (Categoria categoria : categoriaDAO.listPreferencias(id)) {
            categorias.put(categoria.getId(), categoria.getNome());
        }

        return new StandardSuccessResponse(categorias);

    }

    public Object insertPreferida(Request request, Response response) throws SQLException {
        int id = request.session().attribute("id");

        categoriaDAO.insertPreferencia(id, Integer.parseInt(request.params(":id")));
        return new StandardSuccessResponse("Categoria inserida com sucesso");
    }
}
