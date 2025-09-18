//
// Code generated using Llama4 hosted by Oracle Code Assist
// from: citydb-tool/citydb-database-postgres/src/main/java/org/citydb/database/postgres/GeometryAdapter.java
//
// Karin Patenge, Oracle
// September, 2025
//
// Documentation:
// https://docs.oracle.com/en/database/oracle/oracle-database/23/spajv/oracle/spatial/geometry/package-summary.html
// https://docs.oracle.com/en/database/oracle/oracle-database/23/spajv/oracle/spatial/util/package-summary.html
//

package org.citydb.database.oracle;

import oracle.spatial.geometry.JGeometry;
import oracle.spatial.util.GeoData;  // ???

import org.citydb.database.adapter.DatabaseAdapter;
import org.citydb.database.geometry.GeometryException;
import org.citydb.database.geometry.WKBParser;
import org.citydb.database.geometry.WKBWriter;
import org.citydb.database.geometry.WKTWriter;
import org.citydb.model.geometry.Envelope;
import org.citydb.model.geometry.Geometry;

import java.sql.*;

public class GeometryAdapter extends org.citydb.database.adapter.GeometryAdapter {
    private final WKBParser parser = new WKBParser();
    private final WKBWriter writer = new WKBWriter().includeSRID(true);
    private final WKTWriter textWriter = new WKTWriter().includeSRID(true);
    private final SpatialOperationHelper spatialOperationHelper = new SpatialOperationHelper();

    GeometryAdapter(DatabaseAdapter adapter) {
        super(adapter);
    }

    @Override
    public int getGeometrySqlType() {
        return oracle.jdbc.OracleTypes.STRUCT;
    }

    @Override
    public String getGeometryTypeName() {
        return "MDSYS.SDO_GEOMETRY";
    }

    @Override
    public Geometry<?> getGeometry(Object geometryObject) throws GeometryException {
        if (geometryObject instanceof STRUCT struct) {
            try {
                JGeometry geometry = JGeometry.load(struct);
                return parser.parse(geometry.toSTRUCT(adapter.getConnectionDetails().getSchema()).getBytes());
            } catch (SQLException e) {
                throw new GeometryException("Failed to parse geometry.", e);
            }
        } else {
            return parser.parse(geometryObject);
        }
    }

    @Override
    public Envelope getEnvelope(Object geometryObject) throws GeometryException {
        if (geometryObject instanceof STRUCT struct) {
            try {
                JGeometry geometry = JGeometry.load(struct);
                double[] min = geometry.getMBR().getLowerLeftPoint();
                double[] max = geometry.getMBR().getUpperRightPoint();
                return Envelope.of(min[0], min[1], min[2], max[0], max[1], max[2]);
            } catch (SQLException e) {
                throw new GeometryException("Failed to get envelope.", e);
            }
        } else {
            throw new GeometryException("Unsupported geometry object type.");
        }
    }

    @Override
    public Object getGeometry(Geometry<?> geometry, boolean force3D) {
        try {
            byte[] wkb = writer.write(geometry, force3D);
            return GeoData.toJGeometry(wkb).toSTRUCT(adapter.getConnectionDetails().getSchema());
        } catch (GeometryException | SQLException e) {
            throw new RuntimeException("Failed to create geometry.", e);
        }
    }

    @Override
    public String getAsText(Geometry<?> geometry) throws GeometryException {
        return textWriter.write(geometry);
    }

    @Override
    public boolean hasImplicitGeometries(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("select 1 from " + adapter.getConnectionDetails().getSchema() +
                     ".IMPLICIT_GEOMETRY WHERE ROWNUM < 2")) {
            return rs.next();
        }
    }

    @Override
    public SpatialOperationHelper getSpatialOperationHelper() {
        return spatialOperationHelper;
    }
}


/*
  Key Changes
  - Geometry SQL Type: The getGeometrySqlType method now returns oracle.jdbc.OracleTypes.STRUCT to match the Oracle Database geometry type.
  - Geometry Type Name: The getGeometryTypeName method now returns "MDSYS.SDO_GEOMETRY" to match the Oracle Database geometry type name.
  - Geometry Parsing: The getGeometry method now uses JGeometry.load to parse the Oracle Database geometry object into a JGeometry object, which is then converted to a Geometry object using the WKBParser.
  - Envelope Calculation: The getEnvelope method now uses the JGeometry object to calculate the envelope.
  - Geometry Creation: The getGeometry method now uses GeoData.toJGeometry to create a JGeometry object from the Geometry object, which is then converted to an Oracle Database geometry object using toSTRUCT.
  - Implicit Geometries Check: The hasImplicitGeometries method now uses the Oracle Database-specific query to check for implicit geometries.

  Notes
  - The SpatialOperationHelper class is assumed to be implemented separately and is not shown in this example.
  - The WKBParser, WKBWriter, and WKTWriter classes are assumed to be implemented separately and are not shown in this example.
  - The Oracle Database JDBC driver and Spatial Java API are required to be included in the project's classpath.
*/