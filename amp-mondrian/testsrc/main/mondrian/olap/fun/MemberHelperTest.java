/*
// $Id: MemberHelperTest.java,v 1.1 2008-11-10 10:08:01 mpostelnicu Exp $
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 2004-2007 Julian Hyde and others
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
*/

package mondrian.olap.fun;

import mondrian.olap.*;

import junit.framework.TestCase;

/**
 * <code>MemberHelperTest</code> tests {@link MemberHelper}.
 *
 * @author gjohnson
 * @version $Id: MemberHelperTest.java,v 1.1 2008-11-10 10:08:01 mpostelnicu Exp $
 */
public class MemberHelperTest extends TestCase {
    public MemberHelperTest(String name) {
        super(name);
    }

    public void testEqualsMembers() {
        MemberHelper mh0 = new MemberHelper(null);
        assertFalse(mh0.equals(null));

        assertEquals(mh0, mh0);

        assertFalse(mh0.equals(""));

        MemberHelper mh1 = new MemberHelper(null);
        assertEquals(mh0, mh1);

        mh0 = new MemberHelper(new TestMember("foo"));
        assertFalse(mh0.equals(mh1));

        mh1 = new MemberHelper(new TestMember("baz"));
        MemberHelper mh2 = new MemberHelper(new TestMember("foo"));

        assertEquals(mh0, mh2);
        assertFalse(mh1.equals(mh2));

    }


    public void testEqualsMemberArray() {
        Member[] a1 = new Member[]{
            new TestMember("blah"),
            new TestMember("foo"),
            new TestMember("bar"),
        };
        Member[] a2 = new Member[]{
            new TestMember("blah"),
            new TestMember("foo"),
            new TestMember("bar"),
        };
        Member[] a3 = new Member[]{
            new TestMember("blah"),
            new TestMember("bar"),
        };

        MemberHelper mh1 = new MemberHelper(a1);
        MemberHelper mh2 = new MemberHelper(a2);
        MemberHelper mh3 = new MemberHelper(a3);

        assertEquals(mh1, mh2);
        assertFalse(mh1.equals(mh3));
        assertFalse(mh3.equals(mh1));

    }

    public void testConstructor() {
        try {
            new MemberHelper("blah");
            fail("Should not be able to create a MemberHelper with a string");
        }
        catch(IllegalArgumentException iae) {
        }
    }

    static class TestMember implements Member {
        String name;

        public TestMember(String name) {
            this.name = name;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof TestMember)) {
                return false;
            }

            final TestMember testMember = (TestMember) o;

            return name.equals(testMember.name);
        }

        public int hashCode() {
            return name.hashCode();
        }

        public Member[] getAncestorMembers() {
            return new Member[0];
        }

        public String getCaption() {
            return null;
        }

        public Hierarchy getHierarchy() {
            return null;
        }

        public Level getLevel() {
            return null;
        }

        public MemberType getMemberType() {
            return MemberType.REGULAR;
        }

        public int getOrdinal() {
            return 0;
        }

        public Comparable getOrderKey() {
            return null;
        }

        public Member getParentMember() {
            return null;
        }

        public String getParentUniqueName() {
            return null;
        }

        public Property[] getProperties() {
            return new Property[0];
        }

        public Object getPropertyValue(String propertyName) {
            return null;
        }

        public Object getPropertyValue(String propertyName, boolean matchCase) {
            return null;
        }

        public boolean isAll() {
            return false;
        }

        public boolean isCalculated() {
            return false;
        }

        public boolean isCalculatedInQuery() {
            return false;
        }

        public boolean isChildOrEqualTo(Member member) {
            return false;
        }

        public boolean isMeasure() {
            return false;
        }

        public boolean isNull() {
            return false;
        }

        public void setName(String name) {

        }

        public void setProperty(String name, Object value) {

        }

        public String getDescription() {
            return null;
        }

        public String getName() {
            return null;
        }

        public String getQualifiedName() {
            return null;
        }

        public String getUniqueName() {
            return null;
        }

        public OlapElement lookupChild(SchemaReader schemaReader,Id.Segment s) {
            return null;
        }

        public OlapElement lookupChild(
            SchemaReader schemaReader, Id.Segment s, MatchType matchType) {
            return null;
        }

        public Object clone() {
            return this;
        }

        public Dimension getDimension() {
            return null;
        }

        public int compareTo(Object o) {
            return 0;
        }

        public boolean isHidden() {
            return false;
        }

        public int getDepth() {
            return 0;
        }

        public String getPropertyFormattedValue(String propertyName) {
            return "";
        }

        public Member getDataMember() {
            return null;
        }

        public Exp getExpression() {
            return null;
        }

        public int getSolveOrder() {
            return -1;
        }
    }
}

// End MemberHelperTest.java
