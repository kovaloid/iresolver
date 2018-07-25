package com.jresolver.editor.core;

import java.io.File;
import java.io.IOException;


public final class RuleFinder {

    private RuleFinder() { /*UTILITY*/ }

    public static  File[] finder() throws IOException {
        return getRulesDir().listFiles((dir1, filename) -> filename.endsWith(".drl"));
    }

    public static File getRulesDir() throws IOException {
        if (new File("rules").exists()) {
            return new File("rules");
        } else if (new File("src/main/resources/rules").exists()) {
            return new File("src/main/resources/rules");
        } else if (new File("rule-engine/src/main/resources").exists()) {
            return new File("rule-engine/src/main/resources");
        }
        throw new IOException("Rules folder has not found");
    }
}
