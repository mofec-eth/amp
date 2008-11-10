/*
// $Id: FunInfo.java,v 1.1 2008-11-10 10:05:26 mpostelnicu Exp $
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 2005-2007 Julian Hyde and others
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
*/

package mondrian.olap.fun;

import mondrian.olap.FunDef;
import mondrian.olap.Syntax;
import mondrian.olap.Util;

import java.util.*;
import java.lang.reflect.Array;

/**
 * Support class for the {@link mondrian.tui.CmdRunner} allowing one to view
 * available functions and their syntax.
 *
 * @author Richard M. Emberson
 * @version $Id: FunInfo.java,v 1.1 2008-11-10 10:05:26 mpostelnicu Exp $
 */
public class FunInfo implements Comparable<FunInfo> {
    private final Syntax syntax;
    private final String name;
    private final String description;
    private final int[] returnTypes;
    private final int[][] parameterTypes;
    private String[] sigs;

    static FunInfo make(Resolver resolver) {
        FunDef funDef = resolver.getFunDef();
        if (funDef != null) {
            return new FunInfo(funDef);
        } else if (resolver instanceof MultiResolver) {
            return new FunInfo((MultiResolver) resolver);
        } else {
            return new FunInfo(resolver);
        }
    }

    FunInfo(FunDef funDef) {
        this.syntax = funDef.getSyntax();
        this.name = funDef.getName();
        assert name != null;
        assert syntax != null;
        this.returnTypes = new int[] { funDef.getReturnCategory() };
        this.parameterTypes = new int[][] { funDef.getParameterCategories() };

        // use explicit signature if it has one, otherwise generate a set
        this.sigs = funDef instanceof FunDefBase
            && ((FunDefBase) funDef).signature != null
            ? new String[] {((FunDefBase) funDef).signature}
            : makeSigs(syntax, name, returnTypes, parameterTypes);
        this.description = funDef.getDescription();
    }

    FunInfo(MultiResolver multiResolver) {
        this.syntax = multiResolver.getSyntax();
        this.name = multiResolver.getName();
        assert name != null;
        assert syntax != null;
        this.description = multiResolver.getDescription();

        String[] signatures = multiResolver.getSignatures();
        this.returnTypes = new int[signatures.length];
        this.parameterTypes = new int[signatures.length][];
        for (int i = 0; i < signatures.length; i++) {
            returnTypes[i] = FunUtil.decodeReturnCategory(signatures[i]);
            parameterTypes[i] = FunUtil.decodeParameterCategories(signatures[i]);
        }
        this.sigs = makeSigs(syntax, name, returnTypes, parameterTypes);
    }

    FunInfo(Resolver resolver) {
        this.syntax = resolver.getSyntax();
        this.name = resolver.getName();
        assert name != null;
        assert syntax != null;
        this.description = resolver.getDescription();
        this.returnTypes = null;
        this.parameterTypes = null;
        final String signature = resolver.getSignature();
        this.sigs = signature == null ? new String[0] :
                new String[] {signature};
    }

    public String[] getSignatures() {
        return sigs;
    }

    private static String[] makeSigs(
        Syntax syntax,
        String name,
        int[] returnTypes,
        int[][] parameterTypes)
    {
        if (parameterTypes == null) {
            return null;
        }

        String[] sigs = new String[parameterTypes.length];
        for (int i = 0; i < sigs.length; i++) {
            sigs[i] = syntax.getSignature(
                    name, returnTypes[i], parameterTypes[i]);
        }
        return sigs;
    }

    /**
     * Returns the syntactic type of the function.
     */
    public Syntax getSyntax() {
        return this.syntax;
    }

    /**
     * Returns the name of this function.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the description of this function.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the type of value returned by this function. Values are the same
     * as those returned by {@link mondrian.olap.Exp#getCategory()}.
     */
    public int[] getReturnCategories() {
        return this.returnTypes;
    }

    /**
     * Returns the types of the arguments of this function. Values are the same
     * as those returned by {@link mondrian.olap.Exp#getCategory()}. The
     * 0<sup>th</sup> argument of methods and properties are the object they
     * are applied to. Infix operators have two arguments, and prefix operators
     * have one argument.
     */
    public int[][] getParameterCategories() {
        return this.parameterTypes;
    }

    public int compareTo(FunInfo fi) {
        int c = this.name.compareTo(fi.name);
        if (c != 0) {
            return c;
        }
        final List<Object> pcList = toList(this.getParameterCategories());
        final String pc = pcList.toString();
        final List otherPcList = toList(fi.getParameterCategories());
        final String otherPc = otherPcList.toString();
        return pc.compareTo(otherPc);
    }

    public boolean equals(Object obj) {
        if (obj instanceof FunInfo) {
            final FunInfo that = (FunInfo) obj;
            if (!name.equals(that.name)) {
                return false;
            }
            final List<Object> pcList = toList(this.getParameterCategories());
            final List<Object> pcList2 = toList(that.getParameterCategories());
            return pcList.equals(pcList2);
        } else {
            return false;
        }
    }

    public int hashCode() {
        int h = name.hashCode();
        final List<Object> pcList = toList(this.getParameterCategories());
        return Util.hash(h, pcList);
    }

    private static List<Object> toList(Object a) {
        final List<Object> list = new ArrayList<Object>();
        if (a == null) {
            return list;
        }
        final int length = Array.getLength(a);
        for (int i = 0; i < length; i++) {
            final Object o = Array.get(a, i);
            if (o.getClass().isArray()) {
                list.add(toList(o));
            } else {
                list.add(o);
            }
        }
        return list;
    }
}

// End FunInfo.java
