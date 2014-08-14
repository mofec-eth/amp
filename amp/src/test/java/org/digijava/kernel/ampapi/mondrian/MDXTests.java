/**
 * 
 */
package org.digijava.kernel.ampapi.mondrian;

import java.util.Arrays;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.dgfoundation.amp.testutils.AmpTestCase;
import org.digijava.kernel.ampapi.mondrian.queries.MDXGenerator;
import org.digijava.kernel.ampapi.mondrian.queries.entities.MDXAttribute;
import org.digijava.kernel.ampapi.mondrian.queries.entities.MDXConfig;
import org.digijava.kernel.ampapi.mondrian.queries.entities.MDXFilter;
import org.digijava.kernel.ampapi.mondrian.queries.entities.MDXLevel;
import org.digijava.kernel.ampapi.mondrian.queries.entities.MDXMeasure;
import org.digijava.kernel.ampapi.mondrian.queries.entities.MDXTuple;
import org.digijava.kernel.ampapi.mondrian.util.MoConstants;
import org.digijava.kernel.ampapi.mondrian.util.MondrianUtils;
import org.olap4j.CellSet;
import org.olap4j.query.SortOrder;

/**
 * MDX Queries generation tests cases
 * @author Nadejda Mandrescu
 */
public class MDXTests extends AmpTestCase {

	private MDXTests(String name) {
		super(name);
	}
	
	public static Test suite()
	{
		TestSuite suite = new TestSuite(MDXTests.class.getName());
		suite.addTest(new MDXTests("testNoTotals"));
		suite.addTest(new MDXTests("testTotals"));
		suite.addTest(new MDXTests("testNoColumnAttr"));
		suite.addTest(new MDXTests("testDataSingleValueFilter"));
		suite.addTest(new MDXTests("testDataSingleValueFilterByLocation"));
		suite.addTest(new MDXTests("testDataFilters"));
		suite.addTest(new MDXTests("testRangeFilter"));
		suite.addTest(new MDXTests("testSinglePropertyFilter"));
		suite.addTest(new MDXTests("testPropertiesListFilter"));
		suite.addTest(new MDXTests("testMultipleHierarchies"));
		suite.addTest(new MDXTests("testSortingNoTotals"));
		suite.addTest(new MDXTests("testSortingBy2012Q1ActualCommitments"));
		//suite.addTest(new MDXTests("testCacheDimension"));
		return suite;
	}
	
	public void testNoTotals() {
		String expectedRes = null;
		generateAndValidateMDX(getDefaultConfig("testNoTotals", false), expectedRes, false);
	}
	
	public void testTotals() {
		String expectedRes = null;
		MDXConfig config = getDefaultConfig("testTotals", true);
		generateAndValidateMDX(config, expectedRes, false);
	}
	
	public void testNoColumnAttr() {
		MDXConfig config = getDefaultConfig("testNoColumnAttr", true);
		config.getColumnAttributes().clear();
		generateAndValidateMDX(config, null, false);
	}
	
	public void testDataSingleValueFilter() {
		String expectedRes = null;
		MDXConfig config = getDefaultConfig("testDataSingleValueFilter", true);
		config.addSingleValueFilters(new MDXLevel(MoConstants.DATES, MoConstants.H_DATES, MoConstants.ATTR_YEAR, "2014"));
		generateAndValidateMDX(config, expectedRes, false);
	}
	
	public void testDataSingleValueFilterByLocation() {
		String expectedRes = null;
		MDXConfig config = getDefaultConfig("testDataSingleValueFilterByLocation", true);
		config.addSingleValueFilters(new MDXLevel(MoConstants.LOCATION, MoConstants.H_LOCATIONS, MoConstants.ATTR_COUNTRY_NAME, "Moldova"));
		generateAndValidateMDX(config, expectedRes, false);
	}
	
	public void testDataFilters() {
		String expectedRes = null;
		MDXConfig config = getDefaultConfig("testDataFilters", true);
		addSimpleFilter(config);
		generateAndValidateMDX(config, expectedRes, false);
	}
	
	private void addSimpleFilter(MDXConfig config) {
		MDXAttribute attrYear = new MDXLevel(MoConstants.DATES, MoConstants.H_DATES, MoConstants.ATTR_YEAR);
		MDXFilter filter = new MDXFilter(Arrays.asList("1980", "1994"), true, null);
		config.getDataFilters().put(attrYear, filter);
	}
	
	public void testRangeFilter() {
		String expectedRes = null;
		MDXConfig config = getDefaultConfig("testRangeFilter", true);
		MDXAttribute attrYear = new MDXLevel(MoConstants.DATES, MoConstants.H_DATES, MoConstants.ATTR_YEAR);
		MDXFilter filter = new MDXFilter("2010", true, "2012", true, null);
		config.getDataFilters().put(attrYear, filter);
		generateAndValidateMDX(config, expectedRes, false);
	}
	
	public void testSinglePropertyFilter() {
		String expectedRes = null;
		MDXConfig config = getDefaultConfig("testSinglePropertyFilter", true);
		MDXAttribute attrLocation = new MDXLevel(MoConstants.LOCATION, MoConstants.H_LOCATIONS, MoConstants.ATTR_LOCATION_NAME);
		MDXFilter filter = new MDXFilter("8977", true, MoConstants.P_COUNTRY_ID);
		config.getDataFilters().put(attrLocation, filter);
		generateAndValidateMDX(config, expectedRes, false);
	}
	
	public void testPropertiesListFilter() {
		String expectedRes = null;
		MDXConfig config = getDefaultConfig("testPropertiesListFilter", true);
		MDXAttribute attrLocation = new MDXLevel(MoConstants.LOCATION, MoConstants.H_LOCATIONS, MoConstants.ATTR_LOCATION_NAME);
		MDXFilter filter = new MDXFilter(Arrays.asList("8977", "9015", "8857"), true, MoConstants.P_COUNTRY_ID);
		config.getDataFilters().put(attrLocation, filter);
		generateAndValidateMDX(config, expectedRes, false);
	}
	
	public void testMultipleHierarchies() {
		String expectedRes = null;
		MDXConfig config = getDefaultConfig("testMultipleHierarchies", true);
		config.getColumnAttributes().add(0, new MDXAttribute(MoConstants.APPROVAL_STATUS, MoConstants.ATTR_APPROVAL_STATUS));
		config.getRowAttributes().add(0, new MDXLevel(MoConstants.DONOR_AGENCY, MoConstants.H_ORG_GROUP_NAME, MoConstants.ATTR_ORG_GROUP_NAME));
		addSimpleFilter(config); //filter for easier testing
		generateAndValidateMDX(config, expectedRes, false);
	}
	
	public void testSortingNoTotals() {
		String expectedRes = null;
		MDXConfig config = getDefaultConfig("testSortingNoTotals", false);
		MDXTuple sortingTouple1 = new MDXTuple();
		sortingTouple1.add(config.getRowAttributes().get(1));
		MDXTuple sortingTouple2 = new MDXTuple();
		sortingTouple2.add(config.getRowAttributes().get(0));
		config.getSortingOrder().put(sortingTouple1, SortOrder.BASC);
		config.getSortingOrder().put(sortingTouple2, SortOrder.DESC);
		generateAndValidateMDX(config, expectedRes, false);
	}
	
	public void testSortingBy2012Q1ActualCommitments() {
		MDXConfig config = getDefaultConfig("testSortingBy2012Q1ActualCommitments", false);
		config.addColumnAttribute(new MDXLevel(MoConstants.DATES, MoConstants.H_QUARTER, MoConstants.ATTR_QUARTER));
		config.getRowAttributes().clear();
		config.addRowAttribute(new MDXAttribute(MoConstants.PRIMARY_SECTOR, MoConstants.ATTR_PRIMARY_SECTOR_NAME));
		MDXTuple sortingTouple1 = new MDXTuple();
		sortingTouple1.add(new MDXLevel(MoConstants.DATES, MoConstants.H_DATES_DUPLICATE, "2012", "Q1"));
		sortingTouple1.add(config.getColumnMeasures().get(0));
		config.getSortingOrder().put(sortingTouple1, SortOrder.BASC);
		config.addSingleValueFilters(new MDXLevel(MoConstants.DATES, MoConstants.H_DATES, MoConstants.ATTR_YEAR, "2012"));
		generateAndValidateMDX(config, null, false);
	}
	
	private MDXConfig getDefaultConfig(String testName, boolean doTotals) {
		MDXConfig config = new MDXConfig();
		config.setMdxName(testName);
		config.addColumnAttribute(new MDXLevel(MoConstants.DATES, MoConstants.H_DATES, MoConstants.ATTR_YEAR));
		config.addColumnMeasure(new MDXMeasure(MoConstants.ACTUAL_COMMITMENTS));
		config.addColumnMeasure(new MDXMeasure(MoConstants.ACTUAL_DISBURSEMENTS));
		config.addRowAttribute(new MDXLevel(MoConstants.LOCATION, MoConstants.H_LOCATIONS, MoConstants.ATTR_COUNTRY_NAME));
		config.addRowAttribute(new MDXLevel(MoConstants.DONOR_AGENCY, MoConstants.H_ORG_TYPE_NAME, MoConstants.ATTR_ORG_TYPE_NAME));
		config.setAllowEmptyColumnsData(false);
		config.setAllowEmptyRowsData(false);
		config.setDoColumnsTotals(doTotals);
		config.setDoRowTotals(doTotals);
		return config;
	}
	
	private CellSet generateAndValidateMDX(MDXConfig config, String expectedMdx, boolean runQuery) {
		String err = null;
		String mdx = null;
		CellSet set = null;
		MDXGenerator generator = null;
		try {
			generator = new MDXGenerator();
			mdx = generator.getAdvancedOlapQuery(config);
			if (runQuery) {
				set = generator.runQuery(mdx);
				MondrianUtils.print(set, config.getMdxName());
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			err = e.getMessage();
		}
		if (generator != null) generator.tearDown();
		assertNull(err);
		if (expectedMdx!=null)
			assertTrue(expectedMdx.equalsIgnoreCase(mdx));
		return set;
	}
	
	/*******************************************************************
	 *				 Mondrian Cache Control API tests
	 ********************************************************************/
	/*
	public void testCacheDimension() {
		MondrianUtils.PRINT_PATH = "/home/nadia/Documents/AMP/work/Mondrian/reports";
		MDXConfig config = new MDXConfig();
		config.setMdxName("testCacheDimension");
		config.addColumnMeasure(new MDXMeasure(MoConstants.ACTUAL_COMMITMENTS));
		//config.addColumnMeasure(new MDXMeasure(MoConstants.ACTUAL_DISBURSEMENTS));
		//config.addRowAttribute(new MDXLevel(MoConstants.DONOR_AGENCY, MoConstants.H_ORG_TYPE_NAME, MoConstants.ATTR_ORG_TYPE_NAME));
		//config.addRowAttribute(new MDXAttribute(MoConstants.PRIMARY_SECTOR, MoConstants.ATTR_PRIMARY_SECTOR_NAME));
		config.addRowAttribute(new MDXAttribute(MoConstants.PROJECT_TITLE, MoConstants.ATTR_PROJECT_TITLE));
		
		updateData(true);

		initCacheSettings();
		//initDimensionCache(config);

		generateAndValidateMDX(config, null, true);
		
		updateData(false);
		
		config.setMdxName("testCacheDimensionRefresh");
		
		updateDimensionCache(config);
		
		generateAndValidateMDX(config, null, true);
		
		//testFound(config);
		
		try {
			olapConnection.close();
		} catch (java.sql.SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void updateData(boolean doRestore) {
		String[] restore = {"update mondrian_fact_table set transaction_amount=356741.5 where entity_id=109", 
				"update mondrian_fact_table set transaction_amount=1000000 where entity_id=110",
				"update mondrian_activity_texts set name='CENTER FOR PREVENTION OF TRAFFICKING IN WOMEN' where amp_activity_id = (select max(amp_activity_id) from mondrian_activity_texts where amp_id='idea_1374')"
		};
		String[] change = {"update mondrian_fact_table set transaction_amount=456741.5 where entity_id=109",
				"update mondrian_fact_table set transaction_amount=2000000 where entity_id=110",
				"update mondrian_activity_texts set name='CENTER FOR PREVENTION OF TRAFFICKING IN WOMEN111' where amp_activity_id = (select max(amp_activity_id) from mondrian_activity_texts where amp_id='idea_1374')"
		};

		final String c = "jdbc:postgresql://localhost/amp_moldova_210";
		java.sql.Connection conn = null;
		try {
			conn = java.sql.DriverManager.getConnection(c, "amp", "amp321");
			String[] toExecute = doRestore ? restore : change;
			for (String update : toExecute)
				org.dgfoundation.amp.ar.viewfetcher.SQLUtils.executeQuery(conn, update);
		} catch (java.sql.SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (java.sql.SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		//Session session = PersistenceManager.openNewSession();
		//SQLQuery query = session.createSQLQuery(doRestore ? restore : change);
		//query.executeUpdate();
		//session.flush();
		//session.close();
	}
	
	private org.olap4j.OlapConnection olapConnection = null;
	private mondrian.olap.CacheControl cacheControl = null;
	private mondrian.olap.Connection mondrianConnection;
	
	private void initCacheSettings() {
		mondrian.olap.MondrianProperties.instance().EnableRolapCubeMemberCache.set(false);
		
		try {
			olapConnection = org.digijava.kernel.ampapi.mondrian.util.Connection.getOlapConnectionByConnPath(
					org.digijava.kernel.ampapi.mondrian.util.Connection.getDefaultConnectionPath());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (olapConnection != null) {
			try {
				mondrianConnection = olapConnection.unwrap(mondrian.rolap.RolapConnection.class);
				cacheControl = mondrianConnection.getCacheControl(null);
			} catch (java.sql.SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void initDimensionCache(MDXConfig config) {
		try {
			mondrian.olap.Cube cube = getCube(config);
			mondrian.olap.SchemaReader schemaReader = cube.getSchemaReader(null).withLocus(); 
			mondrian.olap.Member member = schemaReader.getMemberByUniqueName(mondrian.olap.Id.Segment.toList(
					MoConstants.PROJECT_TITLE, "CENTER FOR PREVENTION OF TRAFFICKING IN WOMEN"), true);
			//for (Memeber m : )
			mondrian.olap.CacheControl.MemberEditCommand addCommand = cacheControl.createAddCommand(member);

			cacheControl.execute(addCommand);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void updateDimensionCache(MDXConfig config) {
		try {
			mondrian.olap.Cube cube = getCube(config);
			mondrian.olap.SchemaReader schemaReader = cube.getSchemaReader(null).withLocus(); 
			mondrian.olap.Member parent = schemaReader.getMemberByUniqueName(mondrian.olap.Id.Segment.toList(
					MoConstants.PROJECT_TITLE, "CENTER FOR PREVENTION OF TRAFFICKING IN WOMEN111"), true);//.getParentMember();
			mondrian.olap.CacheControl.MemberSet memberSet = cacheControl.createMemberSet(parent, true);
			mondrian.olap.CacheControl.MemberEditCommand delCommand = cacheControl.createDeleteCommand(memberSet);
			
			mondrian.olap.CacheControl.CellRegion measuresRegion = cacheControl.createMeasuresRegion(cube);
			mondrian.olap.CacheControl.CellRegion dimensionRegion = cacheControl.createMemberRegion(parent, true);

			mondrian.olap.CacheControl.CellRegion[] regions = new mondrian.olap.CacheControl.CellRegion[2]; 
			regions[0] = measuresRegion;
			regions[1] = dimensionRegion;
			
			mondrian.olap.CacheControl.CellRegion crossJoin = cacheControl.createCrossjoinRegion(regions);
			//flushes only the region data for associated member
			cacheControl.flush(crossJoin);
			//flushes the entire schema cache
			//cacheControl.flushSchemaCache();
			//flushes all measures related data  
			//cacheControl.flush(measuresRegion);

			//flushes member set it is part of AND! associated regions to the set => in this case all project titles & their regions 
			//cacheControl.execute(delCommand);
			//cacheControl.flush(memberSet);
			
			//olapConnection.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void testFound(MDXConfig config) {
		try {
			mondrian.olap.Cube cube = getCube(config);
			mondrian.olap.SchemaReader schemaReader = cube.getSchemaReader(null).withLocus();
			mondrian.olap.Member other = schemaReader.getMemberByUniqueName(mondrian.olap.Id.Segment.toList(
					MoConstants.PROJECT_TITLE, "CENTER FOR PREVENTION OF TRAFFICKING IN WOMEN111"), true);//.getParentMember();
			mondrian.olap.CacheControl.MemberSet otherMemberSet = cacheControl.createMemberSet(other, true);
		} catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private mondrian.olap.Cube getCube(MDXConfig config) throws AmpApiException {
		String cubeName = config.getCubeName();
		mondrian.olap.Cube cube = null;
		//get the Cube reference
		try {
			cube = mondrianConnection.getSchema().lookupCube(config.getCubeName(), true);
		} catch (Exception e) {
			System.err.println("Cannot get cubes list from Mondrian Schema");
			throw new AmpApiException(AmpApiException.MONDRIAN_ERROR, false, e);
		}
		return cube;
	}
	*/
}
