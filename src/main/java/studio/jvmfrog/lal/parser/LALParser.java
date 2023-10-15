package studio.jvmfrog.lal.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LALParser {
    private ArrayList<Token> tokens;
    private String input;
    private String globalLanguage = null;

    private static final String ANNOTATION_LINE_REGEX = "@\\d*?-\\d*?:.*";
    private static final String SECTION_LINE_REGEX = "#\\S*";
    private static final String ANNOTATION_FORMAT_REGEX = "@\\d*?-\\d*?:";
    private static final String LANGUAGE_CHANGE_REGEX = ">\\s*\\S+";
    private static final String LANGUAGE_RESET_SYMBOL = "<";
    private static final String NEWLINE_REGEX = "\\r?\\n";
    private static final String GLOBAL_LANGUAGE_SET_TAG = ">>";

    public void tokenize(String input) {
        if (this.input == null || !this.input.equals(input)) {
            this.input = input;
            this.tokens = new ArrayList<>();
            String[] lines = input.split(NEWLINE_REGEX);

            boolean globalLanguageIsSet = false;
            boolean parsingMultiline = false;

            StringBuilder stringBuilder = new StringBuilder();
            Token token = null;
            for (String line : lines) {
                String trimmed = line.trim();
                if (trimmed.matches(LANGUAGE_CHANGE_REGEX) ||
                    trimmed.matches(LANGUAGE_RESET_SYMBOL)) continue;
                if (parsingMultiline) {
                    if (!trimmed.isBlank())
                        stringBuilder.append("\n").append(trimmed);
                    else {
                        token.setData(stringBuilder.toString());
                        tokens.add(token);
                        token = null;
                        parsingMultiline = false;
                        continue;
                    }

                    continue;
                }
                if (!globalLanguageIsSet && trimmed.startsWith(GLOBAL_LANGUAGE_SET_TAG)) {
                    globalLanguageIsSet = true;
                    setGlobalLanguage(trimmed.substring(trimmed.lastIndexOf(">") + 1).trim());
                } else if (trimmed.matches(ANNOTATION_LINE_REGEX)) {
                    Pattern pattern = Pattern.compile(ANNOTATION_FORMAT_REGEX);
                    Matcher matcher = pattern.matcher(trimmed);
                    if (matcher.find()) {
                        tokens.add(new Token(TokenType.ANNOTATION_RELATION, matcher.group()));
                        token = new Token(TokenType.ANNOTATION);
                        parsingMultiline = true;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(trimmed.substring(matcher.end()).trim());
                    }
                } else if (trimmed.matches(SECTION_LINE_REGEX)) {
                    tokens.add(new Token(TokenType.SECTION_TYPE, trimmed.substring(1)));
                } else if (!trimmed.isBlank()) {
                    token = new Token(TokenType.SECTION);
                    parsingMultiline = true;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(trimmed);
                }
            }
            if (token != null) {
                token.setData(stringBuilder.toString());
                tokens.add(token);
            }
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
