package lexer;

public class Token {
    private final int tag;

    public Token(int tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return "Token{" +
                "tag=" + tag +
                '}';
    }
}
