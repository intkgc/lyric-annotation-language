package studio.jvmfrog.lal.test.parser;

import org.junit.jupiter.api.Test;
import studio.jvmfrog.lal.lexer.LALLexer;
import studio.jvmfrog.lal.lexer.LALTokenRenderer;
import studio.jvmfrog.lal.lexer.Token;

public class TokenizerTest {
    String testInput = """
            >>ru
            #VERSE
            @1-1:аннотация для первой строки
                        
            Я написал какой-то бред
            Потому что я ленивый
            
            #VERSE
            ТЕСТ
            
            #VERSE
            БРЕД
            """;

    @Test
    void test1() {
        LALLexer parser = new LALLexer();
        parser.tokenize(testInput);
        System.out.println("Global lang: " + parser.getGlobalLanguage());
        for (Token token : parser.getTokens()) {
            System.out.println(token);
        }
    }

    @Test
    void test2() {
        LALLexer lexer = new LALLexer();
        lexer.tokenize(testInput);
        System.out.println(new LALTokenRenderer(lexer).render());
    }
}
