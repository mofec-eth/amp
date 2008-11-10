/*
// $Id: MiscTest.java,v 1.1 2008-11-10 10:05:33 mpostelnicu Exp $
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
 * <code>MiscTest</code> is a test suite which tests miscellaneous
 * complex queries against the FoodMart database. MDX queries and their
 * expected results are maintained separately in MiscTest.ref.xml file.
 * If you would prefer to see them as inlined Java string literals, run
 * ant target "generateDiffRepositoryJUnit" and then use
 * file MiscTestJUnit.java which will be generated in this directory.
 *
 * @author Khanh Vu
 * @version $Id: MiscTest.java,v 1.1 2008-11-10 10:05:33 mpostelnicu Exp $
 */
public class MiscTest extends ClearViewBase {

    public MiscTest() {
        super();
    }

    public MiscTest(String name) {
        super(name);
    }

    public DiffRepository getDiffRepos() {
        return getDiffReposStatic();
    }

    private static DiffRepository getDiffReposStatic() {
        return DiffRepository.lookup(MiscTest.class);
    }

    public static TestSuite suite() {
        return constructSuite(getDiffReposStatic(), MiscTest.class);
    }

}

// End MiscTest.java
