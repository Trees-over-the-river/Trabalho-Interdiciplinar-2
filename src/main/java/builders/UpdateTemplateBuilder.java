package builders;

import java.sql.Statement;
import java.util.*;

public class UpdateTemplateBuilder implements SQLTemplateBuilder {


    private ArrayList<String> sets = new ArrayList<>();

    private List<String> wheres = new ArrayList<>();
    private String table;

    public UpdateTemplateBuilder(String table) {
        this.table = table;
    }



    @Override
    public String toString() {
        return buildSQL();
    }

    public UpdateTemplateBuilder set(String column) {
        sets.add(column);
        return this;
    }


    public UpdateTemplateBuilder where(String expr) {
        wheres.add(expr);
        return this;
    }

    public String buildSQL() {
        ArrayList<String> setExpressions = new ArrayList<>(sets.size());
        sets.forEach(column -> setExpressions.add(column + " = ?"));

        StringBuilder sql = new StringBuilder("UPDATE ").append(table);
        sql.append(" SET ").append(String.join(", ", setExpressions));
        sql.append(" WHERE ").append(String.join(", ", wheres));


        return sql.toString();
    }

}
