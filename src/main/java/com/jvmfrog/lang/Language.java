package com.jvmfrog.lang;

import java.util.HashMap;

public interface Language {
    HashMap<String, Language> languages = new HashMap<>();

    default int syllableCount(String line) {
        return -1;
    }

    String intro();

    String verse();

    String preChorus();

    String chorus();

    String bridge();

    String outro();
}
