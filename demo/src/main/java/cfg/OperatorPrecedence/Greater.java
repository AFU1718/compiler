package cfg.OperatorPrecedence;

public class Greater extends Precedence {

    public Greater() {
        this.precedence = 1;
    }

    @Override
    public String toString() {
        return "Greater{}";
    }
}
