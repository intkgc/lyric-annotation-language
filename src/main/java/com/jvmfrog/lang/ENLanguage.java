package com.jvmfrog.lang;

public class ENLanguage implements Language {
    @Override
    public String intro() {
        return "Intro";
    }

    @Override
    public String verse() {
        return "Verse";
    }

    @Override
    public String preChorus() {
        return "Pre-chorus";
    }

    @Override
    public String chorus() {
        return "Chorus";
    }

    @Override
    public String bridge() {
        return "Bridge";
    }

    @Override
    public String outro() {
        return "Outro";
    }
}
