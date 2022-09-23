package builders;

import java.util.ArrayList;

public class InsertTemplateBuilder implements SQLTemplateBuilder {

    ArrayList<String> columns = new ArrayList<>();

    String table;

    public InsertTemplateBuilder(String table) {
        this.table = table;
    }


    public InsertTemplateBuilder value(String value) {
        columns.add(value);
        return this;
    }

    @Override
    public String buildSQL() {

        if (columns.size() == 0) throw new RuntimeException();

        StringBuilder sql = new StringBuilder("INSERT INTO ")
                .append(table)
                .append("(")
                .append(String.join(", ", columns))
                .append(") VALUES(");
        for(int i = 0; i < columns.size()-1; i++) {
            sql.append("?,");
        }
        sql.append("?)");



        return sql.toString();
    }
}
