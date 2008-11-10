/*
// $Id: StringType.java,v 1.1 2008-11-10 10:07:48 mpostelnicu Exp $
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

/**
 * The type of a string expression.
 *
 * @author jhyde
 * @since Feb 17, 2005
 * @version $Id: StringType.java,v 1.1 2008-11-10 10:07:48 mpostelnicu Exp $
 */
public class StringType extends ScalarType {

    /**
     * Creates a string type.
     */
    public StringType() {
        super("STRING");
    }

    public boolean equals(Object obj) {
        return obj instanceof StringType;
    }
}

// End StringType.java
