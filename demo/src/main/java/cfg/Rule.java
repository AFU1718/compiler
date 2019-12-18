package cfg;

import lexer.Token;

import java.util.List;

public class Rule {
    private NonTerminal left;
    private List<Token> right;

    public Rule(NonTerminal left, List<Token> right) {
        this.left = left;
        this.right = right;
    }

    public NonTerminal getLeft() {
        return left;
    }

    public void setLeft(NonTerminal left) {
        this.left = left;
    }

    public List<Token> getRight() {
        return right;
    }

    public void setRight(List<Token> right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
