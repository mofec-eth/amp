/*
// $Id: LevelCalc.java,v 1.1 2008-11-10 10:07:19 mpostelnicu Exp $
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 2006-2007 Julian Hyde
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
*/
package mondrian.calc;

import mondrian.olap.Evaluator;
import mondrian.olap.Level;

/**
 * Expression which yields a {@link mondrian.olap.Level}.
 *
 * <p>When implementing this interface, it is convenient to extend
 * {@link mondrian.calc.impl.AbstractLevelCalc}, but it is not required.
 *
 * @author jhyde
 * @version $Id: LevelCalc.java,v 1.1 2008-11-10 10:07:19 mpostelnicu Exp $
 * @since Sep 26, 2005
 */
public interface LevelCalc extends Calc {
    /**
     * Evaluates this expression to yield a level.
     *
     * <p>Never returns null.
     *
     * @param evaluator Evaluation context
     * @return a level
     */
    Level evaluateLevel(Evaluator evaluator);
}

// End LevelCalc.java
