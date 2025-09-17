//
// Code generated using Llama4 hosted by Oracle Code Assist
// from: citydb-tool/citydb-database-postgres/src/main/java/org/citydb/database/postgres/BoxParser.java
//
// Karin Patenge, Oracle
// September, 2025
//

package org.citydb.database.oracle;

import org.citydb.database.adapter.DatabaseAdapter;
import org.citydb.database.metadata.DatabaseSize;
import org.citydb.sqlbuilder.function.Function;
import org.citydb.sqlbuilder.literal.StringLiteral;
import org.citydb.sqlbuilder.query.Select;
import org.citydb.sqlbuilder.query.SetOperator;
import org.citydb.sqlbuilder.query.Sets;
import org.citydb.sqlbuilder.schema.Column;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.util.PlainText;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EnumMap;
import java.util.Map;

public class StatisticsHelper extends org.citydb.database.util.StatisticsHelper {

    StatisticsHelper(DatabaseAdapter adapter) {
        super(adapter);
    }

    @Override
    public DatabaseSize getDatabaseSize(Connection connection) throws SQLException {
        String databaseSizeId = "database_size";
        Table dbaSegments = Table.of("dba_segments");

        SetOperator select = Sets.unionAll(
                Select.newInstance()
                        .select(StringLiteral.of(databaseSizeId), Function.of("sum", dbaSegments.column("bytes")))
                        .from(dbaSegments),
                Select.newInstance()
                        .select(dbaSegments.column("segment_name"), dbaSegments.column("bytes"))
                        .from(dbaSegments)
                        .where(dbaSegments.column("owner").eq(adapter.getConnectionDetails().getSchema())));

        long databaseSize = 0;
        Map<org.citydb.database.schema.Table, Long> tableSizes = new EnumMap<>(org.citydb.database.schema.Table.class);
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(select.toSql())) {
            while (rs.next()) {
                String identifier = rs.getString(1);
                if (databaseSizeId.equals(identifier)) {
                    databaseSize = rs.getLong(2);
                } else {
                    tableSizes.put(org.citydb.database.schema.Table.of(identifier), rs.getLong(2));
                }
            }
        }

        return DatabaseSize.of(databaseSize, tableSizes);
    }

    @Override
    protected Column getGeometryType(Table geometryData) {
        // Oracle Database does not have a direct equivalent to PostgreSQL's geometry_properties column.
        // You may need to create a custom function or use a different approach.
        throw new UnsupportedOperationException("getGeometryType is not supported in Oracle Database.");
    }
}

/*
  Key Changes
  - Database Size Query: The getDatabaseSize method now uses the dba_segments table to get the database size, which is specific to Oracle Database.
  - Geometry Type Column: The getGeometryType method now throws an UnsupportedOperationException, as Oracle Database does not have a direct equivalent to PostgreSQL's geometry_properties column. You may need to create a custom function or use a different approach.

  Notes
  - The DatabaseAdapter class is assumed to be implemented separately and is not shown in this example.
  - The StatisticsHelper class is used to provide helper methods for getting database statistics.
*/