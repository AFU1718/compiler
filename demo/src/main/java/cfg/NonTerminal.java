package cfg;

import lexer.Token;

public class NonTerminal extends Token {
    private final boolean start;
    private final String mark;

    public NonTerminal(boolean start, String mark) {
        super(-1);
        this.start = start;
        this.mark = mark;
    }

    public String getMark() {
        return mark;
    }

    @Override
    public String toString() {
        return "NonTerminal{" +
                "start=" + start +
                ", mark='" + mark + '\'' +
                '}';
    }
}
