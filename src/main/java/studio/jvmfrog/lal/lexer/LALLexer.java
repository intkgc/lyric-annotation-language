package studio.jvmfrog.lal.lexer;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LALLexer {
    private ArrayList<Token> tokens;
    private String input;
    private String globalLanguage = null;

    private static final String ANNOTATION_LINE_REGEX = "@\\d*?-\\d*?:.*";
    private static final String SECTION_LINE_REGEX = "#\\S*";
    private static final String ANNOTATION_FORMAT_REGEX = "@\\d*?-\\d*?:";
    private static final String LANGUAGE_CHANGE_REGEX = "^>\\s*[a-z]+";
    private static final String LANGUAGE_RESET_SYMBOL = "<";
    private static final String NEWLINE_REGEX = "\\r?\\n";
    private static final String GLOBAL_LANGUAGE_SET_REGEX = "^>>\\s*[a-z]+";
    private static final String GLOBAL_LANGUAGE_SET_TAG = ">>";

    private boolean globalLanguageIsSet = false;
    private boolean parsingMultiline = false;

    private StringBuilder multilineStringBuilder = new StringBuilder();
    private Token currentToken = null;

    public void tokenize(String input) {
        if (this.input == null || !this.input.equals(input)) {
            this.input = input;
            this.tokens = new ArrayList<>();
            String[] lines = input.split(NEWLINE_REGEX);

            for (String line : lines) {
                processLine(line.trim());
            }
            if (currentToken != null) {
                currentToken.setData(multilineStringBuilder.toString());
                tokens.add(currentToken);
            }
        }
    }

    private void processLine(String trimmed) {
        if (trimmed.matches(LANGUAGE_CHANGE_REGEX) ||
            trimmed.matches(LANGUAGE_RESET_SYMBOL)) return;

        if (parsingMultiline) {
            if (!trimmed.isBlank()) {
                multilineStringBuilder.append("\n").append(trimmed);
            } else {
                currentToken.setData(multilineStringBuilder.toString());
                tokens.add(currentToken);
                currentToken = null;
                parsingMultiline = false;
            }
        } else if (!globalLanguageIsSet && trimmed.matches(GLOBAL_LANGUAGE_SET_REGEX)) {
            globalLanguageIsSet = true;
            setGlobalLanguage(trimmed.substring(
                    trimmed.lastIndexOf(GLOBAL_LANGUAGE_SET_TAG) + 2).trim());
        } else if (trimmed.matches(ANNOTATION_LINE_REGEX)) {
            processAnnotationLine(trimmed);
        } else if (trimmed.matches(SECTION_LINE_REGEX)) {
            tokens.add(new Token(TokenType.SECTION_TYPE, trimmed.substring(1)));
        } else if (!trimmed.isBlank()) {
            currentToken = new Token(TokenType.SECTION);
            parsingMultiline = true;
            multilineStringBuilder = new StringBuilder();
            multilineStringBuilder.append(trimmed);
        }
    }

    private void processAnnotationLine(String line) {
        Pattern pattern = Pattern.compile(ANNOTATION_FORMAT_REGEX);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            tokens.add(new Token(TokenType.ANNOTATION_RELATION, matcher.group()));
            currentToken = new Token(TokenType.ANNOTATION);
            parsingMultiline = true;
            multilineStringBuilder = new StringBuilder();
            multilineStringBuilder.append(line.substring(matcher.end()).trim());
        }
    }

    public void setGlobalLanguage(String globalLanguage) {
        this.globalLanguage = globalLanguage;
    }

    public String getGlobalLanguage() {
        return globalLanguage;
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public static int[] onlyTextLines() {
        return null;
    }
}
