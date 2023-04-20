package co.incubyte.db;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

public class SqlParser {
    public Sql parse(String strSql) {
        try {
            return parseSql(strSql);
        } catch (JSQLParserException e) {
            throw new IllegalArgumentException("Invalid SQL", e);
        }
    }

    private static Sql parseSql(String strSql) throws JSQLParserException {
        Sql sql = new Sql();
        Statement statement = CCJSqlParserUtil.parse(strSql);
        if (statement instanceof Select) {
            PlainSelect plainSelect = getPlainSelect((Select) statement);
            addTables(sql, plainSelect);
            addJoins(sql, plainSelect);
            addWhere(sql, plainSelect);
            addOrderBy(sql, plainSelect);
            addGroupBy(sql, plainSelect);
            addColumns(sql, plainSelect);
        }
        return sql;
    }

    private static void addColumns(Sql sql, PlainSelect plainSelect) {
        if (plainSelect.getSelectItems() == null) {
            return;
        }
        plainSelect.getSelectItems().stream()
                .map(Object::toString)
                .forEach(sql::addColumn);
    }

    private static void addGroupBy(Sql sql, PlainSelect plainSelect) {
        if (plainSelect.getGroupBy() == null) {
            return;
        }
        plainSelect.getGroupBy().getGroupByExpressionList().getExpressions().stream()
                .map(Object::toString)
                .forEach(sql::addGroupBy);
    }

    private static void addOrderBy(Sql sql, PlainSelect plainSelect) {
        if (plainSelect.getOrderByElements() == null) {
            return;
        }
        plainSelect.getOrderByElements().stream()
                .map(OrderByElement::toString)
                .forEach(sql::addOrderBy);
    }

    private static void addWhere(Sql sql, PlainSelect plainSelect) {
        Expression where = plainSelect.getWhere();
        if (where == null) {
            return;
        }
        sql.setWhere(where.toString());
    }

    private static void addJoins(Sql sql, PlainSelect plainSelect) {
        if (plainSelect.getJoins() == null) {
            return;
        }
        plainSelect.getJoins().
                forEach(join ->
                        sql.addJoins(join.getRightItem().toString())
                );
    }

    private static PlainSelect getPlainSelect(Select statement) {
        return (PlainSelect) statement.getSelectBody();
    }

    private static void addTables(Sql sql, PlainSelect plainSelect) {
        FromItem fromItem = plainSelect.getFromItem();
        sql.addTables(fromItem.toString());
    }

}
