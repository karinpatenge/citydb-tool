//
// Code generated using Llama4 hosted by Oracle Code Assist
// from: citydb-tool/citydb-database-postgres/src/main/java/org/citydb/database/postgres/BoxParser.java
//
// Karin Patenge, Oracle
// September, 2025
//
// Documentation:
// https://docs.oracle.com/en/database/oracle/oracle-database/23/spajv/oracle/spatial/geometry/package-summary.html
//

package org.citydb.database.oracle;

import oracle.spatial.geometry.JGeometry;

import org.citydb.database.geometry.GeometryException;
import org.citydb.model.geometry.Coordinate;
import org.citydb.model.geometry.Envelope;

public class BoxParser {

    public Envelope parse(String text) throws GeometryException {
        return text != null ? readBox(text) : null;
    }

    private Envelope readBox(String text) throws GeometryException {
        try {
            JGeometry geometry = JGeometry.load(text);
            double[] min = geometry.getMBR().getLowerLeftPoint();
            double[] max = geometry.getMBR().getUpperRightPoint();

            Coordinate lowerCorner = Coordinate.of(min[0], min[1], min.length == 3 ? min[2] : Double.NaN);
            Coordinate upperCorner = Coordinate.of(max[0], max[1], max.length == 3 ? max[2] : Double.NaN);

            Envelope envelope = Envelope.of(lowerCorner, upperCorner);
            envelope.setSRID(geometry.getSRID());

            return envelope;
        } catch (Exception e) {
            throw new GeometryException("Failed to parse box geometry.", e);
        }
    }
}

/*
  Key Changes
  - Simplified Parsing: The readBox method now uses JGeometry.load to directly parse the Oracle Database geometry string into a JGeometry object.
  - Envelope Calculation: The readBox method now uses the JGeometry object to calculate the envelope by getting the minimum bounding rectangle (MBR) and creating Coordinate objects for the lower and upper corners.
  - SRID Handling: The SRID is automatically retrieved from the JGeometry object and set on the Envelope object.
  - Error Handling: The readBox method now catches any exceptions that occur during parsing and throws a GeometryException with a meaningful error message.

  Notes
  - The JGeometry class is part of the Oracle Spatial Java API and is used to represent and manipulate Oracle Database geometries.
  - The Envelope and Coordinate classes are assumed to be implemented separately and are not shown in this example.
*/