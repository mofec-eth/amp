/*
// $Id: RolapSchemaReaderTest.java,v 1.1 2008-11-10 10:07:24 mpostelnicu Exp $
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 2004-2007 Julian Hyde and others
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
*/
package mondrian.rolap;

import java.util.List;
import java.util.Arrays;

import junit.framework.TestCase;
import mondrian.olap.*;
import mondrian.test.FoodMartTestCase;


/**
 * Unit test for {@link SchemaReader}.
 */
public class RolapSchemaReaderTest extends FoodMartTestCase {
    public RolapSchemaReaderTest(String name) {
        super(name);
    }

    public void testGetCubesWithNoHrCubes() {

        String[] expectedCubes = new String[] {
                "Sales", "Warehouse", "Warehouse and Sales", "Store",
                "Sales Ragged", "Sales 2"
        };

        Connection connection = getTestContext().withRole("No HR Cube").getConnection();
        try {
            SchemaReader reader = connection.getSchemaReader();

            Cube[] cubes = reader.getCubes();

            assertEquals(expectedCubes.length, cubes.length);

            assertCubeExists(expectedCubes, cubes);
        }
        finally {
            connection.close();
        }
    }

    public void testGetCubesWithNoRole() {

        String[] expectedCubes = new String[] {
                "Sales", "Warehouse", "Warehouse and Sales", "Store",
                "Sales Ragged", "Sales 2", "HR"
        };

        Connection connection = getTestContext().getConnection();
        try {
            SchemaReader reader = connection.getSchemaReader();

            Cube[] cubes = reader.getCubes();

            assertEquals(expectedCubes.length, cubes.length);

            assertCubeExists(expectedCubes, cubes);
        }
        finally {
            connection.close();
        }
    }

    public void testGetCubesForCaliforniaManager() {

        String[] expectedCubes = new String[] {
                "Sales"
        };

        Connection connection =
                getTestContext().withRole("California manager").getConnection();
        try {
            SchemaReader reader = connection.getSchemaReader();

            Cube[] cubes = reader.getCubes();

            assertEquals(expectedCubes.length, cubes.length);

            assertCubeExists(expectedCubes, cubes);
        }
        finally {
            connection.close();
        }
    }

    private void assertCubeExists(String[] expectedCubes, Cube[] cubes) {
        List cubesAsList = Arrays.asList(expectedCubes);

        for (Cube cube : cubes) {
            String cubeName = cube.getName();
            assertTrue("Cube name not found: " + cubeName,
                cubesAsList.contains(cubeName));
        }
    }

}

// End RolapSchemaReaderTest.java
