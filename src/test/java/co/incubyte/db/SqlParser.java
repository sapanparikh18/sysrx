package co.incubyte.db;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

import javax.swing.plaf.nimbus.State;

public class SqlParser {
    public Sql parse(String strSql) throws JSQLParserException {
        Sql sql = new Sql();
        Statement statement = CCJSqlParserUtil.parse(strSql);
        if (statement instanceof Select) {
            Select select = (Select) statement;
            PlainSelect plainSelect = (PlainSelect)  select.getSelectBody();
            FromItem fromItem = plainSelect.getFromItem();
            Alias alias = fromItem.getAlias();
            if (alias != null) {
                sql.addTables(alias.getName());
            } else {
                sql.addTables(fromItem.toString());
            }
        }
        return sql;
    }
}
