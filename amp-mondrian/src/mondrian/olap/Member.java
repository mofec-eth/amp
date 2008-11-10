/*
// $Id: Member.java,v 1.1 2008-11-10 10:06:28 mpostelnicu Exp $
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 1999-2002 Kana Software, Inc.
// Copyright (C) 2001-2007 Julian Hyde and others
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
//
// jhyde, 2 March, 1999
*/

package mondrian.olap;

/**
 * A <code>Member</code> is a 'point' on a dimension of a cube. Examples are
 * <code>[Time].[1997].[January]</code>,
 * <code>[Customer].[All Customers]</code>,
 * <code>[Customer].[USA].[CA]</code>,
 * <code>[Measures].[Unit Sales]</code>.
 *
 * <p> Every member belongs to a {@link Level} of a {@link Hierarchy}. Members
 * except the root member have a parent, and members not at the leaf level
 * have one or more children.
 *
 * <p> Measures are a special kind of member. They belong to their own
 * dimension, <code>[Measures]</code>.
 *
 * <p> There are also special members representing the 'All' value of a
 * hierarchy, the null value, and the error value.
 *
 * <p> Members can have member properties. Their {@link Level#getProperties}
 * defines which are allowed.
 */
public interface Member extends OlapElement, Comparable {

    /**
     * Returns this member's parent, or null (not the 'null member', as
     * returned by {@link Hierarchy#getNullMember}) if it has no parent.
     *
     * <p>In an access-control context, a member may have no <em>visible</em>
     * parents, so use {@link SchemaReader#getMemberParent}.
     */
    Member getParentMember();

    Level getLevel();

    Hierarchy getHierarchy();

    /**
     * Returns name of parent member, or empty string (not null) if we are the
     * root.
     */
    String getParentUniqueName();

    /**
     * Returns the type of member.
     */
    MemberType getMemberType();

    enum MemberType {
        UNKNOWN,
        REGULAR, // adMemberRegular
        ALL,
        MEASURE,
        FORMULA,
        /**
         * This member is its hierarchy's NULL member (such as is returned by
         * <code>[Gender]&#46;[All Gender]&#46;PrevMember</code>, for example).
         */
        NULL
    }

    /**
     * Only allowable if the member is part of the <code>WITH</code> clause of
     * a query.
     */
    void setName(String name);

    /** Returns whether this is the 'all' member. */
    boolean isAll();

    /** Returns whether this is a member of the measures dimension. */
    boolean isMeasure();

    /** Returns whether this is the 'null member'. */
    boolean isNull();

    /**
     * Returns whether <code>member</code> is equal to, a child, or a
     * descendent of this <code>Member</code>.
     */
    boolean isChildOrEqualTo(Member member);

    /** Returns whether this member is computed using either a <code>with
     * member</code> clause in an mdx query or a calculated member defined in
     * cube. */
    boolean isCalculated();
    int getSolveOrder();
    Exp getExpression();

    /**
     * Returns array of all members, which are ancestor to <code>this</code>.
     */
    Member[] getAncestorMembers();

    /**
     * Returns whether this member is computed from a <code>with member</code>
     * clause in an mdx query.
     */
    boolean isCalculatedInQuery();

    /**
     * Returns the value of the property named <code>propertyName</code>.
     * Name match is case-sensitive.
     */
    Object getPropertyValue(String propertyName);

    /**
     * Returns the value of the property named <code>propertyName</code>,
     * matching according to the required case-sensitivity.
     */
    Object getPropertyValue(String propertyName, boolean matchCase);

    /**
     * Returns the formatted value of the property named <code>propertyName</code>.
     */
    String getPropertyFormattedValue(String propertyName);

    /**
     * Sets a property of this member to a given value.
     */
    void setProperty(String name, Object value);

    /**
     * Returns the definitions of the properties this member may have.
     */
    Property[] getProperties();

    /**
     * Returns the ordinal of the member.
     */
    int getOrdinal();

    /**
     * Returns the order key of the member (relative to its siblings);
     * null if undefined or unavailable.
     */
    Comparable getOrderKey();

    /**
     * Returns whether this member is 'hidden', as per the rules which define
     * a ragged hierarchy.
     */
    boolean isHidden();

    /**
     * returns the depth of this member, which is not the level's depth
     *  in case of parent child dimensions
     * @return depth
     */
    int getDepth();

    /**
     * Returns the system-generated data member that is associated with a
     * nonleaf member of a dimension.
     *
     * <p>Returns this member if this member is a leaf member, or if the
     * nonleaf member does not have an associated data member.</p>
     */
    Member getDataMember();
}

// End Member.java
