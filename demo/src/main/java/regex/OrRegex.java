package regex;

public class OrRegex extends RegexBase{
    private RegexBase leftRegex;
    private RegexBase rightRegex;


    public OrRegex(RegexBase leftRegex, RegexBase rightRegex) {
        this.leftRegex = leftRegex;
        this.rightRegex = rightRegex;
    }

    public RegexBase getLeftRegex() {
        return leftRegex;
    }

    public void setLeftRegex(RegexBase leftRegex) {
        this.leftRegex = leftRegex;
    }

    public RegexBase getRightRegex() {
        return rightRegex;
    }

    public void setRightRegex(RegexBase rightRegex) {
        this.rightRegex = rightRegex;
    }

    @Override
    public String toString() {
        return "("+leftRegex.toString()+"|"+rightRegex.toString()+")";
    }
}
