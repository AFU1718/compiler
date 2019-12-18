package exemple.calculator;

import cfg.AnalyseTableOperatorPrecedence;
import cfg.CFGUtils;
import cfg.NonTerminal;
import cfg.OperatorPrecedence.Precedence;
import lexer.Token;

import java.util.List;
import java.util.Map;

import static exemple.calculator.CFGCalculatorOperatorPrecedence.CFG_CALCULATOR_OPERATOR_PRECEDENCE;

public class Main {
    public static void main(String[] args) {
//        Map<NonTerminal, List<Token>> firstVTSet = CFGUtils.firstVTSet(CFG_CALCULATOR_OPERATOR_PRECEDENCE);
//        System.out.println(firstVTSet);
//
//        System.out.println("11111111111111111111111111");
//
//        Map<NonTerminal, List<Token>> lastVTSet = CFGUtils.lastVTSet(CFG_CALCULATOR_OPERATOR_PRECEDENCE);
//        System.out.println(lastVTSet);

        AnalyseTableOperatorPrecedence analyseTableOperatorPrecedence = CFGUtils.analyseTableOperatorPrecedence(CFG_CALCULATOR_OPERATOR_PRECEDENCE);
        System.out.println(analyseTableOperatorPrecedence.getTMapping());
        System.out.println("1111111111111111111111111111111");

        Precedence[][] table = analyseTableOperatorPrecedence.getTable();

        for(int x=0; x<table.length; x++) {
            for(int y=0; y<table[x].length; y++) {
                System.out.print(x+","+y+" :"+table[x][y]+" ");
            }
        }



    }
}
