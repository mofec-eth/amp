/*
// $Id: CatalogLocator.java,v 1.1 2008-11-10 10:08:25 mpostelnicu Exp $
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 2005-2005 Julian Hyde
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
*/
package mondrian.spi;

/**
 * Abstract layer for locating catalog schema content.
 *
 * @author Gang Chen
 * @since December, 2005
 * @version $Id: CatalogLocator.java,v 1.1 2008-11-10 10:08:25 mpostelnicu Exp $
 */
public interface CatalogLocator {

    /**
     * @return URL complied string representation.
     */
    String locate(String catalogPath);

}

// End CatalogLocator.java
