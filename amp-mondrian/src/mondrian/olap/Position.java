/*
// $Id: Position.java,v 1.1 2008-11-10 10:06:30 mpostelnicu Exp $
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 2001-2002 Kana Software, Inc.
// Copyright (C) 2001-2007 Julian Hyde and others
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
//
// jhyde, 6 August, 2001
*/

package mondrian.olap;

import java.util.List;

/**
 * A <code>Position</code> is an item on an {@link Axis}.  It contains
 * one or more {@link Member}s.
 *
 * @author jhyde
 * @since 6 August, 2001
 * @version $Id: Position.java,v 1.1 2008-11-10 10:06:30 mpostelnicu Exp $
 */
public interface Position extends List<Member> {
}

// End Position.java
