/*
// $Id: QueryTest.java,v 1.1 2008-11-10 10:07:54 mpostelnicu Exp $
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 1998-2002 Kana Software, Inc.
// Copyright (C) 2001-2007 Julian Hyde and others
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
//
// Shishir, 08 May, 2007
*/
package mondrian.olap;

import mondrian.test.FoodMartTestCase;
import mondrian.test.TestContext;


public class QueryTest extends FoodMartTestCase {
    private QueryPart[] cellProps={
            new CellProperty("[Value]"),
            new CellProperty("[Formatted_Value]"),
            new CellProperty("[Format_String]")};
    private QueryAxis[] axes = new QueryAxis[0];
    private Formula[] formulas = new Formula[0];
    private Query queryWithCellProps;
    private Query queryWithoutCellProps;

    protected void setUp() throws Exception {
        super.setUp();

        TestContext testContext = getTestContext();
        Connection connection = testContext.getConnection();

        queryWithCellProps = new Query(connection, formulas, axes, "Sales",
                null, cellProps, false, false);
        queryWithoutCellProps = new Query(connection, formulas, axes, "Sales",
                null, new QueryPart[0], false, false);
    }

    public void testHasCellPropertyWhenQueryHasCellProperties() {
        assertTrue(queryWithCellProps.hasCellProperty("Value"));
        assertFalse(queryWithCellProps.hasCellProperty("Language"));
    }
    public void testIsCellPropertyEmpty() {
        assertTrue(queryWithoutCellProps.isCellPropertyEmpty());
    }

}
