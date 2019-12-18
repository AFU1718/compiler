package cfg;

import lexer.Token;

import java.util.List;
import java.util.Map;

public class CFG {
    private List<NonTerminal> NTList;
    private List<Token> TList;
    private NonTerminal start;
    private Map<NonTerminal,List<Rule>> ruleListMap;

    public List<NonTerminal> getNTList() {
        return NTList;
    }

    public void setNTList(List<NonTerminal> NTList) {
        this.NTList = NTList;
    }

    public List<Token> getTList() {
        return TList;
    }

    public void setTList(List<Token> TList) {
        this.TList = TList;
    }

    public NonTerminal getStart() {
        return start;
    }

    public void setStart(NonTerminal start) {
        this.start = start;
    }

    public Map<NonTerminal, List<Rule>> getRuleListMap() {
        return ruleListMap;
    }

    public void setRuleListMap(Map<NonTerminal, List<Rule>> ruleListMap) {
        this.ruleListMap = ruleListMap;
    }
}
