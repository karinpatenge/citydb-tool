//
// Code generated using Llama4 hosted by Oracle Code Assist
// from: citydb-tool/citydb-database-postgres/src/main/java/org/citydb/database/module-info.java
//
// Karin Patenge, Oracle
// September, 2025
//

module org.citydb.database.oracle {
    requires org.citydb.database;
    requires com.oracle.ojdbc;

    exports org.citydb.database.oracle;

    provides org.citydb.database.adapter.DatabaseAdapter with org.citydb.database.oracle.OracleDatabaseAdapter;
}

/*
  Key Changes
  - Module Name: The module name has been changed from org.citydb.database.postgres to org.citydb.database.oracle to reflect the new database being used.
  - Dependency: The dependency on org.postgresql.jdbc has been replaced with com.oracle.ojdbc to include the Oracle JDBC driver.
  - Service Provider: The service provider has been updated to provide org.citydb.database.oracle.OracleDatabaseAdapter instead of org.citydb.database.postgres.PostgresqlAdapter to match the new database adapter class.

  Notes
  - The com.oracle.ojdbc module is assumed to be available in the module path.
  - The OracleDatabaseAdapter class is assumed to be implemented separately and is not shown in this example.
*/