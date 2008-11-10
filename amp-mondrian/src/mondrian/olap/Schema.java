/*
// $Id: Schema.java,v 1.1 2008-11-10 10:06:15 mpostelnicu Exp $
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 2006-2007 Julian Hyde
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
*/
package mondrian.olap;

import java.util.Date;
import java.util.List;

/**
 * A <code>Schema</code> is a collection of cubes, shared dimensions, and roles.
 *
 * @author jhyde
 * @version $Id: Schema.java,v 1.1 2008-11-10 10:06:15 mpostelnicu Exp $
 */
public interface Schema {

    /**
     * Returns the name of this schema.
     * @post return != null
     * @post return.length() > 0
     */
    String getName();
    /**
     * Finds a cube called <code>cube</code> in this schema; if no cube
     * exists, <code>failIfNotFound</code> controls whether to raise an error
     * or return <code>null</code>.
     */
    Cube lookupCube(String cube,boolean failIfNotFound);

    /**
     * Returns a list of all cubes in this schema.
     */
    Cube[] getCubes();

    /**
     * Returns a list of shared dimensions in this schema.
     */
    Hierarchy[] getSharedHierarchies();

    /**
     * Creates a dimension in the given cube by parsing an XML string. The XML
     * string must be either a &lt;Dimension&gt; or a &lt;DimensionUsage&gt;.
     * Returns the dimension created.
     */
    Dimension createDimension(Cube cube, String xml);

    /**
     * Creates a cube by parsing an XML string. Returns the cube created.
     */
    Cube createCube(String xml);

    /**
     * Removes a cube.
     *
     * @return Whether cube was removed
     */
    boolean removeCube(String cubeName);

    /**
     * Creates a {@link SchemaReader} without any access control.
     */
    SchemaReader getSchemaReader();

    /**
     * Finds a role with a given name in the current catalog, or returns
     * <code>null</code> if no such role exists.
     */
    Role lookupRole(String role);

    /**
     * Returns this schema's function table.
     */
    FunTable getFunTable();

    /**
     * Returns this schema's parameters.
     */
    Parameter[] getParameters();

    /**
     * Returns when this schema was last loaded.
     * 
     * @return Date and time when this schema was last loaded
     */
    Date getSchemaLoadDate();

    /**
     * Returns a list of warnings and errors that occurred while loading this
     * schema.
     *
     * @return list of warnings
     */
    List<Exception> getWarnings();
}

// End Schema.java
