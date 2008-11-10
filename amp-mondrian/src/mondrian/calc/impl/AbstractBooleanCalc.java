/*
// $Id: AbstractBooleanCalc.java,v 1.1 2008-11-10 10:07:42 mpostelnicu Exp $
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 2006-2007 Julian Hyde
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
*/
package mondrian.calc.impl;

import mondrian.olap.Evaluator;
import mondrian.olap.Exp;
import mondrian.olap.type.BooleanType;
import mondrian.calc.*;

/**
 * Abstract implementation of the {@link mondrian.calc.BooleanCalc} interface.
 *
 * <p>The derived class must
 * implement the {@link #evaluateBoolean(mondrian.olap.Evaluator)} method,
 * and the {@link #evaluate(mondrian.olap.Evaluator)} method will call it.
 *
 * @author jhyde
 * @version $Id: AbstractBooleanCalc.java,v 1.1 2008-11-10 10:07:42 mpostelnicu Exp $
 * @since Sep 26, 2005
 */
public abstract class AbstractBooleanCalc
        extends AbstractCalc
        implements BooleanCalc {
    private final Calc[] calcs;

    public AbstractBooleanCalc(Exp exp, Calc[] calcs) {
        super(exp);
        this.calcs = calcs;
        // now supports int and double conversion (see AbstractExpCompiler.compileBoolean()
        // assert getType() instanceof BooleanType;
    }

    public Object evaluate(Evaluator evaluator) {
        return Boolean.valueOf(evaluateBoolean(evaluator));
    }

    public Calc[] getCalcs() {
        return calcs;
    }
}

// End AbstractBooleanCalc.java
