/*
// $Id: NativeEvaluator.java,v 1.1 2008-11-10 10:06:15 mpostelnicu Exp $
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 2005-2007 Julian Hyde and others
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
*/
package mondrian.olap;

import mondrian.calc.ResultStyle;

/**
 * Allows expressions to be evaluated native, e.g. in SQL.
 *
 * @author av
 * @since Nov 11, 2005
 * @version $Id: NativeEvaluator.java,v 1.1 2008-11-10 10:06:15 mpostelnicu Exp $
 */

public interface NativeEvaluator {
    Object execute(ResultStyle resultStyle);
}

// End NativeEvaluator.java
