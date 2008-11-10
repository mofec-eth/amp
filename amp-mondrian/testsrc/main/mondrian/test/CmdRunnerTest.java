/*
// $Id: CmdRunnerTest.java,v 1.1 2008-11-10 10:05:12 mpostelnicu Exp $
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 2006-2006 Julian Hyde
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
*/
package mondrian.test;

import mondrian.olap.Connection;
import mondrian.tui.CmdRunner;

import java.io.*;

/**
 * Unit test for {@link mondrian.tui.CmdRunner}.
 *
 * @author jhyde
 * @version $Id: CmdRunnerTest.java,v 1.1 2008-11-10 10:05:12 mpostelnicu Exp $
 * @since Jun 2, 2006
 */
public class CmdRunnerTest extends FoodMartTestCase {
    protected DiffRepository getDiffRepos() {
        return DiffRepository.lookup(CmdRunnerTest.class);
    }
    public CmdRunnerTest() {
    }
    public CmdRunnerTest(String name) {
        super(name);
    }

    public void testQuery() throws IOException {
        doTest();
    }
    public void test7731() throws IOException {
        doTest();
    }
    protected void doTest() {
        final DiffRepository diffRepos = getDiffRepos();
        String input = diffRepos.expand("input", "${input}");
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        final CmdRunnerTrojan cmdRunner = new CmdRunnerTrojan(null, pw);
        cmdRunner.commandLoop(new StringReader(input), false);
        pw.flush();
        String output = sw.toString();
        diffRepos.assertEquals("output", "${output}", output);
    }

    private class CmdRunnerTrojan extends CmdRunner {
        public CmdRunnerTrojan(CmdRunner.Options options, PrintWriter out) {
            super(options, out);
        }

        public void commandLoop(Reader in, boolean interactive) {
            super.commandLoop(in, interactive);
        }

        public Connection getConnection() {
            return CmdRunnerTest.this.getConnection();
        }
    }
}

// End CmdRunnerTest.java
