package studio.jvmfrog.lal.parser;

public class Token {
    private TokenType type;
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
        return type + "{'" + data + "'}";
    }
}
