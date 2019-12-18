package regex;

public class StarRegex extends RegexBase {
    private RegexBase regex;

    public StarRegex(RegexBase regex) {
        this.regex = regex;
    }

    public RegexBase getRegex() {
        return regex;
    }

    public void setRegex(RegexBase regex) {
        this.regex = regex;
    }

    @Override
    public String toString() {
        return "("+regex.toString()+")*";
    }
}
