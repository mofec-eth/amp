/*
// $Id: Type.java,v 1.1 2008-11-10 10:07:49 mpostelnicu Exp $
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 2005-2008 Julian Hyde
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
*/
package mondrian.olap.type;

import mondrian.olap.Dimension;
import mondrian.olap.Hierarchy;
import mondrian.olap.Level;

/**
 * Type of an MDX expression.
 *
 * @author jhyde
 * @since Feb 17, 2005
 * @version $Id: Type.java,v 1.1 2008-11-10 10:07:49 mpostelnicu Exp $
 */
public interface Type {
    /**
     * Returns whether this type contains a given dimension.<p/>
     *
     * For example:
     * <ul>
     * <li><code>DimensionType([Gender])</code> uses only the
     *     <code>[Gender]</code> dimension.</li>
     * <li><code>TupleType(MemberType([Gender]), MemberType([Store]))</code>
     *     uses <code>[Gender]</code>  and <code>[Store]</code>
     *     dimensions.</li>
     * </ul><p/>
     *
     * The <code>definitely</code> parameter comes into play when the
     * dimensional information is incomplete. For example, when applied to
     * <code>TupleType(MemberType(null), MemberType([Store]))</code>,
     * <code>usesDimension([Gender], false)</code> returns true because it
     * is possible that the expression returns a member of the
     * <code>[Gender]</code> dimension; but
     * <code>usesDimension([Gender], true)</code> returns true because it
     * is possible that the expression returns a member of the
     * <code>[Gender]</code> dimension.
     *
     * @param dimension Dimension
     * @param definitely If true, returns true only if this type definitely
     *    uses the dimension
     *
     * @return whether this Type uses the given Dimension
     */
    boolean usesDimension(Dimension dimension, boolean definitely);

    /**
     * Returns the Dimension of this Type, or null if not known.
     * If not applicable, throws.
     *
     * @return the Dimension of this Type, or null if not known.
     */
    Dimension getDimension();

    /**
     * Returns the Hierarchy of this Type, or null if not known.
     * If not applicable, throws.
     *
     * @return the Hierarchy of this type, or null if not known
     */
    Hierarchy getHierarchy();

    /**
     * Returns the Level of this Type, or null if not known.
     * If not applicable, throws.
     *
     * @return the Level of this Type
     */
    Level getLevel();

    /**
     * Returns a Type which is more general than this and the given Type.
     * The type returned is broad enough to hold any value of either type,
     * but no broader. If there is no such type, returns null.
     *
     * <p>Some examples:<ul>
     * <li>The common type for StringType and NumericType is ScalarType.
     * <li>The common type for NumericType and DecimalType(4, 2) is
     *     NumericType.
     * <li>DimensionType and NumericType have no common type.
     * </ul></p>
     *
     * <p>If <code>conversionCount</code> is not null, implicit conversions
     * such as HierarchyType to DimensionType are considered; the parameter
     * is incremented by the number of conversions performed.
     *
     * <p>Some examples:<ul>
     * <li>The common type for HierarchyType(hierarchy=Time.Weekly)
     *     and LevelType(dimension=Time), if conversions are allowed, is
     *     HierarchyType(dimension=Time).
     * </ul>
     *
     * <p>One use of common types is to determine the types of the arguments
     * to the <code>Iif</code> function. For example, the call
     *
     * <blockquote><code>Iif(1 > 2, [Measures].[Unit Sales], 5)</code></blockquote>
     *
     * has type ScalarType, because DecimalType(-1, 0) is a subtype of
     * ScalarType, and MeasureType can be converted implicitly to ScalarType.
     *
     * @param type Type
     *
     * @param conversionCount Number of conversions; output parameter that is
     * incremented each time a conversion is performed; if null, conversions
     * are not considered
     *
     * @return More general type
     */
    Type computeCommonType(Type type, int[] conversionCount);
}

// End Type.java
