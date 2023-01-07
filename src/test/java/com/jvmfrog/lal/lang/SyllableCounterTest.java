package com.jvmfrog.lal.lang;

import com.jvmfrog.lang.Language;
import com.jvmfrog.parser.LalParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SyllableCounterTest {
    @Test
    public void testRU() {
        LalParser.x();
        String input = "Привет мир. Этот текст содержит кучу текста :D";
        int count = Language.languages.get("ru").syllableCount(input);
        System.out.printf("Input: %s, Syllable count: %d%n", input, count);
        Assertions.assertEquals(13, count);
    }
}
