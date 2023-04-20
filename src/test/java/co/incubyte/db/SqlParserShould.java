package co.incubyte.db;

import net.sf.jsqlparser.JSQLParserException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SqlParserShould {
    @Test
    @DisplayName("Parse a simple select statement")
    public void parse_a_simple_select_statement() throws JSQLParserException {
        SqlParser parser = new SqlParser();
        String strSql = "select * from customers";
        Sql sql = parser.parse(strSql);
        Assertions.assertThat(sql.getTables()).contains("customers");
    }

    @Test
    @DisplayName("Parse a simple select statement with multiple tables")
    public void parse_a_simple_select_statement_with_multiple_tables() throws JSQLParserException {
        SqlParser sqlParser = new SqlParser();
        String strSql = "select * from customers, orders";
        Sql sql = sqlParser.parse(strSql);
        Assertions.assertThat(sql.getTables()).contains("customers", "orders");
    }
}
