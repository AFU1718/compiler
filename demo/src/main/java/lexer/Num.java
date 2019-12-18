package lexer;

public class Num extends Token {
    private final int value;

    public Num(int value) {
        super(Tag.NUM);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Num{" +
                "value=" + value +
                '}';
    }
}
