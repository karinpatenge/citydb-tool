//
// Code generated using Llama4 hosted by Oracle Code Assist
// from: citydb-tool/citydb-database-postgres/src/main/java/org/citydb/database/postgres/BoxParser.java
//
// Karin Patenge, Oracle
// September, 2025
//

package org.citydb.database.oracle;

import org.citydb.core.concurrent.LazyCheckedInitializer;
import org.citydb.core.version.Version;
import org.citydb.database.adapter.DatabaseAdapter;
import org.citydb.database.metadata.DatabaseProperty;
import org.citydb.database.schema.Index;
import org.citydb.database.schema.Sequence;
import org.citydb.database.srs.SpatialReferenceType;
import org.citydb.model.property.RelationType;
import org.citydb.sqlbuilder.common.SqlObject;
import org.citydb.sqlbuilder.function.Function;
import org.citydb.sqlbuilder.literal.BooleanLiteral;
import org.citydb.sqlbuilder.literal.IntegerLiteral;
import org.citydb.sqlbuilder.literal.StringLiteral;
import org.citydb.sqlbuilder.operation.Case;
import org.citydb.sqlbuilder.operation.Not;
import org.citydb.sqlbuilder.query.CommonTableExpression;
import org.citydb.sqlbuilder.query.Select;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.util.PlainText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

public class SchemaAdapter extends org.citydb.database.adapter.SchemaAdapter {
    protected static final String PROPERTY_ORACLE_SPATIAL = "oracle_spatial";

    private final LazyCheckedInitializer<String, IOException> featureHierarchyQuery;
    private final LazyCheckedInitializer<String, IOException> recursiveImplicitGeometryQuery;
    private final OperationHelper operationHelper;
    private final StatisticsHelper statisticsHelper;
    private final TempTableHelper tempTableHelper;

    SchemaAdapter(DatabaseAdapter adapter) {
        super(adapter);
        featureHierarchyQuery = LazyCheckedInitializer.of(this::readFeatureHierarchyQuery);
        recursiveImplicitGeometryQuery = LazyCheckedInitializer.of(this::readRecursiveImplicitGeometryQuery);
        operationHelper = new OperationHelper(this);
        statisticsHelper = new StatisticsHelper(adapter);
        tempTableHelper = new TempTableHelper(adapter);
    }

    // ...

    @Override
    protected String getCityDBVersion() {
        return "select major_version, minor_version, minor_revision, version from citydb_pkg.citydb_version()";
    }

    @Override
    protected String getSchemaExists(String schemaName, Version version) {
        return "select citydb_pkg.schema_exists('" + schemaName + "') from dual";
    }

    @Override
    protected String getDatabaseSrs() {
        return "select srid, srs_name, coord_ref_sys_name, coord_ref_sys_kind, wktext " +
                "from citydb_pkg.db_metadata('" + adapter.getConnectionDetails().getSchema() + "')";
    }

    @Override
    protected String getSpatialReference(int srid) {
        return "select coord_ref_sys_name, coord_ref_sys_kind, wktext " +
                "from citydb_pkg.get_coord_ref_sys_info(" + srid + ")";
    }

    // ...

    @Override
    protected List<DatabaseProperty> getDatabaseProperties(Version version, Connection connection) throws SQLException {
        List<DatabaseProperty> properties = new ArrayList<>();
        properties.add(getDatabaseProperty(PROPERTY_ORACLE_SPATIAL, "Oracle Spatial",
                Select.newInstance().select(StringLiteral.of("supported")), connection));
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("select id, name, value from citydb_pkg.db_properties()")) {
            while (rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);
                if (id != null && name != null) {
                    properties.add(DatabaseProperty.of(id, name, rs.getString(3)));
                }
            }
        }

        return properties;
    }

    // ...
}

/*
  Key Changes
  - Database-specific queries: The queries have been modified to be compatible with Oracle Database.
  - Database properties: The database properties have been modified to include Oracle Spatial.
  - Schema exists query: The schema exists query has been modified to use the citydb_pkg.schema_exists function.

  Notes
  - The SchemaAdapter class is used to provide helper methods for schema-related operations.
  - The OperationHelper, StatisticsHelper, and TempTableHelper classes are assumed to be implemented separately and are not shown in this example.
*/
