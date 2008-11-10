/*
// $Id: MemberExpr.java,v 1.1 2008-11-10 10:07:46 mpostelnicu Exp $
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 2006-2006 Julian Hyde
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
*/
package mondrian.mdx;

import mondrian.olap.*;
import mondrian.olap.type.Type;
import mondrian.olap.type.MemberType;
import mondrian.calc.*;
import mondrian.calc.impl.ConstantCalc;

/**
 * Usage of a {@link mondrian.olap.Member} as an MDX expression.
 *
 * @author jhyde
 * @version $Id: MemberExpr.java,v 1.1 2008-11-10 10:07:46 mpostelnicu Exp $
 * @since Sep 26, 2005
 */
public class MemberExpr extends ExpBase implements Exp {
    private final Member member;

    /**
     * Creates a member expression.
     *
     * @param member Member
     * @pre member != null
     */
    public MemberExpr(Member member) {
        Util.assertPrecondition(member != null, "member != null");
        this.member = member;
    }

    /**
     * Returns the member.
     *
     * @post return != null
     */
    public Member getMember() {
        return member;
    }

    public String toString() {
        return member.getUniqueName();
    }

    public Type getType() {
        return MemberType.forMember(member);
    }

    public MemberExpr clone() {
        return new MemberExpr(member);
    }

    public int getCategory() {
        return Category.Member;
    }

    public Exp accept(Validator validator) {
        return this;
    }

    public Calc accept(ExpCompiler compiler) {
        return ConstantCalc.constantMember(member);
    }

    public Object accept(MdxVisitor visitor) {
        return visitor.visit(this);
    }
}

// End MemberExpr.java
