package com.jvmfrog.lang;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RULanguage implements Language {
    @Override
    public int syllableCount(String line) {
        Pattern syllable = Pattern.compile("[аоуэыяёеюи]+");
        Matcher matcher = syllable.matcher(line.toLowerCase());
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    @Override
    public String intro() {
        return "Интро";
    }

    @Override
    public String verse() {
        return "Куплет";
    }

    @Override
    public String preChorus() {
        return "Пред-припев";
    }

    @Override
    public String chorus() {
        return "Припев";
    }

    @Override
    public String bridge() {
        return "Бридж";
    }

    @Override
    public String outro() {
        return "Оутро";
    }
}
