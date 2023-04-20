package co.incubyte.db;

import net.sf.jsqlparser.JSQLParserException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SqlParserShould {
    @Test
    @DisplayName("Parse a simple select statement")
    void parse_a_simple_select_statement() {
        SqlParser parser = new SqlParser();
        String strSql = "select * from customers";
        Sql sql = parser.parse(strSql);
        Assertions.assertThat(sql.getTables()).contains("customers");
    }

    @Test
    @DisplayName("Parse a simple select statement with multiple tables")
    void parse_a_simple_select_statement_with_multiple_tables() {
        SqlParser sqlParser = new SqlParser();
        String strSql = "select * from customers, orders, addresses";
        Sql sql = sqlParser.parse(strSql);
        Assertions.assertThat(sql.getTables()).contains("customers");
        Assertions.assertThat(sql.getJoins()).contains("orders", "addresses");
    }

    @Test
    @DisplayName("Parse a simple select statement with multiple tables and joins")
    void parse_a_simple_select_statement_with_multiple_tables_and_joins() {
        SqlParser sqlParser = new SqlParser();
        String strSql = "select * from customers, orders, addresses where customers.id = orders.customer_id and orders.address_id =addresses.id and customers.deleted !=  0";
        Sql sql = sqlParser.parse(strSql);
        Assertions.assertThat(sql.getTables()).contains("customers");
        Assertions.assertThat(sql.getJoins()).contains("orders", "addresses");
        Assertions.assertThat(sql.getWhere()).isEqualTo("customers.id = orders.customer_id AND orders.address_id = addresses.id AND customers.deleted != 0");
    }

    @Test
    @DisplayName("Parse a simple select statement with order by")
    void parse_a_simple_select_statement_with_order_by() {
        SqlParser sqlParser = new SqlParser();
        String strSql = "select * from customers, orders, addresses where customers.id = orders.customer_id and orders.address_id =addresses.id and customers.deleted !=  0 order by customers.firstname, customers.id";
        Sql sql = sqlParser.parse(strSql);
        Assertions.assertThat(sql.getTables()).contains("customers");
        Assertions.assertThat(sql.getJoins()).contains("orders", "addresses");
        Assertions.assertThat(sql.getWhere()).isEqualTo("customers.id = orders.customer_id AND orders.address_id = addresses.id AND customers.deleted != 0");
        Assertions.assertThat(sql.getOrderBy()).containsExactly("customers.firstname", "customers.id");
    }

    @Test
    @DisplayName("Parse a simple select statement with group by and order by")
    void parse_a_simple_select_statement_with_group_by_and_order_by() {
        SqlParser sqlParser = new SqlParser();
        String strSql = "select * from customers, orders, addresses where customers.id = orders.customer_id and orders.address_id =addresses.id and customers.deleted !=  0 group by customers.firstname, customers.id order by customers.firstname, customers.id";
        Sql sql = sqlParser.parse(strSql);
        Assertions.assertThat(sql.getTables()).contains("customers");
        Assertions.assertThat(sql.getJoins()).contains("orders", "addresses");
        Assertions.assertThat(sql.getWhere()).isEqualTo("customers.id = orders.customer_id AND orders.address_id = addresses.id AND customers.deleted != 0");
        Assertions.assertThat(sql.getOrderBy()).containsExactly("customers.firstname", "customers.id");
        Assertions.assertThat(sql.getGroupBy()).containsExactly("customers.firstname", "customers.id");
    }

    @Test
    @DisplayName("Parse column names from a simple select statement")
    void parse_column_names_from_a_simple_select_statement() {
        SqlParser sqlParser = new SqlParser();
        String strSql = "select customers.firstname, customers.lastname, orders.id, orders.customer_id, addresses.id, addresses.street from customers, orders, addresses where customers.id = orders.customer_id and orders.address_id =addresses.id and customers.deleted !=  0 group by customers.firstname, customers.id order by customers.firstname, customers.id";
        Sql sql = sqlParser.parse(strSql);
        Assertions.assertThat(sql.getTables()).contains("customers");
        Assertions.assertThat(sql.getJoins()).contains("orders", "addresses");
        Assertions.assertThat(sql.getWhere()).isEqualTo("customers.id = orders.customer_id AND orders.address_id = addresses.id AND customers.deleted != 0");
        Assertions.assertThat(sql.getOrderBy()).containsExactly("customers.firstname", "customers.id");
        Assertions.assertThat(sql.getGroupBy()).containsExactly("customers.firstname", "customers.id");
        Assertions.assertThat(sql.getColumns()).contains("customers.firstname", "customers.lastname", "orders.id", "orders.customer_id", "addresses.id", "addresses.street");
    }
}
