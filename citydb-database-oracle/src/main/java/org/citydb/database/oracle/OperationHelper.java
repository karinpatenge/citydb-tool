//
// Code generated using Llama4 hosted by Oracle Code Assist
// from: citydb-tool/citydb-database-postgres/src/main/java/org/citydb/database/postgres/OperationsHelper.java
//
// Karin Patenge, Oracle
// September, 2025
//

package org.citydb.database.oracle;

import org.citydb.database.adapter.SchemaAdapter;
import org.citydb.sqlbuilder.common.Expression;
import org.citydb.sqlbuilder.function.Function;
import org.citydb.sqlbuilder.literal.ScalarExpression;

public class OperationHelper extends org.citydb.database.util.OperationHelper {

    OperationHelper(SchemaAdapter schemaAdapter) {
        super(schemaAdapter);
    }

    @Override
    public Expression intDivision(ScalarExpression leftOperand, ScalarExpression rightOperand) {
        return leftOperand.divide(rightOperand).truncate();
    }

    @Override
    public Expression lower(ScalarExpression expression) {
        return Function.of("lower", expression);
    }

    @Override
    public Expression toDate(ScalarExpression expression) {
        return Function.of("to_date", expression);
    }

    @Override
    public Expression power(ScalarExpression leftOperand, ScalarExpression rightOperand) {
        return Function.of("power", leftOperand, rightOperand);
    }

    @Override
    public Expression unaccent(ScalarExpression expression) {
        // Oracle Database does not have a direct equivalent to PostgreSQL's unaccent function.
        // You may need to create a custom function or use a different approach.
        throw new UnsupportedOperationException("unaccent is not supported in Oracle Database.");
    }

    @Override
    public Expression upper(ScalarExpression expression) {
        return Function.of("upper", expression);
    }
}

/*
  Key Changes
  - Integer Division: The intDivision method now uses the divide and truncate methods to achieve integer division, as Oracle Database does not have a specific div function.
  - To Date: The toDate method now uses the to_date function, which is the Oracle Database equivalent of PostgreSQL's date function.
  - Unaccent: The unaccent method now throws an UnsupportedOperationException, as Oracle Database does not have a direct equivalent to PostgreSQL's unaccent function. You may need to create a custom function or use a different approach.

  Notes
  - The SchemaAdapter class is assumed to be implemented separately and is not shown in this example.
  - The Function and ScalarExpression classes are part of the SQL builder API and are used to construct SQL expressions.
*/