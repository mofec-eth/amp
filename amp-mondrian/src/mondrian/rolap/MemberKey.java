/*
// $Id: MemberKey.java,v 1.1 2008-11-10 10:06:56 mpostelnicu Exp $
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 2002-2002 Kana Software, Inc.
// Copyright (C) 2002-2005 Julian Hyde and others
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
//
// jhyde, 21 March, 2002
*/
package mondrian.rolap;

/**
 * <code>MemberKey</code> todo:
 *
 * @author jhyde
 * @since 21 March, 2002
 * @version $Id: MemberKey.java,v 1.1 2008-11-10 10:06:56 mpostelnicu Exp $
 */
class MemberKey {
    private final RolapMember parent;
    private final Object value;
    MemberKey(RolapMember parent, Object value) {
        this.parent = parent;
        this.value = value;
    }
    // override Object
    public boolean equals(Object o) {
        if (!(o instanceof MemberKey)) {
            return false;
        }
        MemberKey other = (MemberKey) o;
        return (other.parent == this.parent) &&
            other.value.equals(this.value);
    }
    // override Object
    public int hashCode() {
        return ((parent == null)
            ? 0 
            : parent.hashCode() << 16) ^ value.hashCode();
    }
}

// End MemberKey.java
