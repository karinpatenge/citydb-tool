//
// Code generated using Llama4 hosted by Oracle Code Assist
// from: citydb-tool/citydb-database-postgres/src/main/java/org/citydb/database/postgres/BoxParser.java
//
// Karin Patenge, Oracle
// September, 2025
//
// Documentation:
// https://docs.oracle.com/en/database/oracle/oracle-database/23/jajdb/
//

package org.citydb.database.oracle;

import oracle.jdbc.OracleDriver;

import org.citydb.database.adapter.DatabaseAdapter;
import org.citydb.database.adapter.DatabaseType;

import java.util.Properties;

@DatabaseType(name = OracleDatabaseAdapter.DATABASE_NAME)
public class OracleDatabaseAdapter extends DatabaseAdapter {
    public static final String DATABASE_NAME = "Oracle";

    @Override
    protected SchemaAdapter createSchemaAdapter(DatabaseAdapter adapter) {
        return new SchemaAdapter(adapter);
    }

    @Override
    protected GeometryAdapter createGeometryAdapter(DatabaseAdapter adapter) {
        return new GeometryAdapter(adapter);
    }

    @Override
    public Class<?> getDriverClass() {
        return OracleDriver.class;
    }

    @Override
    public int getDefaultPort() {
        return 1521;
    }

    @Override
    public String getConnectionString(String host, int port, String database) {
        return "jdbc:oracle:thin:@//" + host + ":" + port + "/" + database;
    }

    @Override
    public void setDefaultConnectionProperties(Properties properties) {
        properties.setProperty("oracle.net.CONNECT_TIMEOUT", "5000");
        properties.setProperty("oracle.jdbc.ReadTimeout", "5000");
        properties.setProperty("oracle.jdbc.fetchSize", "1000");
    }
}

/*
  Key Changes
  - Database Name: The database name has been changed from "PostgreSQL" to "Oracle".
  - Driver Class: The driver class has been changed from org.postgresql.Driver to oracle.jdbc.OracleDriver.
  - Default Port: The default port has been changed from 5432 to 1521, which is the default port for Oracle Database.
  - Connection String: The connection string has been changed to the Oracle Database JDBC connection string format.
  - Default Connection Properties: The default connection properties have been changed to Oracle Database-specific properties, including oracle.net.CONNECT_TIMEOUT, oracle.jdbc.ReadTimeout, and oracle.jdbc.fetchSize.

  Notes
  - The SchemaAdapter and GeometryAdapter classes are assumed to be implemented separately and are not shown in this example.
  - The DatabaseType annotation is used to specify the database name.
  - The OracleDriver class is part of the Oracle JDBC driver and is used to connect to Oracle Database.
*/