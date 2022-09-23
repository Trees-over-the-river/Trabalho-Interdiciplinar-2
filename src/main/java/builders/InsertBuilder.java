package builders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


public class InsertBuilder implements StatementBuilder {

    Connection connection;
    ArrayList<Object> values = new ArrayList<>();
    final InsertTemplateBuilder templateBuilder;


    public InsertBuilder(Connection connection, String table) {
        this.templateBuilder = new InsertTemplateBuilder(table);
        this.connection = connection;
    }


    public InsertBuilder values(String column, Object value) {
        values.add(value);
        templateBuilder.value(column);
        return this;
    }

    @Override
    public PreparedStatement buildStatement() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(templateBuilder.buildSQL());
        for (int i = 0; i < values.size(); i++) {
            statement.setObject(i+1, values.get(i));
        }

        return statement;
    }

    @Override
    public String buildSQL() throws SQLException {
        return buildStatement().toString();
    }


}
