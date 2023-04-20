package co.incubyte.db;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

public class Sql {
    private final Collection<String> tables = new LinkedHashSet<>();
    private final Collection<String> joins = new LinkedHashSet<>();
    private String where = "";
    private final Collection<String> orderby = new LinkedHashSet<>();
    private final Collection<String> groupBy = new LinkedHashSet<>();
    private final Collection<String> columns = new LinkedHashSet<>();

    public Collection<String> getTables() {
        return Collections.unmodifiableCollection(tables);
    }

    public void addTables(String tableName) {
        tables.add(tableName);
    }

    public Collection<String> getJoins() {
        return Collections.unmodifiableCollection(joins);
    }

    public void addJoins(String joinedTable) {
        joins.add(joinedTable);
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String whereClause){
        where = whereClause;
    }

    public Collection<String> getOrderBy() {
        return Collections.unmodifiableCollection(orderby);
    }

    public void addOrderBy(String orderByColumns) {
        orderby.add(orderByColumns);
    }

    public Collection<String> getGroupBy() {
        return Collections.unmodifiableCollection(groupBy);
    }

    public void addGroupBy(String groupBy) {
        this.groupBy.add(groupBy);
    }

    public Collection<String> getColumns() {
        return Collections.unmodifiableCollection(columns);
    }

    public void addColumn(String column) {
        this.columns.add(column);
    }
}
