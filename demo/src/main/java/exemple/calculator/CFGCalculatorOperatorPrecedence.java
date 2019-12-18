package exemple.calculator;

import cfg.CFG;
import cfg.NonTerminal;
import cfg.Rule;
import lexer.Token;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CFGCalculatorOperatorPrecedence {
    public static final CFG CFG_CALCULATOR_OPERATOR_PRECEDENCE;

    static{
        // 算符优先文法
        CFG_CALCULATOR_OPERATOR_PRECEDENCE = new CFG();
        NonTerminal E = new NonTerminal(true, "E");
        NonTerminal T = new NonTerminal(false, "T");
        NonTerminal F= new NonTerminal(false, "F");
        NonTerminal P= new NonTerminal(false, "P");

        List<NonTerminal> NTList=Arrays.asList(E,T,F,P);

        List<Token> TList=Arrays.asList(new Token(112),new Token(113),new Token(114),
                new Token(100),new Token(102),new Token(1),new Token(-1));

        NonTerminal start=E;

        Map<NonTerminal,List<Rule>> ruleListMap=new HashMap<>();
        ruleListMap.put(E,Arrays.asList(new Rule(E,Arrays.asList(E,new Token(100),T)),
                new Rule(E,Arrays.asList(T))));
        ruleListMap.put(T,Arrays.asList(new Rule(T,Arrays.asList(T,new Token(102),F)),
                new Rule(T,Arrays.asList(F))));
        ruleListMap.put(F,Arrays.asList(new Rule(F,Arrays.asList(P,new Token(114),F)),
                new Rule(F,Arrays.asList(P))));
        ruleListMap.put(P,Arrays.asList(new Rule(P,Arrays.asList(new Token(112),E,new Token(113))),
                new Rule(P,Arrays.asList(new Token(1)))));

        CFG_CALCULATOR_OPERATOR_PRECEDENCE.setNTList(NTList);
        CFG_CALCULATOR_OPERATOR_PRECEDENCE.setTList(TList);
        CFG_CALCULATOR_OPERATOR_PRECEDENCE.setStart(start);
        CFG_CALCULATOR_OPERATOR_PRECEDENCE.setRuleListMap(ruleListMap);

    }
}
