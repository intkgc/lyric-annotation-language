package com.jvmfrog.parser;

public class LyricAnnotation {
    public boolean verseAnnotation = false;
    public int annotatedLinesStart = -1;
    public int annotatedLinesEnd = -1;
    public String annotation;

    public LyricAnnotation() {
        verseAnnotation = true;
    }

    public LyricAnnotation(int start, int end) {
        annotatedLinesStart = start;
        annotatedLinesEnd = end;
    }
}
