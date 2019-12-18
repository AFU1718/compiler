package Parser;

import cfg.CFG;
import lexer.Num;
import lexer.Token;

import java.util.ArrayDeque;
import java.util.List;

public class RecursiveDescentParser {
    private List<Token> text;
    private CFG cfg;
    private Integer currentPos=0;
    private Integer result;
    private ArrayDeque<String> LRD=new ArrayDeque<>();

    public RecursiveDescentParser(List<Token> text, CFG cfg) {
        this.text = text;
        this.cfg = cfg;
    }

    private void parse_S(){
        parse_E();
        if (isSameToken(text.get(currentPos),new Token(-1))){
            success("解析成功");
        }else{
            error("没有以#结束");
        }
    }

    private void parse_E(){
        parse_T();
        parse_E_PRIME();

    }
    private void parse_E_PRIME(){
        if (isSameToken(text.get(currentPos),new Token(100))){
            advance();
            parse_T();
            Integer pop1 = Integer.valueOf(LRD.pop());
            Integer pop2 = Integer.valueOf(LRD.pop());
            LRD.push(String.valueOf(pop1+pop2));
            parse_E_PRIME();

        }

    }
    private void parse_T(){
        parse_F();
        parse_T_PRIME();
    }
    private void parse_T_PRIME(){
        if (isSameToken(text.get(currentPos),new Token(102))){
            advance();
            parse_F();
            Integer pop1 = Integer.valueOf(LRD.pop());
            Integer pop2 = Integer.valueOf(LRD.pop());
            LRD.push(String.valueOf(pop1*pop2));
            parse_T_PRIME();

        }

    }
    private void parse_F(){
        if (isSameToken(text.get(currentPos),new Token(112))){
            advance();
            parse_E();
            if (isSameToken(text.get(currentPos),new Token(113))){
                advance();
            }else {
                error("没有右括号");
            }
        }else if (isSameToken(text.get(currentPos),new Token(1))){
            LRD.push(String.valueOf(((Num)text.get(currentPos)).getValue()));
            advance();
        }else{
            error("语法错误F");
        }
    }

    private void advance(){
        currentPos=currentPos+1;
    }
    public void parse(){
        parse_S();
    }

    private void error(String errorMsg){
        System.out.println(errorMsg);
    }
    private void success(String successMsg){
        System.out.println(successMsg);
    }

    private boolean isSameToken(Token token1,Token token2){
        return token1.getTag()==token2.getTag();
    }

    public List<Token> getText() {
        return text;
    }

    public void setText(List<Token> text) {
        this.text = text;
    }

    public CFG getCfg() {
        return cfg;
    }

    public void setCfg(CFG cfg) {
        this.cfg = cfg;
    }

    public Integer getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(Integer currentPos) {
        this.currentPos = currentPos;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public ArrayDeque<String> getLRD() {
        return LRD;
    }

    public void setLRD(ArrayDeque<String> LRD) {
        this.LRD = LRD;
    }
}
