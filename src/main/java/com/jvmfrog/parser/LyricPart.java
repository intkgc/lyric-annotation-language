package com.jvmfrog.parser;

import java.util.ArrayList;

public class LyricPart {
    private String partSymbol;
    public final boolean isStatic;
    private int num = -1;

    public String lyric;

    public ArrayList<LyricAnnotation> annotations = new ArrayList<>();

    public LyricPart(String partSymbol, boolean isStatic) {
        this.partSymbol = partSymbol;
        this.isStatic = isStatic;
    }

    public String getPartSymbol() {
        return partSymbol;
    }

    public void setNum(int num) {
        partSymbol = partSymbol + num;
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    @Override
    public String toString() {
        return partSymbol + " " + num;
    }
}
