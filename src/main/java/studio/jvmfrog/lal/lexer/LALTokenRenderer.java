package studio.jvmfrog.lal.lexer;

import java.util.regex.Pattern;

public class LALTokenRenderer {
    private final LALLexer lexer;

    public LALTokenRenderer(LALLexer lexer) {
        this.lexer = lexer;
    }

    public String render() {
        return render(lexer.getGlobalLanguage());
    }

    public String render(String preferredLang) {
        StringBuilder builder = new StringBuilder();
        for (Token token : lexer.getTokens()) {
            switch (token.getType()) {
                case SECTION_TYPE -> builder.append("[ $")
                        .append(token.getData())
                        .append("$ ")
                        .append(countOf(builder, token.getData()))
                        .append(" ]")
                        .append("\n");
                case SECTION -> builder.append(token.getData()).append("\n\n");
            }
        }
        return builder.toString().trim();
    }

    private long countOf(StringBuilder input, String find) {
        return Pattern.compile(find)
                .matcher(input)
                .results()
                .count();
    }
}
