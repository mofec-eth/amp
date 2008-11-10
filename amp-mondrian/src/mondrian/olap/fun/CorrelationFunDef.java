/*
// $Id: CorrelationFunDef.java,v 1.1 2008-11-10 10:05:27 mpostelnicu Exp $
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 2006-2007 Julian Hyde
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
*/
package mondrian.olap.fun;

import mondrian.olap.FunDef;
import mondrian.olap.Evaluator;
import mondrian.olap.Dimension;
import mondrian.calc.Calc;
import mondrian.calc.ExpCompiler;
import mondrian.calc.ListCalc;
import mondrian.calc.impl.ValueCalc;
import mondrian.calc.impl.AbstractDoubleCalc;
import mondrian.mdx.ResolvedFunCall;

import java.util.List;

/**
 * Definition of the <code>Correlation</code> MDX function.
 *
 * @author jhyde
 * @version $Id: CorrelationFunDef.java,v 1.1 2008-11-10 10:05:27 mpostelnicu Exp $
 * @since Mar 23, 2006
 */
class CorrelationFunDef extends AbstractAggregateFunDef {
    static final ReflectiveMultiResolver Resolver = new ReflectiveMultiResolver(
            "Correlation",
            "Correlation(<Set>, <Numeric Expression>[, <Numeric Expression>])",
            "Returns the correlation of two series evaluated over a set.",
            new String[]{"fnxn","fnxnn"},
            CorrelationFunDef.class);

    public CorrelationFunDef(FunDef dummyFunDef) {
        super(dummyFunDef);
    }

    public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        final ListCalc listCalc =
                compiler.compileList(call.getArg(0));
        final Calc calc1 =
                compiler.compileScalar(call.getArg(1), true);
        final Calc calc2 = call.getArgCount() > 2 ?
                compiler.compileScalar(call.getArg(2), true) :
                new ValueCalc(call);
        return new AbstractDoubleCalc(call, new Calc[] {listCalc, calc1, calc2}) {
            public double evaluateDouble(Evaluator evaluator) {
                List memberList = evaluateCurrentList(listCalc, evaluator);
                return correlation(evaluator.push(),
                        memberList, calc1, calc2);
            }

            public boolean dependsOn(Dimension dimension) {
                return anyDependsButFirst(getCalcs(), dimension);
            }
        };
    }
}

// End CorrelationFunDef.java
