/*
// $Id: NullType.java,v 1.1 2008-11-10 10:07:50 mpostelnicu Exp $
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 2005-2008 Julian Hyde
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
*/
package mondrian.olap.type;

/**
 * The type of a null expression.
 *
 * @author medstat
 * @version $Id: NullType.java,v 1.1 2008-11-10 10:07:50 mpostelnicu Exp $
 * @since Aug 21, 2006
 */
public class NullType extends ScalarType
{
    /**
     * Creates a null type.
     */
    public NullType()
    {
        super("<NULLTYPE>");
    }

    public boolean equals(Object obj) {
        return obj instanceof NullType;
    }
}

// End NullType.java
