/*
// $Id: ParameterCompilable.java,v 1.1 2008-11-10 10:07:20 mpostelnicu Exp $
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 2006-2006 Julian Hyde
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
*/
package mondrian.calc;

import mondrian.olap.Parameter;

/**
 * Extension to {@link mondrian.olap.Parameter} which allows compilation.
 *
 * @author jhyde
 * @version $Id: ParameterCompilable.java,v 1.1 2008-11-10 10:07:20 mpostelnicu Exp $
 * @since Jul 22, 2006
 */
public interface ParameterCompilable extends Parameter {
    Calc compile(ExpCompiler compiler);
}

// End ParameterCompilable.java
