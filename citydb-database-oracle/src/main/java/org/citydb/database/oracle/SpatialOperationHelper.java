//
// Code generated using Llama4 hosted by Oracle Code Assist
// from: citydb-tool/citydb-database-postgres/src/main/java/org/citydb/database/postgres/BoxParser.java
//
// Karin Patenge, Oracle
// September, 2025
//

package org.citydb.database.oracle;

import org.citydb.sqlbuilder.function.Cast;
import org.citydb.sqlbuilder.function.Function;
import org.citydb.sqlbuilder.literal.IntegerLiteral;
import org.citydb.sqlbuilder.literal.Placeholder;
import org.citydb.sqlbuilder.literal.ScalarExpression;
import org.citydb.sqlbuilder.literal.StringLiteral;
import org.citydb.sqlbuilder.operation.BinaryComparisonOperation;
import org.citydb.sqlbuilder.operation.BooleanExpression;
import org.citydb.sqlbuilder.operation.Operators;

public class SpatialOperationHelper implements org.citydb.database.util.SpatialOperationHelper {
    private final StringLiteral TRUE = StringLiteral.of("1");

    @Override
    public Function extent(ScalarExpression operand) {
        return Function.of("sdo_mbr", operand);
    }

    @Override
    public Function transform(ScalarExpression operand, int srid) {
        return Function.of("sdo_cs.transform", operand, IntegerLiteral.of(srid));
    }

    @Override
    public BooleanExpression bbox(ScalarExpression leftOperand, ScalarExpression rightOperand) {
        return Function.of("sdo_filter", leftOperand, rightOperand, StringLiteral.of("querytype=WINDOW"));
    }

    @Override
    public BooleanExpression contains(ScalarExpression leftOperand, ScalarExpression rightOperand) {
        return Function.of("sdo_relate", leftOperand, rightOperand, StringLiteral.of("mask=CONTAINS querytype=WINDOW"));
    }

    @Override
    public BooleanExpression crosses(ScalarExpression leftOperand, ScalarExpression rightOperand) {
        // Oracle Database does not have a direct equivalent to PostgreSQL's st_crosses function.
        // You may need to create a custom function or use a different approach.
        throw new UnsupportedOperationException("crosses is not supported in Oracle Database.");
    }

    @Override
    public BooleanExpression disjoint(ScalarExpression leftOperand, ScalarExpression rightOperand) {
        return Function.of("sdo_relate", leftOperand, rightOperand, StringLiteral.of("mask=DISJOINT querytype=WINDOW"));
    }

    @Override
    public BooleanExpression equals(ScalarExpression leftOperand, ScalarExpression rightOperand) {
        return Function.of("sdo_relate", leftOperand, rightOperand, StringLiteral.of("mask=EQUAL querytype=WINDOW"));
    }

    @Override
    public BooleanExpression intersects(ScalarExpression leftOperand, ScalarExpression rightOperand) {
        return Function.of("sdo_relate", leftOperand, rightOperand, StringLiteral.of("mask=ANYINTERACT querytype=WINDOW"));
    }

    @Override
    public BooleanExpression overlaps(ScalarExpression leftOperand, ScalarExpression rightOperand) {
        return Function.of("sdo_relate", leftOperand, rightOperand, StringLiteral.of("mask=OVERLAPBDYDISJOINT+OVERLAPBDYINTERSECT querytype=WINDOW"));
    }

    @Override
    public BooleanExpression touches(ScalarExpression leftOperand, ScalarExpression rightOperand) {
        return Function.of("sdo_relate", leftOperand, rightOperand, StringLiteral.of("mask=TOUCH querytype=WINDOW"));
    }

    @Override
    public BooleanExpression within(ScalarExpression leftOperand, ScalarExpression rightOperand) {
        return Function.of("sdo_relate", leftOperand, rightOperand, StringLiteral.of("mask=INSIDE querytype=WINDOW"));
    }

    @Override
    public BooleanExpression dWithin(ScalarExpression leftOperand, ScalarExpression rightOperand, ScalarExpression distance) {
        return Function.of("sdo_within_distance", leftOperand, rightOperand, StringLiteral.of("distance=" + distance.toString()));
    }

    @Override
    public BooleanExpression beyond(ScalarExpression leftOperand, ScalarExpression rightOperand, ScalarExpression distance) {
        return Operators.not(Function.of("sdo_within_distance", leftOperand, rightOperand, StringLiteral.of("distance=" + distance.toString())));
    }

    private ScalarExpression cast(ScalarExpression expression) {
        // Oracle Database does not require explicit casting to a geometry type.
        return expression;
    }
}

/*
  Key Changes
  - Spatial Functions: The spatial functions have been changed to Oracle Database-specific functions, such as sdo_mbr, sdo_cs.transform, sdo_filter, and sdo_relate.
  - Spatial Operators: The spatial operators have been changed to Oracle Database-specific operators, such as sdo_filter and sdo_relate.
  - Casting: The cast method has been simplified, as Oracle Database does not require explicit casting to a geometry type.

  Notes
  - The SpatialOperationHelper class is used to provide helper methods for spatial operations.
  - Some spatial operations, such as crosses, are not directly supported in Oracle Database and may require custom implementation.
*/
