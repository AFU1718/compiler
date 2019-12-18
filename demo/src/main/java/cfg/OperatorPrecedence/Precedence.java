package cfg.OperatorPrecedence;

public class Precedence {
    protected Integer precedence;

    public Integer getPrecedence() {
        return precedence;
    }

    public void setPrecedence(Integer precedence) {
        this.precedence = precedence;
    }

    @Override
    public String toString() {
        return "Precedence{" +
                "precedence=" + precedence +
                '}';
    }
}
