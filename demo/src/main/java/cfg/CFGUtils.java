package cfg;

import cfg.OperatorPrecedence.Equal;
import cfg.OperatorPrecedence.Greater;
import cfg.OperatorPrecedence.Precedence;
import cfg.OperatorPrecedence.Smaller;
import lexer.Token;

import java.util.*;

public class CFGUtils {

    public static Map<NonTerminal, List<Token>> NTFirstSet(CFG cfg) {
        Map<NonTerminal, List<Rule>> ruleListMap = cfg.getRuleListMap();

        Map<NonTerminal, List<Token>> firstSet = new HashMap<>();
        for (NonTerminal NT : cfg.getNTList()) {
            firstSet.put(NT, new ArrayList<>());
        }

        boolean modified = true;
        while (modified) {
            modified = false;
            for (Map.Entry<NonTerminal, List<Rule>> entry : ruleListMap.entrySet()) {
                NonTerminal NT = entry.getKey();
                List<Rule> ruleList = entry.getValue();
                List<Token> tempFirst = firstSet.get(NT);
                for (Rule rule : ruleList) {
                    List<Token> right = rule.getRight();
                    Token firstToken = right.get(0);
                    int size = right.size();
                    if (NonTerminal.class.isInstance(firstToken)) {
                        Token nextToken = firstToken;
                        int tokenPos = 0;
                        boolean previousEpsilon = true;
                        while (NonTerminal.class.isInstance(nextToken) && previousEpsilon) {
                            previousEpsilon = false;
                            List<Token> tokenList = firstSet.get(nextToken);
                            if (addAllNonEpsilonToken(tempFirst, tokenList)) {
                                modified = true;
                            }
                            if (containsToken(tokenList, new Token(0))) {
                                previousEpsilon = true;
                                if (tokenPos < size - 1) {
                                    tokenPos = tokenPos + 1;
                                    nextToken = right.get(tokenPos);
                                } else {
                                    if (addTerminalToken(tempFirst, new Token(0))) {
                                        modified = true;
                                    }
                                }
                            }
                        }
                    } else {
                        if (addTerminalToken(tempFirst, firstToken)) {
                            modified = true;
                        }
                    }


                }

            }
        }
        return firstSet;
    }

    public static List<Token> firstSetForToken(Map<NonTerminal, List<Token>> NTFirstSetMap, Token token) {
        if (NonTerminal.class.isInstance(token)) {
            return NTFirstSetMap.get(token);
        } else {
            return Arrays.asList(token);
        }
    }

    public static List<Token> firstSetForSentence(Map<NonTerminal, List<Token>> NTFirstSetMap, List<Token> sentence) {
        List<Token> result = new ArrayList<>();

        int size = sentence.size();
        int pos = -1;
        for (Token token : sentence) {
            pos = pos + 1;
            List<Token> tokenList = firstSetForToken(NTFirstSetMap, token);
            addAllNonEpsilonToken(result, tokenList);
            if (!containsToken(tokenList, new Token(0))) {
                break;
            } else {
                if (pos == size - 1) {
                    addTerminalToken(result, new Token(0));
                }
            }
        }
        return result;
    }

    public static Map<NonTerminal, List<Token>> NTFollowSet(CFG cfg) {
        Map<NonTerminal, List<Token>> NTFirstSetMap = NTFirstSet(cfg);

        Map<NonTerminal, List<Token>> followSet = new HashMap<>();
        for (NonTerminal NT : cfg.getNTList()) {
            followSet.put(NT, new ArrayList<>());
        }
        NonTerminal start = cfg.getStart();
        followSet.get(start).add(new Token(-1));

        Map<NonTerminal, List<Rule>> ruleListMap = cfg.getRuleListMap();
        boolean modified = true;
        while (modified) {
            modified = false;
            for (Map.Entry<NonTerminal, List<Rule>> entry : ruleListMap.entrySet()) {
                NonTerminal NT = entry.getKey();
                List<Rule> ruleList = entry.getValue();

                for (Rule rule : ruleList) {
                    List<Token> right = rule.getRight();
                    int size = right.size();
                    for (int i = 0; i < size; i++) {
                        if (NonTerminal.class.isInstance(right.get(i))) {
                            if (i == size - 1) {
                                if (addAllToken(followSet.get(right.get(i)), followSet.get(NT))) {
                                    modified = true;
                                }
                            } else {
                                List<Token> tillTheEnd = right.subList(i + 1, size);
                                if (addAllNonEpsilonToken(followSet.get(right.get(i)), firstSetForSentence(NTFirstSetMap, tillTheEnd))) {
                                    modified = true;
                                }
                                if (containsToken(firstSetForSentence(NTFirstSetMap, tillTheEnd), new Token(0))) {
                                    if (addAllToken(followSet.get(right.get(i)), followSet.get(NT))) {
                                        modified = true;
                                    }
                                }
                            }

                        }
                    }


                }


            }
        }
        return followSet;
    }

    public static AnalyseTableLL1 analyseTableLL1(CFG cfg) {
        List<Token> TList = cfg.getTList();
        Map<Integer, Integer> TMapping = new HashMap<>();
        int i = -1;
        for (Token T : nonEpsilonToken(TList)) {
            i = i + 1;
            TMapping.put(T.getTag(), i);
        }
        List<NonTerminal> NTList = cfg.getNTList();
        Map<String, Integer> NTMapping = new HashMap<>();
        int j = -1;
        for (NonTerminal NT : NTList) {
            j = j + 1;
            NTMapping.put(NT.getMark(), j);
        }

        Rule[][] table = new Rule[NTList.size()][TList.size() - 1];

        Map<NonTerminal, List<Token>> NTFirstSetMap = NTFirstSet(cfg);
        Map<NonTerminal, List<Token>> NTFollowSetMap = NTFollowSet(cfg);

        Map<NonTerminal, List<Rule>> ruleListMap = cfg.getRuleListMap();
        for (Map.Entry<NonTerminal, List<Rule>> entry : ruleListMap.entrySet()) {
            NonTerminal NT = entry.getKey();
            List<Rule> ruleList = entry.getValue();

            for (Rule rule : ruleList) {
                List<Token> firstSetTokenList = firstSetForSentence(NTFirstSetMap, rule.getRight());
                for (Token token : firstSetTokenList) {
                    if (token.getTag() == 0) {
                        List<Token> followSetTokenList = NTFollowSetMap.get(NT);
                        for (Token follow : followSetTokenList) {
                            table[NTMapping.get(NT.getMark())][TMapping.get(follow.getTag())] = rule;
                        }
                    } else {
                        table[NTMapping.get(NT.getMark())][TMapping.get(token.getTag())] = rule;
                    }
                }

            }
        }
        return new AnalyseTableLL1(table, NTMapping, TMapping);
    }

    public static Map<NonTerminal, List<Token>> firstVTSet(CFG cfg) {
        Map<NonTerminal, List<Rule>> ruleListMap = cfg.getRuleListMap();

        Map<NonTerminal, List<Token>> firstVTSet = new HashMap<>();
        for (NonTerminal NT : cfg.getNTList()) {
            firstVTSet.put(NT, new ArrayList<>());
        }

        boolean modified = true;
        while (modified) {
            modified = false;
            for (Map.Entry<NonTerminal, List<Rule>> entry : ruleListMap.entrySet()) {
                NonTerminal NT = entry.getKey();
                List<Rule> ruleList = entry.getValue();
                for (Rule rule : ruleList) {
                    List<Token> right = rule.getRight();
                    int size=right.size();

                        Token firstToken = right.get(0);
                        if (NonTerminal.class.isInstance(firstToken)){
                            if (addAllToken(firstVTSet.get(NT),firstVTSet.get((NonTerminal) firstToken))){
                                modified=true;
                            }
                            if (size>1){
                                Token secondToken=right.get(1);
                                if (addTerminalToken(firstVTSet.get(NT),secondToken)){
                                    modified=true;
                                }
                            }
                        }else{
                            if (addTerminalToken(firstVTSet.get(NT),firstToken)){
                                modified=true;
                            }
                        }
                }
            }
        }
        return firstVTSet;
    }

    public static Map<NonTerminal, List<Token>> lastVTSet(CFG cfg) {
        Map<NonTerminal, List<Rule>> ruleListMap = cfg.getRuleListMap();

        Map<NonTerminal, List<Token>> lastVTSet = new HashMap<>();
        for (NonTerminal NT : cfg.getNTList()) {
            lastVTSet.put(NT, new ArrayList<>());
        }

        boolean modified = true;
        while (modified) {
            modified = false;
            for (Map.Entry<NonTerminal, List<Rule>> entry : ruleListMap.entrySet()) {
                NonTerminal NT = entry.getKey();
                List<Rule> ruleList = entry.getValue();
                for (Rule rule : ruleList) {
                    List<Token> right = rule.getRight();
                    int size=right.size();

                    Token lastToken = right.get(size-1);
                    if (NonTerminal.class.isInstance(lastToken)){
                        if (addAllToken(lastVTSet.get(NT),lastVTSet.get((NonTerminal) lastToken))){
                            modified=true;
                        }
                        if (size>1){
                            Token secondLastToken=right.get(size-2);
                            if (addTerminalToken(lastVTSet.get(NT),secondLastToken)){
                                modified=true;
                            }
                        }
                    }else{
                        if (addTerminalToken(lastVTSet.get(NT),lastToken)){
                            modified=true;
                        }
                    }
                }
            }
        }
        return lastVTSet;
    }

    public static AnalyseTableOperatorPrecedence analyseTableOperatorPrecedence(CFG cfg) {
        List<Token> TList = cfg.getTList();
        Map<Integer, Integer> TMapping = new HashMap<>();
        int i = -1;
        for (Token T : nonEpsilonToken(TList)) {
            i = i + 1;
            TMapping.put(T.getTag(), i);
        }

        Precedence[][] table = new Precedence[TList.size()][TList.size()];

        Map<NonTerminal, List<Token>> firstVTSetMap = firstVTSet(cfg);
        Map<NonTerminal, List<Token>> lastVTSetMap = lastVTSet(cfg);

        Map<NonTerminal, List<Rule>> ruleListMap = cfg.getRuleListMap();
        for (Map.Entry<NonTerminal, List<Rule>> entry : ruleListMap.entrySet()) {
            NonTerminal NT = entry.getKey();
            List<Rule> ruleList = entry.getValue();

            for (Rule rule : ruleList) {
                List<Token> right = rule.getRight();
                int size = right.size();
                if (size>=2){
                    for (int j=0;j<size-1;j++){
                        Token token1 = right.get(j);
                        Token token2 = right.get(j+1);
                        if ((!NonTerminal.class.isInstance(token1)) && (!NonTerminal.class.isInstance(token2))){
                            table[TMapping.get(token1.getTag())][TMapping.get(token2.getTag())]=new Equal();

                        }else if ((!NonTerminal.class.isInstance(token1)) && (NonTerminal.class.isInstance(token2))){
                            List<Token> tokenList = firstVTSetMap.get((NonTerminal) (token2));
                            for (Token token:tokenList) {
                                table[TMapping.get(token1.getTag())][TMapping.get(token.getTag())]=new Smaller();
                            }
                            if (j!=size-2){
                                Token token3 = right.get(j + 2);
                                table[TMapping.get(token1.getTag())][TMapping.get(token3.getTag())]=new Equal();

                            }

                        }else if ((NonTerminal.class.isInstance(token1)) && (!NonTerminal.class.isInstance(token2))){
                            List<Token> tokenList = lastVTSetMap.get((NonTerminal) (token1));
                            for (Token token:tokenList) {
                                table[TMapping.get(token.getTag())][TMapping.get(token2.getTag())]=new Greater();
                            }
                        }

                    }
                }
            }
        }
        NonTerminal start = cfg.getStart();
        for (Token token:firstVTSetMap.get(start)) {
            table[TMapping.get(-1)][TMapping.get(token.getTag())]=new Smaller();
        }
        for (Token token:lastVTSetMap.get(start)) {
            table[TMapping.get(token)][TMapping.get(-1)]=new Greater();
        }
        return new AnalyseTableOperatorPrecedence(table, TMapping);
    }


    private static boolean containsToken(List<Token> tokenList, Token token) {
        for (Token t : tokenList) {
            if (t.getTag() == token.getTag()) {
                return true;
            }
        }
        return false;
    }

    private static List<Token> nonEpsilonToken(List<Token> tokenList) {
        List<Token> resultTokenList = new ArrayList<>();
        for (Token t : tokenList) {
            if (t.getTag() != 0) {
                resultTokenList.add(t);
            }
        }
        return resultTokenList;
    }

    private static boolean addTerminalToken(List<Token> tokenList, Token token) {
        if (containsToken(tokenList, token)) {
            return false;
        } else {
            tokenList.add(token);
            return true;
        }
    }

    private static boolean addAllNonEpsilonToken(List<Token> tokenList1, List<Token> tokenList2) {
        boolean modified = false;
        tokenList2 = nonEpsilonToken(tokenList2);
        for (Token t : tokenList2) {
            if (addTerminalToken(tokenList1, t)) {
                modified = true;
            }
        }
        return modified;
    }

    private static boolean addAllToken(List<Token> tokenList1, List<Token> tokenList2) {
        boolean modified = false;
        for (Token t : tokenList2) {
            if (addTerminalToken(tokenList1, t)) {
                modified = true;
            }
        }
        return modified;
    }


}
