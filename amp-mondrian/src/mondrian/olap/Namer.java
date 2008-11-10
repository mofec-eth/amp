/*
// $Id: Namer.java,v 1.1 2008-11-10 10:06:14 mpostelnicu Exp $
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 2000-2002 Kana Software, Inc.
// Copyright (C) 2001-2005 Julian Hyde and others
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
//
*/

package mondrian.olap;

/**
 * Namer contains the methods to retrieve localized attributes
 */
public interface Namer {
    public String getLocalResource(String uName, String defaultValue);
}


// End Namer.java
