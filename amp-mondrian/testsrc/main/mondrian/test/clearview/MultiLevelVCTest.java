/*
// $Id: MultiLevelVCTest.java,v 1.1 2008-11-10 10:05:32 mpostelnicu Exp $
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 2007-2007 Julian Hyde and others
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
//
*/
package mondrian.test.clearview;

import junit.framework.*;

import mondrian.test.*;

/**
 * <code>MultiLevelVCTest</code> is a test suite which tests
 * complex queries against the FoodMart database. MDX queries and their
 * expected results are maintained separately in MultiLevelVCTest.ref.xml file.
 * If you would prefer to see them as inlined Java string literals, run
 * ant target "generateDiffRepositoryJUnit" and then use
 * file MultiLevelVCTestJUnit.java which will be generated in this directory.
 *
 * @author Khanh Vu
 * @version $Id: MultiLevelVCTest.java,v 1.1 2008-11-10 10:05:32 mpostelnicu Exp $
 */
public class MultiLevelVCTest extends ClearViewBase {

    public MultiLevelVCTest() {
        super();
    }

    public MultiLevelVCTest(String name) {
        super(name);
    }

    public DiffRepository getDiffRepos() {
        return getDiffReposStatic();
    }

    private static DiffRepository getDiffReposStatic() {
        return DiffRepository.lookup(MultiLevelVCTest.class);
    }

    public static TestSuite suite() {
        return constructSuite(getDiffReposStatic(), MultiLevelVCTest.class);
    }

}

// End MultiLevelVCTest.java
