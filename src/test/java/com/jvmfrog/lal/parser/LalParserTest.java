package com.jvmfrog.lal.parser;

import com.jvmfrog.parser.LalParser;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class LalParserTest {
    @Test
    public void test() throws URISyntaxException, IOException {
        String lal = FileUtils.readFileToString(
                new File(getClass().getResource("/test.lal").toURI()),
                "UTF-8");
        LalParser parser = new LalParser();
        parser.parse(lal);
        System.out.println(parser.getFullLyric());
    }
}
