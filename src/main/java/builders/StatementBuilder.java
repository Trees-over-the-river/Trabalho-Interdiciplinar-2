package builders;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public interface StatementBuilder {


    PreparedStatement buildStatement() throws SQLException;

    String buildSQL() throws SQLException;

}
