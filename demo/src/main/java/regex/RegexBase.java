package regex;

public class RegexBase {
    //c的取值为Ø,ε,1234567890,a-zA-Z,
    private String c;

    public RegexBase() {
    }

    public RegexBase(String c) {
        this.c = c;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }


    @Override
    public String toString() {
        return this.c;
    }
}

