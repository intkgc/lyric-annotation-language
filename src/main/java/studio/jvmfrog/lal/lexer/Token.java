package studio.jvmfrog.lal.lexer;

public class Token {
    private final TokenType type;
    private String data;

    public Token(TokenType type) {
        this.type = type;
    }

    public Token(TokenType type, String data) {
        this.type = type;
        this.data = data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        String dataWithoutNL = data.replaceAll("\\n", "\\\\n");
        String dataRes = dataWithoutNL.length() >= 6 ? dataWithoutNL.substring(0, 6).trim() + "..." : dataWithoutNL;
        return dataRes + " : " + type;
    }
}
