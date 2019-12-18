package exemple.calculator;

import cfg.CFG;
import cfg.NonTerminal;
import cfg.Rule;
import lexer.Token;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CFGCalculatorLL1 {
    public static final CFG CFG_CALCULATOR_LL1;

    static{
        // LL(1)文法
        CFG_CALCULATOR_LL1 = new CFG();
        NonTerminal E = new NonTerminal(true, "E");
        NonTerminal E_PRIME = new NonTerminal(false, "E_PRIME");
        NonTerminal T = new NonTerminal(false, "T");
        NonTerminal T_PRIME = new NonTerminal(false, "T_PRIME");
        NonTerminal F= new NonTerminal(false, "F");

        List<NonTerminal> NTList=Arrays.asList(E,E_PRIME,T,T_PRIME,F);

        List<Token> TList=Arrays.asList(new Token(112),new Token(113),
                new Token(100),new Token(102),new Token(1),new Token(0),new Token(-1));

        NonTerminal start=E;

        Map<NonTerminal,List<Rule>> ruleListMap=new HashMap<>();
        ruleListMap.put(E,Arrays.asList(new Rule(E,Arrays.asList(T,E_PRIME))));
        ruleListMap.put(E_PRIME,Arrays.asList(new Rule(E_PRIME,Arrays.asList(new Token(100),T,E_PRIME)),
                new Rule(E_PRIME,Arrays.asList(new Token(0)))));
        ruleListMap.put(T,Arrays.asList(new Rule(T,Arrays.asList(F,T_PRIME))));
        ruleListMap.put(T_PRIME,Arrays.asList(new Rule(T_PRIME,Arrays.asList(new Token(102),F,T_PRIME)),
                new Rule(T_PRIME,Arrays.asList(new Token(0)))));
        ruleListMap.put(F,Arrays.asList(new Rule(F,Arrays.asList(new Token(112),E,new Token(113))),
                new Rule(F,Arrays.asList(new Token(1)))));

        CFG_CALCULATOR_LL1.setNTList(NTList);
        CFG_CALCULATOR_LL1.setTList(TList);
        CFG_CALCULATOR_LL1.setStart(start);
        CFG_CALCULATOR_LL1.setRuleListMap(ruleListMap);

    }

}
