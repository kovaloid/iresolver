package com.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import com.koval.jresolver.classifier.Launcher;
import com.koval.jresolver.manager.Manager;

@Category(FunctionalTests.class)
public class TestsLauncher extends Assert {
    private final List<String> args = new ArrayList<>();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        Manager.setTest("classifier");
    }

    @Test
    public void testLauncher() throws Exception {
        File file = new File(Manager.getDataDirectory() + "DataSet.txt");
        if (file.exists()) {
            file.delete();
        }
        args.clear();
        args.add("prepare");
        String[] args1 = {};
        Launcher.main(args.toArray(args1));
        assertTrue(Manager.checkClassifierPrepare());
        args.clear();
        args.add("configure");
        Launcher.main(args.toArray(args1));
        assertTrue(Manager.checkClassifierConfigure());
    }

    @Test
    public void testEmptyArgs() throws Exception {
        String[] emptyArgs = {};
        File file = new File(Manager.getDataDirectory() + "DataSet.txt");
        if (file.exists()) {
            file.delete();
        }
        Launcher.main(emptyArgs);
        assertFalse(Manager.checkClassifierPrepare());
    }

    @Test
    public void testExtraArgs() throws Exception {
        String[] extraArgs = {"test1", "test2", "test3"};
        File file = new File(Manager.getDataDirectory() + "DataSet.txt");
        if (file.exists()) {
            file.delete();
        }
        Launcher.main(extraArgs);
        assertFalse(Manager.checkClassifierPrepare());
    }

    /*@Test
    public void testPredict() throws Exception {
        String[] argsPredict = {"predict", "lalalala jshdfj djfhwk j q wkjefh lqw dkjfw kjshf qkj hkdwjf wlej hfkwjefb wke kejfwkje we"};
        if (!Manager.checkClassifierConfigure()) {
            if (!Manager.checkClassifierPrepare()) {
                String[] pr = {"prepare"};
                Launcher.main(pr);
            }
            String[] conf = {"configure"};
            Launcher.main(conf);
        }
        Launcher.main(argsPredict);
    }*/
}
