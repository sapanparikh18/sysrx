package co.incubyte.db;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SqlMarkdownRendererShould {
    @Test
    @DisplayName("should render markdown from the sql")
    void should_render_markdown_from_the_sql() {
        SqlParser sqlParser = new SqlParser();
        String strSql = "select customers.firstname, customers.lastname, orders.id, orders.customer_id, addresses.id, addresses.street from customers, orders, addresses where customers.id = orders.customer_id and orders.address_id =addresses.id and customers.deleted !=  0 group by customers.firstname, customers.id order by customers.firstname, customers.id";
        Sql sql = sqlParser.parse(strSql);
        String md = SqlMarkdownRenderer.render(sql);
        Assertions.assertThat(md).isEqualTo("| Columns | Tables | Joins | Where | Group By | Order By |\n" +
                "| --- | --- | --- | --- | --- | --- |\n" +
                "| customers.firstname, customers.lastname, orders.id, orders.customer_id, addresses.id, addresses.street | customers | orders, addresses | customers.id = orders.customer_id AND orders.address_id = addresses.id AND customers.deleted != 0 | customers.firstname, customers.id | customers.firstname, customers.id |");
    }
}
