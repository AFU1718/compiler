package Parser;

import cfg.*;
import lexer.Token;

import java.util.ArrayDeque;
import java.util.List;

public class AnalyseTableLL1Parser {
    private CFG cfg;
    private AnalyseTableLL1 analyseTable;
    private List<Token> text;
    private Integer currentPos = 0;
    private ArrayDeque<Token> stack = new ArrayDeque();

    public AnalyseTableLL1Parser(List<Token> text, CFG cfg) {
        this.text = text;
        this.cfg = cfg;
        analyseTable=CFGUtils.analyseTableLL1(cfg);
        stack.push(new Token(-1));
        stack.push(this.cfg.getStart());
    }
    
    public void parse() {
        while (true) {
            if (stack.getFirst().getTag() == -1 && text.get(currentPos).getTag() == -1) {
                System.out.println("分析成功");
                break;
            } else {
                Token top = stack.pop();
                if (NonTerminal.class.isInstance(top)) {
                    Integer TColumn = analyseTable.getTMapping().get(text.get(currentPos).getTag());
                    Integer NTRow = analyseTable.getNTMapping().get((((NonTerminal) top).getMark()));
                    Rule rule = analyseTable.getTable()[NTRow][TColumn];
                    List<Token> right = rule.getRight();
                    if (right ==null){
                        System.out.println("语法出错");
                        break;
                    }else if(right.size()!=1 || right.get(0).getTag()!=0){
                        for (int i=right.size()-1;i>=0;i--){
                            stack.push(right.get(i));
                        }
                    }
                }else if(top.getTag()==text.get(currentPos).getTag()){
                    advance();
                }else{
                    System.out.println("语法出错");
                    break;
                }
            }
        }
    }

    private void advance() {
        currentPos = currentPos + 1;
    }
}
