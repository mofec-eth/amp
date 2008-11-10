/*
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 2004-2005 TONBELLER AG
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
*/
package mondrian.rolap.cache;

import java.util.*;

/**
 * An implementation of {@link SmartCache} that uses hard
 * references. Used for testing.
 *
 * @version $Id: HardSmartCache.java,v 1.1 2008-11-10 10:08:08 mpostelnicu Exp $
 */
public class HardSmartCache <K, V> implements SmartCache <K, V> {
    Map<K, V> cache = new HashMap<K, V>();

    public V put(K key, V value) {
        return cache.put(key, value);
    }

    public V get(K key) {
        return cache.get(key);
    }

    public void clear() {
        cache.clear();
    }

    public int size() {
        return cache.size();
    }

    public Iterator<Map.Entry<K, V>> iterator() {
        return cache.entrySet().iterator();
    }
}

// End HardSmartCache.java
