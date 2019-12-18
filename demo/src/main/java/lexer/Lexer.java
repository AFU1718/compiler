package lexer;

import java.util.*;

public class Lexer {

    private static Token stringToToken(String s){
        switch (s){
            case "(" : return new Token(112);
            case ")" : return new Token(113);
            case "+" : return new Token(100);
            case "-" : return new Token(101);
            case "*" : return new Token(102);
            case "/" : return new Token(103);
            case "^" : return new Token(114);
            case "#" : return new Token(-1);
            default:
                return new Num(Integer.valueOf(s));
        }
    }
    public static List<Token> stringListToTokenList(List<String> text){
        List<Token> tokenList=new ArrayList<>();
        for (String word:text) {
            tokenList.add(stringToToken(word));
        }
        return tokenList;
    }




}
