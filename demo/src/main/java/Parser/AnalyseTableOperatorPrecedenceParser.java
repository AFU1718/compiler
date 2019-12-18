package Parser;

import cfg.AnalyseTableOperatorPrecedence;
import cfg.CFG;
import cfg.CFGUtils;
import lexer.Token;

import java.util.ArrayDeque;
import java.util.List;

public class AnalyseTableOperatorPrecedenceParser {

    private CFG cfg;
    private AnalyseTableOperatorPrecedence analyseTable;
    private List<Token> text;
    private Integer currentPos = 0;
    private ArrayDeque<Token> stack = new ArrayDeque();

    public AnalyseTableOperatorPrecedenceParser(List<Token> text, CFG cfg) {
        this.text = text;
        this.cfg = cfg;
        analyseTable=CFGUtils.analyseTableOperatorPrecedence(cfg);
        stack.push(new Token(-1));
        stack.push(this.cfg.getStart());
    }

    public void parse() {

    }

    private void advance() {
        currentPos = currentPos + 1;
    }

}
