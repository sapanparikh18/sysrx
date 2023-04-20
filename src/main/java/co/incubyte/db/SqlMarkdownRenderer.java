package co.incubyte.db;

public class SqlMarkdownRenderer {
    private SqlMarkdownRenderer() {
    }

    public static String render(Sql sql) {
        return "| Columns | Tables | Joins | Where | Group By | Order By |\n" +
                "| --- | --- | --- | --- | --- | --- |\n" +
                "| " + String.join(", ", sql.getColumns()) + " | " + String.join(", ", sql.getTables()) + " | " + String.join(", ", sql.getJoins()) + " | " + sql.getWhere() + " | " + String.join(", ", sql.getGroupBy()) + " | " + String.join(", ", sql.getOrderBy()) + " |";
    }
}
