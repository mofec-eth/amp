/*
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 2004-2005 TONBELLER AG
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
*/
package mondrian.rolap;

import mondrian.olap.Evaluator;
import mondrian.rolap.sql.TupleConstraint;
import mondrian.rolap.sql.MemberChildrenConstraint;
import mondrian.rolap.sql.SqlQuery;
import mondrian.rolap.aggmatcher.AggStar;

/**
 * TupleConstraint which does not restrict the result.
 *
 * @version $Id: DefaultTupleConstraint.java,v 1.1 2008-11-10 10:07:10 mpostelnicu Exp $
 */
public class DefaultTupleConstraint implements TupleConstraint {

    private static final TupleConstraint instance = new DefaultTupleConstraint();

    /** we have no state, so all instances are equal */
    private static final Object cacheKey = new Object();

    protected DefaultTupleConstraint() {
    }

    public void addConstraint(SqlQuery sqlQuery, RolapCube baseCube) {
    }

    public void addLevelConstraint(
        SqlQuery query,
        RolapCube baseCube,
        AggStar aggStar,
        RolapLevel level) {
    }

    public MemberChildrenConstraint getMemberChildrenConstraint(RolapMember parent) {
        return DefaultMemberChildrenConstraint.instance();
    }

    public String toString() {
        return "DefaultTupleConstraint";
    }

    public Object getCacheKey() {
        return cacheKey;
    }

    public static TupleConstraint instance() {
        return instance;
    }

    public Evaluator getEvaluator() {
        return null;
    }

}

// End DefaultTupelConstraint.java

