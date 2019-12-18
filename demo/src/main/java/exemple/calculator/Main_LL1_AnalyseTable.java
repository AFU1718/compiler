package exemple.calculator;

import Parser.AnalyseTableLL1Parser;
import graph.GraphUtils;
import lexer.Token;
import lexer.Lexer;

import java.util.List;

import static exemple.calculator.CFGCalculatorLL1.CFG_CALCULATOR_LL1;
import static exemple.calculator.GraphCalculator.DFA_GRAPH_CALCULATOR;

public class Main_LL1_AnalyseTable {

    public static void main(String[] args) {

        List<String> stringList = GraphUtils.textToStringList(DFA_GRAPH_CALCULATOR, "3*7+(4)+5*(1+1)* 4 # ");
        List<Token> tokenList = Lexer.stringListToTokenList(stringList);
        AnalyseTableLL1Parser analyseTableParser = new AnalyseTableLL1Parser(tokenList, CFG_CALCULATOR_LL1);
        analyseTableParser.parse();


    }
}
