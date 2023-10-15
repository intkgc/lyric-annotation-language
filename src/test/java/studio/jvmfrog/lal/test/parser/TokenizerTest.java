package studio.jvmfrog.lal.test.parser;

import org.junit.jupiter.api.Test;
import studio.jvmfrog.lal.parser.LALParser;
import studio.jvmfrog.lal.parser.Token;

public class TokenizerTest {
    String testInput = """
            >>    ru
            
            #VERSE
            @1-2:    Прикол
            
            ТЕКСТ ТЕСТ
                        
            #CHORUS
            ПРИПЕВ
            ПРИПЕВ
            ПРИПЕВ
            ПРИПЕВ
            
            >en
            #VERSE
            WOW english text
            <
            
            #VERSE
            Снова на русском
            f
            """;

    @Test
    void test1() {
        LALParser parser = new LALParser();
        parser.tokenize(testInput);
        System.out.println("Global lang: " + parser.getGlobalLanguage());
        for(Token token : parser.getTokens()){
            System.out.println(token);
        }
    }
}
