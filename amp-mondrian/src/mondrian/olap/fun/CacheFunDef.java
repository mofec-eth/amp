/*
// $Id: CacheFunDef.java,v 1.1 2008-11-10 10:05:21 mpostelnicu Exp $
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 2005-2007 Julian Hyde and others
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
*/
package mondrian.olap.fun;

import mondrian.olap.*;
import mondrian.olap.type.Type;
import mondrian.olap.type.SetType;
import mondrian.calc.*;
import mondrian.calc.impl.GenericCalc;
import mondrian.mdx.ResolvedFunCall;

import java.io.PrintWriter;

/**
 * Definition of the <code>Cache</code> system function, which is smart enough
 * to evaluate its argument only once.
 *
 * @author jhyde
 * @since 2005/8/14
 * @version $Id: CacheFunDef.java,v 1.1 2008-11-10 10:05:21 mpostelnicu Exp $
 */
public class CacheFunDef extends FunDefBase {
    static final String NAME = "Cache";
    private static final String SIGNATURE = "Cache(<<Exp>>)";
    private static final String DESCRIPTION = "Evaluates and returns its sole argument, applying statement-level caching";
    private static final Syntax SYNTAX = Syntax.Function;
    static final CacheFunResolver Resolver = new CacheFunResolver();

    CacheFunDef(
            String name,
            String signature,
            String description,
            Syntax syntax,
            int category,
            Type type) {
        super(name, signature, description, syntax,
                category, new int[] {category});
        Util.discard(type);
    }

    public void unparse(Exp[] args, PrintWriter pw) {
        args[0].unparse(pw);
    }

    public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        final Exp exp = call.getArg(0);
        final ExpCacheDescriptor cacheDescriptor =
                new ExpCacheDescriptor(exp, compiler);
        return new GenericCalc(call) {
            public Object evaluate(Evaluator evaluator) {
                return evaluator.getCachedResult(cacheDescriptor);
            }

            public Calc[] getCalcs() {
                return new Calc[] {cacheDescriptor.getCalc()};
            }

            public ResultStyle getResultStyle() {
                // cached lists are immutable
                if (type instanceof SetType) {
                    return ResultStyle.LIST;
                } else {
                    return ResultStyle.VALUE;
                }
            }
        };
    }

    public static class CacheFunResolver extends ResolverBase {
        CacheFunResolver() {
            super(NAME, SIGNATURE, DESCRIPTION, SYNTAX);
        }

        public FunDef resolve(
                Exp[] args, Validator validator, int[] conversionCount) {
            if (args.length != 1) {
                return null;
            }
            final Exp exp = args[0];
            final int category = exp.getCategory();
            final Type type = exp.getType();
            return new CacheFunDef(NAME, SIGNATURE, DESCRIPTION, SYNTAX,
                    category, type);
        }

        public boolean requiresExpression(int k) {
            return false;
        }
    }
}

// End CacheFunDef.java
