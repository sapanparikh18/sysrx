package co.incubyte.db;

import java.util.Collection;
import java.util.HashSet;

public class Sql {
    private Collection<String> tables = new HashSet<>();

    public Collection<String> getTables() {
        return tables;
    }

    public void addTables(String tableName) {
        tables.add(tableName);
    }
}
