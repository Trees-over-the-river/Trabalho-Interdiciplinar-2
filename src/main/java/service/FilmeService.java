package service;

import dao.FilmeDAO;
import org.apache.http.MethodNotSupportedException;
import spark.Request;
import spark.Response;

public class FilmeService {

    private final FilmeDAO filmeDAO;


    public FilmeService(FilmeDAO filmeDao) {
        this.filmeDAO = filmeDao;
    }

    public Object getFilmeByID(Request request, Response response) throws MethodNotSupportedException {
        throw new MethodNotSupportedException("");
    }

    public Object getAvaliacoes(Request request, Response response) throws MethodNotSupportedException {
        throw new MethodNotSupportedException("");
    }

    public Object replaceAvaliacao(Request request, Response response) throws MethodNotSupportedException {
        throw new MethodNotSupportedException("");
    }

    public Object getCategoriasOfFilme(Request request, Response response) throws MethodNotSupportedException {
        throw new MethodNotSupportedException("");
    }
}
