package builders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UpdateBuilder implements StatementBuilder {

    ArrayList<Object> setValues = new ArrayList<>();
    ArrayList<Object> wheresValues = new ArrayList<>();
    final UpdateTemplateBuilder templateBuilder;
    Connection connection;

    public UpdateBuilder(Connection connection, String table) {
        templateBuilder = new UpdateTemplateBuilder(table);
        this.connection = connection;
    }


    public UpdateBuilder set(String column, Object value) {
        templateBuilder.set(column);
        setValues.add(value);
        return this;
    }

    public UpdateBuilder where(String expr) {
        templateBuilder.where(expr);
        return this;
    };

    public UpdateBuilder where(String expr, Object value) {
        where(expr);
        wheresValues.add(value);
        return this;
    };

    @Override
    public PreparedStatement buildStatement() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(templateBuilder.buildSQL());
        for (int i = 0; i < setValues.size(); i++) {
            statement.setObject(i+1, setValues.get(i));
        }
        for (int i = 0; i < wheresValues.size(); i++) {
            statement.setObject(i+setValues.size()+1, wheresValues.get(i));
        }

        return  statement;
    }

    @Override
    public String buildSQL() throws SQLException {
        return buildStatement().toString();
    }
}
