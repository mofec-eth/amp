/*
// $Id: GroupingSetQueryTest.java,v 1.1 2008-11-10 10:07:24 mpostelnicu Exp $
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 2004-2008 Julian Hyde and others
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
*/
package mondrian.rolap;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import mondrian.rolap.agg.CellRequest;
import mondrian.rolap.sql.SqlQuery;
import mondrian.olap.MondrianProperties;
import mondrian.olap.Util;
import mondrian.test.SqlPattern;

/**
 * Test support for generating SQL queries with the <code>GROUPING SETS</code>
 * construct, if the DBMS supports it.
 *
 * @author Thiyagu
 * @version $Id: GroupingSetQueryTest.java,v 1.1 2008-11-10 10:07:24 mpostelnicu Exp $
 * @since 08-Jun-2007
 */
public class GroupingSetQueryTest extends BatchTestCase {

    private MondrianProperties prop = MondrianProperties.instance();
    
    private static final String cubeNameSales2 = "Sales 2";
    private static final String measureStoreSales = "[Measures].[Store Sales]";
    private static final String fieldNameMaritalStatus = "marital_status";
    private static final String measureCustomerCount = "[Measures].[Customer Count]";

    private boolean useGroupingSets;
    private boolean formattedSql;
    private String origWarnIfNoPatternForDialect;
    private static final Set<SqlPattern.Dialect> ORACLE_TERADATA =
        Util.enumSetOf(SqlPattern.Dialect.ORACLE, SqlPattern.Dialect.TERADATA);

    protected void setUp() throws Exception {
        super.setUp();
        getTestContext().clearConnection();
        useGroupingSets = prop.EnableGroupingSets.get();
        formattedSql = prop.GenerateFormattedSql.get();
        origWarnIfNoPatternForDialect = prop.WarnIfNoPatternForDialect.get();
        
        prop.GenerateFormattedSql.set(false);
        
        /*
         * This test warns of missing sql patterns for
         * 
         * ACCESS
         * ORACLE
         * 
         */
        final SqlQuery.Dialect dialect = getTestContext().getDialect();
        if (prop.WarnIfNoPatternForDialect.get().equals("ANY") ||
            (dialect.isAccess() || dialect.isOracle())) {
            prop.WarnIfNoPatternForDialect.set(
                SqlPattern.Dialect.get(dialect).toString());
        } else {
            /*
             * Do not warn unless the dialect is "ACCESS" or "ORACLE", or
             * if the test chooses to warn regardless of the dialect.
             */
            prop.WarnIfNoPatternForDialect.set("NONE");
        }
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        prop.EnableGroupingSets.set(useGroupingSets);
        prop.GenerateFormattedSql.set(formattedSql);
        prop.WarnIfNoPatternForDialect.set(origWarnIfNoPatternForDialect);
    }

    public void testGroupingSetForSingleColumnConstraint() {
        prop.DisableCaching.setString("false");

        CellRequest request1 = createRequest(
            cubeNameSales2, measureUnitSales, tableCustomer, fieldGender, "M");

        CellRequest request2 = createRequest(
            cubeNameSales2, measureUnitSales, tableCustomer, fieldGender, "F");

        CellRequest request3 = createRequest(
            cubeNameSales2, measureUnitSales, null, "", "");

        SqlPattern[] patternsWithGsets = {
            new SqlPattern(
                ORACLE_TERADATA,
                "select \"customer\".\"gender\" as \"c0\", sum(\"sales_fact_1997\".\"unit_sales\") as \"m0\", " +
                "grouping(\"customer\".\"gender\") as \"g0\" " +
                "from \"customer\" =as= \"customer\", \"sales_fact_1997\" =as= \"sales_fact_1997\" " +
                "where \"sales_fact_1997\".\"customer_id\" = \"customer\".\"customer_id\" " +
                "group by grouping sets ((\"customer\".\"gender\"),())",
            26)
        };

        // If aggregates are enabled, mondrian should use them. Results should
        // be the same with or without grouping sets enabled.
        SqlPattern[] patternsWithAggs = {
            new SqlPattern(
                ORACLE_TERADATA,
                "select sum(\"agg_c_10_sales_fact_1997\".\"unit_sales\") as \"m0\""
                    + " from \"agg_c_10_sales_fact_1997\" \"agg_c_10_sales_fact_1997\"",
                null),
            new SqlPattern(
                ORACLE_TERADATA,
                "select \"agg_g_ms_pcat_sales_fact_1997\".\"gender\" as \"c0\","
                    + " sum(\"agg_g_ms_pcat_sales_fact_1997\".\"unit_sales\") as \"m0\" "
                    + "from \"agg_g_ms_pcat_sales_fact_1997\" \"agg_g_ms_pcat_sales_fact_1997\" "
                    + "group by \"agg_g_ms_pcat_sales_fact_1997\".\"gender\"",
                null)
        };

        SqlPattern[] patternsWithoutGsets = {
            new SqlPattern(
                SqlPattern.Dialect.ACCESS,
                "select \"customer\".\"gender\" as \"c0\", sum(\"sales_fact_1997\".\"unit_sales\") as \"m0\" " +
                    "from \"customer\" as \"customer\", \"sales_fact_1997\" as \"sales_fact_1997\" " +
                    "where \"sales_fact_1997\".\"customer_id\" = \"customer\".\"customer_id\" " +
                    "group by \"customer\".\"gender\"", 26),
            new SqlPattern(
                ORACLE_TERADATA,
                "select \"customer\".\"gender\" as \"c0\", sum(\"sales_fact_1997\".\"unit_sales\") as \"m0\" " +
                    "from \"customer\" =as= \"customer\", \"sales_fact_1997\" =as= \"sales_fact_1997\" " +
                    "where \"sales_fact_1997\".\"customer_id\" = \"customer\".\"customer_id\" " +
                    "group by \"customer\".\"gender\"", 26)
        };

        prop.EnableGroupingSets.set(true);

        if (prop.ReadAggregates.get() && prop.UseAggregates.get()) {
            assertRequestSql(
                new CellRequest[] {request3, request1, request2},
                patternsWithAggs);
        } else {
            assertRequestSql(
                new CellRequest[] {request3, request1, request2},
                patternsWithGsets);
        }

        prop.EnableGroupingSets.set(false);

        if (prop.ReadAggregates.get() && prop.UseAggregates.get()) {
            assertRequestSql(
                new CellRequest[] {request3, request1, request2},
                patternsWithAggs);
        } else {
            assertRequestSql(
                new CellRequest[] {request3, request1, request2},
                patternsWithoutGsets);
        }
    }
    public void testNotUsingGroupingSetWhenGroupUsesDifferentAggregateTable() {
        if (!(prop.UseAggregates.get() &&
            prop.ReadAggregates.get())) {
            return;
        }

        CellRequest request1 = createRequest(cubeNameSales,
            measureUnitSales, tableCustomer, fieldGender, "M");

        CellRequest request2 = createRequest(cubeNameSales,
            measureUnitSales, tableCustomer, fieldGender, "F");

        CellRequest request3 = createRequest(cubeNameSales,
            measureUnitSales, null, "", "");

        prop.EnableGroupingSets.set(true);

        SqlPattern[] patternsWithoutGsets = {
            new SqlPattern(
                SqlPattern.Dialect.ACCESS,
                "select \"agg_g_ms_pcat_sales_fact_1997\".\"gender\" as \"c0\", " +
                    "sum(\"agg_g_ms_pcat_sales_fact_1997\".\"unit_sales\") as \"m0\" " +
                    "from \"agg_g_ms_pcat_sales_fact_1997\" as \"agg_g_ms_pcat_sales_fact_1997\" " +
                    "group by \"agg_g_ms_pcat_sales_fact_1997\".\"gender\"",
                26),
            new SqlPattern(
                ORACLE_TERADATA,
                "select \"agg_g_ms_pcat_sales_fact_1997\".\"gender\" as \"c0\", " +
                    "sum(\"agg_g_ms_pcat_sales_fact_1997\".\"unit_sales\") as \"m0\" " +
                    "from \"agg_g_ms_pcat_sales_fact_1997\" \"agg_g_ms_pcat_sales_fact_1997\" " +
                    "group by \"agg_g_ms_pcat_sales_fact_1997\".\"gender\"",
                26)
        };
        assertRequestSql(
            new CellRequest[] {request3, request1, request2},
            patternsWithoutGsets);
    }

    public void testNotUsingGroupingSet() {
        if (prop.ReadAggregates.get() && prop.UseAggregates.get()) {
            return;
        }
        prop.EnableGroupingSets.set(true);
        CellRequest request1 = createRequest(cubeNameSales2,
            measureUnitSales, tableCustomer, fieldGender, "M");

        CellRequest request2 = createRequest(cubeNameSales2,
            measureUnitSales, tableCustomer, fieldGender, "F");

        SqlPattern[] patternsWithGsets = {
            new SqlPattern(
                ORACLE_TERADATA,
                "select \"customer\".\"gender\" as \"c0\", sum(\"sales_fact_1997\".\"unit_sales\") as \"m0\" " +
                    "from \"customer\" =as= \"customer\", \"sales_fact_1997\" =as= \"sales_fact_1997\" " +
                    "where \"sales_fact_1997\".\"customer_id\" = \"customer\".\"customer_id\" " +
                    "group by \"customer\".\"gender\"", 72)
            };
        assertRequestSql(
            new CellRequest[] {request1, request2},
            patternsWithGsets);

        prop.EnableGroupingSets.set(false);

        SqlPattern[] patternsWithoutGsets = {
            new SqlPattern(
                SqlPattern.Dialect.ACCESS,
                "select \"customer\".\"gender\" as \"c0\", sum(\"sales_fact_1997\".\"unit_sales\") as \"m0\" " +
                    "from \"customer\" as \"customer\", \"sales_fact_1997\" as \"sales_fact_1997\" " +
                    "where \"sales_fact_1997\".\"customer_id\" = \"customer\".\"customer_id\" " +
                    "group by \"customer\".\"gender\"", 72),
            new SqlPattern(
                ORACLE_TERADATA,
                "select \"customer\".\"gender\" as \"c0\", sum(\"sales_fact_1997\".\"unit_sales\") as \"m0\" " +
                    "from \"customer\" =as= \"customer\", \"sales_fact_1997\" =as= \"sales_fact_1997\" " +
                    "where \"sales_fact_1997\".\"customer_id\" = \"customer\".\"customer_id\" " +
                    "group by \"customer\".\"gender\"", 72)
        };
        assertRequestSql(
            new CellRequest[] {request1, request2},
            patternsWithoutGsets);
    }

    public void testGroupingSetForMultipleMeasureAndSingleConstraint() {
        if (prop.ReadAggregates.get() && prop.UseAggregates.get()) {
            return;
        }
        prop.EnableGroupingSets.set(true);

        CellRequest request1 = createRequest(cubeNameSales2,
            measureUnitSales, tableCustomer, fieldGender, "M");
        CellRequest request2 = createRequest(cubeNameSales2,
            measureUnitSales, tableCustomer, fieldGender, "F");
        CellRequest request3 = createRequest(cubeNameSales2,
            measureUnitSales, null, "", "");
        CellRequest request4 = createRequest(cubeNameSales2,
            measureStoreSales, tableCustomer, fieldGender, "M");
        CellRequest request5 = createRequest(cubeNameSales2,
            measureStoreSales, tableCustomer, fieldGender, "F");
        CellRequest request6 = createRequest(cubeNameSales2,
            measureStoreSales, null, "", "");

        SqlPattern[] patternsWithGsets = {
            new SqlPattern(
                ORACLE_TERADATA,
                "select \"customer\".\"gender\" as \"c0\", sum(\"sales_fact_1997\".\"unit_sales\") as \"m0\", " +
                    "sum(\"sales_fact_1997\".\"store_sales\") as \"m1\", grouping(\"customer\".\"gender\") as \"g0\" " +
                    "from \"customer\" =as= \"customer\", \"sales_fact_1997\" =as= \"sales_fact_1997\" " +
                    "where \"sales_fact_1997\".\"customer_id\" = \"customer\".\"customer_id\" " +
                    "group by grouping sets ((\"customer\".\"gender\"),())",
                26)
        };
        assertRequestSql(
            new CellRequest[] {
                request1, request2, request3, request4, request5, request6},
            patternsWithGsets);

        prop.EnableGroupingSets.set(false);

        SqlPattern[] patternsWithoutGsets = {
            new SqlPattern(
                SqlPattern.Dialect.ACCESS,
                "select \"customer\".\"gender\" as \"c0\", sum(\"sales_fact_1997\".\"unit_sales\") as \"m0\", " +
                    "sum(\"sales_fact_1997\".\"store_sales\") as \"m1\" " +
                    "from \"customer\" as \"customer\", \"sales_fact_1997\" as \"sales_fact_1997\" " +
                    "where \"sales_fact_1997\".\"customer_id\" = \"customer\".\"customer_id\" " +
                    "group by \"customer\".\"gender\"", 26),
            new SqlPattern(
                ORACLE_TERADATA,
                "select \"customer\".\"gender\" as \"c0\", sum(\"sales_fact_1997\".\"unit_sales\") as \"m0\", " +
                    "sum(\"sales_fact_1997\".\"store_sales\") as \"m1\" " +
                    "from \"customer\" =as= \"customer\", \"sales_fact_1997\" =as= \"sales_fact_1997\" " +
                    "where \"sales_fact_1997\".\"customer_id\" = \"customer\".\"customer_id\" " +
                    "group by \"customer\".\"gender\"", 26)
        };
        assertRequestSql(
            new CellRequest[] {
                request1, request2, request3, request4, request5, request6},
            patternsWithoutGsets);
    }

    public void testGroupingSetForASummaryCanBeGroupedWith2DetailBatch() {
        if (prop.ReadAggregates.get() && prop.UseAggregates.get()) {
            return;
        }        
        prop.EnableGroupingSets.set(true);
        CellRequest request1 = createRequest(cubeNameSales2,
            measureUnitSales, tableCustomer, fieldGender, "M");
        CellRequest request2 = createRequest(cubeNameSales2,
            measureUnitSales, tableCustomer, fieldGender, "F");
        CellRequest request3 = createRequest(cubeNameSales2,
            measureUnitSales, null, "", "");
        CellRequest request4 = createRequest(cubeNameSales2,
            measureUnitSales, tableCustomer, fieldNameMaritalStatus, "M");
        CellRequest request5 = createRequest(cubeNameSales2,
            measureUnitSales, tableCustomer, fieldNameMaritalStatus, "S");
        CellRequest request6 = createRequest(cubeNameSales2,
            measureUnitSales, null, "", "");

        SqlPattern[] patternWithGsets = {
            new SqlPattern(
                ORACLE_TERADATA,
                "select \"customer\".\"gender\" as \"c0\", sum(\"sales_fact_1997\".\"unit_sales\") as \"m0\", " +
                    "grouping(\"customer\".\"gender\") as \"g0\" " +
                    "from \"customer\" =as= \"customer\", \"sales_fact_1997\" =as= \"sales_fact_1997\" " +
                    "where \"sales_fact_1997\".\"customer_id\" = \"customer\".\"customer_id\" " +
                    "group by grouping sets ((\"customer\".\"gender\"),())",
                26),

            new SqlPattern(
                ORACLE_TERADATA,
                "select \"customer\".\"marital_status\" as \"c0\", sum(\"sales_fact_1997\".\"unit_sales\") as \"m0\" " +
                    "from \"customer\" =as= \"customer\", \"sales_fact_1997\" =as= \"sales_fact_1997\" " +
                    "where \"sales_fact_1997\".\"customer_id\" = \"customer\".\"customer_id\" " +
                    "group by \"customer\".\"marital_status\"",
                26),
            };

        assertRequestSql(
            new CellRequest[] {
                request1, request2, request3, request4, request5, request6},
            patternWithGsets);

        prop.EnableGroupingSets.set(false);

        SqlPattern[] patternWithoutGsets = {
            new SqlPattern(
                SqlPattern.Dialect.ACCESS,
                "select sum(\"sales_fact_1997\".\"unit_sales\") as \"m0\" " +
                    "from \"sales_fact_1997\" as \"sales_fact_1997\"",
                40),
            new SqlPattern(
                ORACLE_TERADATA,
                "select sum(\"sales_fact_1997\".\"unit_sales\") as \"m0\" " +
                    "from \"sales_fact_1997\" =as= \"sales_fact_1997\"",
                40)
        };

        assertRequestSql(
            new CellRequest[] {
                request1, request2, request3, request4, request5, request6},
            patternWithoutGsets);
    }

    public void testGroupingSetForMultipleColumnConstraint() {
        if (prop.ReadAggregates.get() && prop.UseAggregates.get()) {
            return;
        }
        prop.EnableGroupingSets.set(true);
        CellRequest request1 = createRequest(cubeNameSales2,
            measureUnitSales, new String[]{tableCustomer, tableTime},
            new String[]{fieldGender, fieldYear},
            new String[]{"M", "1997"});

        CellRequest request2 = createRequest(cubeNameSales2,
            measureUnitSales, new String[]{tableCustomer, tableTime},
            new String[]{fieldGender, fieldYear},
            new String[]{"F", "1997"});

        CellRequest request3 = createRequest(cubeNameSales2,
            measureUnitSales, tableTime, fieldYear, "1997");

        SqlPattern[] patternsWithGsets = {
            new SqlPattern(
                ORACLE_TERADATA,
                "select \"time_by_day\".\"the_year\" as \"c0\", \"customer\".\"gender\" as \"c1\", " +
                "sum(\"sales_fact_1997\".\"unit_sales\") as \"m0\", grouping(\"customer\".\"gender\") as \"g0\" " +
                "from \"time_by_day\" =as= \"time_by_day\", \"sales_fact_1997\" =as= \"sales_fact_1997\", \"customer\" =as= \"customer\" " +
                "where \"sales_fact_1997\".\"time_id\" = \"time_by_day\".\"time_id\" and \"time_by_day\".\"the_year\" = 1997 " +
                "and \"sales_fact_1997\".\"customer_id\" = \"customer\".\"customer_id\" " +
                "group by grouping sets ((\"time_by_day\".\"the_year\",\"customer\".\"gender\"),(\"time_by_day\".\"the_year\"))",
            150)
        };

        // Sometimes this query causes Oracle 10.2 XE to give
        //   ORA-12516, TNS:listener could not find available handler with
        //   matching protocol stack
        //
        // You need to configure Oracle:
        //  $ su - oracle
        //  $ sqlplus / as sysdba
        //  SQL> ALTER SYSTEM SET sessions=320 SCOPE=SPFILE;
        //  SQL> SHUTDOWN
        assertRequestSql(
            new CellRequest[] {request3, request1, request2},
            patternsWithGsets);

        prop.EnableGroupingSets.set(false);

        SqlPattern[] patternsWithoutGsets = {
            new SqlPattern(
                SqlPattern.Dialect.ACCESS,
                "select \"time_by_day\".\"the_year\" as \"c0\", \"customer\".\"gender\" as \"c1\", " +
                    "sum(\"sales_fact_1997\".\"unit_sales\") as \"m0\" " +
                    "from \"time_by_day\" as \"time_by_day\", \"sales_fact_1997\" as \"sales_fact_1997\", " +
                    "\"customer\" as \"customer\" " +
                    "where \"sales_fact_1997\".\"time_id\" = \"time_by_day\".\"time_id\" and " +
                    "\"time_by_day\".\"the_year\" = 1997 and " +
                    "\"sales_fact_1997\".\"customer_id\" = \"customer\".\"customer_id\" " +
                    "group by \"time_by_day\".\"the_year\", \"customer\".\"gender\"",
                50),
            new SqlPattern(
                ORACLE_TERADATA,
                    "select \"time_by_day\".\"the_year\" as \"c0\", \"customer\".\"gender\" as \"c1\", " +
                        "sum(\"sales_fact_1997\".\"unit_sales\") as \"m0\" " +
                        "from \"time_by_day\" =as= \"time_by_day\", \"sales_fact_1997\" =as= \"sales_fact_1997\", " +
                        "\"customer\" =as= \"customer\" " +
                        "where \"sales_fact_1997\".\"time_id\" = \"time_by_day\".\"time_id\" and " +
                        "\"time_by_day\".\"the_year\" = 1997 " +
                        "and \"sales_fact_1997\".\"customer_id\" = \"customer\".\"customer_id\" " +
                        "group by \"time_by_day\".\"the_year\", \"customer\".\"gender\"",
                    50)
            };
        assertRequestSql(
            new CellRequest[]{request3, request1, request2},
            patternsWithoutGsets);
    }

    public void testGroupingSetForMultipleColumnConstraintAndCompoundConstraint() {
        if (prop.ReadAggregates.get() && prop.UseAggregates.get()) {
            return;
        }
        List<String[]> compoundMembers = new ArrayList<String[]>();
        compoundMembers.add(new String[] {"USA", "OR"});
        compoundMembers.add(new String[] {"CANADA", "BC"});
        CellRequestConstraint constraint =
            makeConstraintCountryState(compoundMembers);

        CellRequest request1 = createRequest(cubeNameSales2,
            measureCustomerCount, new String[]{tableCustomer, tableTime},
            new String[]{fieldGender, fieldYear},
            new String[]{"M", "1997"}, constraint);

        CellRequest request2 = createRequest(cubeNameSales2,
            measureCustomerCount, new String[]{tableCustomer, tableTime},
            new String[]{fieldGender, fieldYear},
            new String[]{"F", "1997"}, constraint);

        CellRequest request3 = createRequest(cubeNameSales2,
            measureCustomerCount, tableTime, fieldYear, "1997", constraint);

        String sqlWithGS =
            "select \"time_by_day\".\"the_year\" as \"c0\", " +
            "\"customer\".\"gender\" as \"c1\", " +
            "count(distinct \"sales_fact_1997\".\"customer_id\") as \"m0\", " +
            "grouping(\"customer\".\"gender\") as \"g0\" " +
            "from \"time_by_day\" =as= \"time_by_day\", " +
            "\"sales_fact_1997\" =as= \"sales_fact_1997\", \"customer\" =as= \"customer\", " +
            "\"store\" =as= \"store\" " +
            "where \"sales_fact_1997\".\"time_id\" = \"time_by_day\".\"time_id\" " +
            "and \"time_by_day\".\"the_year\" = 1997 and \"sales_fact_1997\".\"customer_id\" = \"customer\".\"customer_id\" " +
            "and \"sales_fact_1997\".\"store_id\" = \"store\".\"store_id\" and ((\"store\".\"store_country\" = 'USA' " +
            "and \"store\".\"store_state\" = 'OR') or " +
            "(\"store\".\"store_country\" = 'CANADA' and \"store\".\"store_state\" = 'BC')) " +
            "group by grouping sets ((\"time_by_day\".\"the_year\",\"customer\".\"gender\"),(\"time_by_day\".\"the_year\"))";
        String sqlWithoutGS =
            "select \"time_by_day\".\"the_year\" as \"c0\", \"customer\".\"gender\" as \"c1\", " +
            "count(distinct \"sales_fact_1997\".\"customer_id\") as \"m0\" from \"time_by_day\" =as= \"time_by_day\", " +
            "\"sales_fact_1997\" =as= \"sales_fact_1997\", \"customer\" =as= \"customer\", \"store\" =as= \"store\" " +
            "where \"sales_fact_1997\".\"time_id\" = \"time_by_day\".\"time_id\" and \"time_by_day\".\"the_year\" = 1997 " +
            "and \"sales_fact_1997\".\"customer_id\" = \"customer\".\"customer_id\" and " +
            "\"sales_fact_1997\".\"store_id\" = \"store\".\"store_id\" and " +
            "((\"store\".\"store_country\" = 'USA' and \"store\".\"store_state\" = 'OR') or " +
            "(\"store\".\"store_country\" = 'CANADA' and \"store\".\"store_state\" = 'BC')) " +
            "group by \"time_by_day\".\"the_year\", \"customer\".\"gender\"";

        SqlPattern[] patternsGSDisabled = {
            new SqlPattern(ORACLE_TERADATA, sqlWithoutGS, sqlWithoutGS)
        };

        SqlPattern[] patternsGSEnabled = {
            new SqlPattern(ORACLE_TERADATA, sqlWithGS, sqlWithGS)
        };

        prop.EnableGroupingSets.set(true);

        assertRequestSql(
            new CellRequest[] {request3, request1, request2}, patternsGSEnabled);

        prop.EnableGroupingSets.set(false);

        assertRequestSql(
            new CellRequest[]{request3, request1, request2}, patternsGSDisabled);
    }

    public void testSQLForTotalOnCJofMembersWithDistinctCount() {

        prop.EnableGroupingSets.set(true);
        String mdxQuery = "WITH \n" +
            "SET [COG_OQP_INT_s2] AS 'CROSSJOIN(" +
            "{[Store].MEMBERS}, " +
            "{{[Gender].MEMBERS}, " +
            "{([Gender].[COG_OQP_USR_Aggregate(Gender)])}})' \n" +
            "SET [COG_OQP_INT_s1] AS 'CROSSJOIN({[Store].MEMBERS}, {[Gender].MEMBERS})' \n" +
            "\n" +
            "MEMBER [Store].[COG_OQP_USR_Aggregate(Store)] AS '\n" +
            "AGGREGATE({COG_OQP_INT_s1})', SOLVE_ORDER = 4 \n" +
            "\n" +
            "MEMBER [Gender].[COG_OQP_USR_Aggregate(Gender)] AS '\n" +
            "AGGREGATE({[Gender].DEFAULTMEMBER})', SOLVE_ORDER = 8 \n" +
            "\n" +
            "\n" +
            "SELECT {[Measures].[Customer Count]} ON AXIS(0), \n" +
            "{[COG_OQP_INT_s2], HEAD({([Store].[COG_OQP_USR_Aggregate(Store)], [Gender].DEFAULTMEMBER)}, " +
            "IIF(COUNT([COG_OQP_INT_s1], INCLUDEEMPTY) > 0, 1, 0))} ON AXIS(1) \n" +
            "FROM [sales]";

        String oraTeraSql = "select \"store\".\"store_country\" as \"c0\", " +
            "\"time_by_day\".\"the_year\" " +
            "as \"c1\", \"customer\".\"gender\" as \"c2\", " +
            "count(distinct \"sales_fact_1997\".\"customer_id\") as \"m0\", " +
            "grouping(\"customer\".\"gender\") as \"g0\", " +
            "grouping(\"store\".\"store_country\") as \"g1\" " +
            "from \"store\" =as= \"store\", " +
            "\"sales_fact_1997\" =as= \"sales_fact_1997\", " +
            "\"time_by_day\" =as= \"time_by_day\", " +
            "\"customer\" =as= \"customer\" " +
            "where \"sales_fact_1997\".\"store_id\" = \"store\".\"store_id\" " +
            "and \"sales_fact_1997\".\"time_id\" = \"time_by_day\".\"time_id\" " +
            "and \"time_by_day\".\"the_year\" = 1997 " +
            "and \"sales_fact_1997\".\"customer_id\" = \"customer\".\"customer_id\" " +
            "group by grouping sets " +
            "((\"store\".\"store_country\",\"time_by_day\".\"the_year\",\"customer\".\"gender\")," +
            "(\"store\".\"store_country\",\"time_by_day\".\"the_year\"),(\"time_by_day\".\"the_year\")," +
            "(\"time_by_day\".\"the_year\",\"customer\".\"gender\"))";


        SqlPattern[] patterns1 = {
            new SqlPattern(ORACLE_TERADATA, oraTeraSql, oraTeraSql)};

        assertQuerySql(mdxQuery,patterns1);
    }

    public void testAggregationOnMembersAndDefaultMemberForDistinctCount() {

        prop.EnableGroupingSets.set(true);
        String mdxQueryWithMembers = "WITH " +
            "MEMBER [Gender].[COG_OQP_USR_Aggregate(Gender)] " +
            "AS 'AGGREGATE({[Gender].MEMBERS})', SOLVE_ORDER = 8" +
            "SELECT {[Measures].[Customer Count]} ON AXIS(0), " +
            "{[Gender].MEMBERS, [Gender].[COG_OQP_USR_Aggregate(Gender)]} " +
            "ON AXIS(1) " +
            "FROM [Sales]";

        String mdxQueryWithDefaultMember = "WITH " +
            "MEMBER [Gender].[COG_OQP_USR_Aggregate(Gender)] " +
            "AS 'AGGREGATE({[Gender].DEFAULTMEMBER})', SOLVE_ORDER = 8" +
            "SELECT {[Measures].[Customer Count]} ON AXIS(0), \n" +
            "{[Gender].MEMBERS, [Gender].[COG_OQP_USR_Aggregate(Gender)]} " +
            "ON AXIS(1) \n" +
            "FROM [sales]";

        String desiredResult = fold(
            "Axis #0:\n" +
                "{}\n" +
                "Axis #1:\n" +
                "{[Measures].[Customer Count]}\n" +
                "Axis #2:\n" +
                "{[Gender].[All Gender]}\n" +
                "{[Gender].[All Gender].[F]}\n" +
                "{[Gender].[All Gender].[M]}\n" +
                "{[Gender].[COG_OQP_USR_Aggregate(Gender)]}\n" +
                "Row #0: 5,581\n" +
                "Row #1: 2,755\n" +
                "Row #2: 2,826\n" +
                "Row #3: 5,581\n");


        String  oraTeraSql = "select \"time_by_day\".\"the_year\" as \"c0\", " +
            "\"customer\".\"gender\" as \"c1\", " +
            "count(distinct \"sales_fact_1997\".\"customer_id\") as \"m0\", " +
            "grouping(\"customer\".\"gender\") as \"g0\" " +
            "from \"time_by_day\" =as= \"time_by_day\", " +
            "\"sales_fact_1997\" =as= \"sales_fact_1997\", \"customer\" =as= \"customer\" " +
            "where \"sales_fact_1997\".\"time_id\" = \"time_by_day\".\"time_id\" " +
            "and \"time_by_day\".\"the_year\" = 1997 " +
            "and \"sales_fact_1997\".\"customer_id\" = \"customer\".\"customer_id\" " +
            "group by grouping sets " +
            "((\"time_by_day\".\"the_year\",\"customer\".\"gender\"),(\"time_by_day\".\"the_year\"))";

        SqlPattern[] patterns = {
            new SqlPattern(ORACLE_TERADATA, oraTeraSql, oraTeraSql)};

        assertQueryReturns(mdxQueryWithMembers, desiredResult);
        assertQuerySql(mdxQueryWithMembers, patterns);
        assertQueryReturns(mdxQueryWithDefaultMember, desiredResult);
        assertQuerySql(mdxQueryWithDefaultMember, patterns);
    }

    /**
     * Testcase for bug 2004202, "Except not working with grouping sets".
     */
    public void testBug2004202() {
        assertQueryReturns(
            "with member store.allbutwallawalla as\n"
                + " 'aggregate(\n"
                + "    except(\n"
                + "        store.[store name].members,\n"
                + "        { [Store].[All Stores].[USA].[WA].[Walla Walla].[Store 22]}))'\n"
                + "select {\n"
                + "          store.[store name].members,\n"
                + "         store.allbutwallawalla,\n"
                + "         store.[all stores]} on 0,\n" 
                + "  {measures.[customer count]} on 1\n"
                + "from sales",
            fold("Axis #0:\n" +
                "{}\n" +
                "Axis #1:\n" +
                "{[Store].[All Stores].[Canada].[BC].[Vancouver].[Store 19]}\n" +
                "{[Store].[All Stores].[Canada].[BC].[Victoria].[Store 20]}\n" +
                "{[Store].[All Stores].[Mexico].[DF].[Mexico City].[Store 9]}\n" +
                "{[Store].[All Stores].[Mexico].[DF].[San Andres].[Store 21]}\n" +
                "{[Store].[All Stores].[Mexico].[Guerrero].[Acapulco].[Store 1]}\n" +
                "{[Store].[All Stores].[Mexico].[Jalisco].[Guadalajara].[Store 5]}\n" +
                "{[Store].[All Stores].[Mexico].[Veracruz].[Orizaba].[Store 10]}\n" +
                "{[Store].[All Stores].[Mexico].[Yucatan].[Merida].[Store 8]}\n" +
                "{[Store].[All Stores].[Mexico].[Zacatecas].[Camacho].[Store 4]}\n" +
                "{[Store].[All Stores].[Mexico].[Zacatecas].[Hidalgo].[Store 12]}\n" +
                "{[Store].[All Stores].[Mexico].[Zacatecas].[Hidalgo].[Store 18]}\n" +
                "{[Store].[All Stores].[USA].[CA].[Alameda].[HQ]}\n" +
                "{[Store].[All Stores].[USA].[CA].[Beverly Hills].[Store 6]}\n" +
                "{[Store].[All Stores].[USA].[CA].[Los Angeles].[Store 7]}\n" +
                "{[Store].[All Stores].[USA].[CA].[San Diego].[Store 24]}\n" +
                "{[Store].[All Stores].[USA].[CA].[San Francisco].[Store 14]}\n" +
                "{[Store].[All Stores].[USA].[OR].[Portland].[Store 11]}\n" +
                "{[Store].[All Stores].[USA].[OR].[Salem].[Store 13]}\n" +
                "{[Store].[All Stores].[USA].[WA].[Bellingham].[Store 2]}\n" +
                "{[Store].[All Stores].[USA].[WA].[Bremerton].[Store 3]}\n" +
                "{[Store].[All Stores].[USA].[WA].[Seattle].[Store 15]}\n" +
                "{[Store].[All Stores].[USA].[WA].[Spokane].[Store 16]}\n" +
                "{[Store].[All Stores].[USA].[WA].[Tacoma].[Store 17]}\n" +
                "{[Store].[All Stores].[USA].[WA].[Walla Walla].[Store 22]}\n" +
                "{[Store].[All Stores].[USA].[WA].[Yakima].[Store 23]}\n" +
                "{[Store].[allbutwallawalla]}\n" +
                "{[Store].[All Stores]}\n" +
                "Axis #2:\n" +
                "{[Measures].[Customer Count]}\n" +
                "Row #0: \n" +
                "Row #0: \n" +
                "Row #0: \n" +
                "Row #0: \n" +
                "Row #0: \n" +
                "Row #0: \n" +
                "Row #0: \n" +
                "Row #0: \n" +
                "Row #0: \n" +
                "Row #0: \n" +
                "Row #0: \n" +
                "Row #0: \n" +
                "Row #0: 1,059\n" +
                "Row #0: 1,147\n" +
                "Row #0: 962\n" +
                "Row #0: 296\n" +
                "Row #0: 563\n" +
                "Row #0: 474\n" +
                "Row #0: 190\n" +
                "Row #0: 179\n" +
                "Row #0: 906\n" +
                "Row #0: 84\n" +
                "Row #0: 278\n" +
                "Row #0: 96\n" +
                "Row #0: 95\n" +
                "Row #0: 5,485\n" +
                "Row #0: 5,581\n"));
    }
}

// End GroupingSetQueryTest.java
