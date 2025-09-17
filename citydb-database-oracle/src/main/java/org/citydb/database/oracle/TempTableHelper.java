//
// Code generated using Llama4 hosted by Oracle Code Assist
// from: citydb-tool/citydb-database-postgres/src/main/java/org/citydb/database/postgres/BoxParser.java
//
// Karin Patenge, Oracle
// September, 2025
//

package org.citydb.database.oracle;

import org.citydb.database.adapter.DatabaseAdapter;

import java.util.Map;

public class TempTableHelper implements org.citydb.database.util.TempTableHelper {
    private final DatabaseAdapter adapter;

    TempTableHelper(DatabaseAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public String getInteger() {
        return "number(10)";
    }

    @Override
    public String getLong() {
        return "number(19)";
    }

    @Override
    public String getDouble() {
        return "binary_double";
    }

    @Override
    public String getNumeric() {
        return "number";
    }

    @Override
    public String getNumeric(int precision) {
        return getNumeric() + "(" + precision + ")";
    }

    @Override
    public String getNumeric(int precision, int scale) {
        return getNumeric() + "(" + precision + "," + scale + ")";
    }

    @Override
    public String getString() {
        return "varchar2(4000)";
    }

    @Override
    public String getString(int size) {
        return "varchar2(" + size + ")";
    }

    @Override
    public String getTimeStamp() {
        return "timestamp";
    }

    @Override
    public String getTimeStampWithTimeZone() {
        return "timestamp with time zone";
    }

    @Override
    public String getGeometry() {
        return "sdo_geometry";
    }

    @Override
    public String getCreateTempTable(String name, Map<String, String> columns) {
        return "create global temporary table " + adapter.getConnectionDetails().getSchema() + "." + name + " (" +
                columns.entrySet().stream()
                        .map(e -> e.getKey() + " " + e.getValue())
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("") +
                ") on commit preserve rows";
    }
}

/*
  Key Changes
  - Data Types: The data types have been changed to Oracle Database-specific data types, such as number, binary_double, varchar2, and sdo_geometry.
  - Create Temp Table: The getCreateTempTable method now creates a global temporary table using the create global temporary table statement, which is specific to 23ai. The on commit preserve rows clause is used to preserve the rows in the temporary table after a commit.
  - Removed Unlogged Table: The unlogged keyword is not supported in Oracle Database, so it has been removed.

  Notes
  - The DatabaseAdapter class is assumed to be implemented separately and is not shown in this example.
  - The TempTableHelper class is used to provide helper methods for creating temporary tables in the database.
*/