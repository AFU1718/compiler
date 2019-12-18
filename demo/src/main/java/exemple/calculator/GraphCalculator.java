package exemple.calculator;

import graph.DFAGraph;
import graph.GraphUtils;
import graph.NFAGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphCalculator {
    public static final DFAGraph DFA_GRAPH_CALCULATOR;
    static{
        List<NFAGraph> digitGraphList=new ArrayList<>();
        for(int i=1;i<10;i++){
            digitGraphList.add(NFAGraph.singleCharNFAGraph(String.valueOf(i)));
        }
        NFAGraph nfaGraph_digit=NFAGraph.andNFAGraph(NFAGraph.orNFAGraph(digitGraphList),NFAGraph.starNFAGraph(NFAGraph.orNFAGraph(digitGraphList)));
        NFAGraph nfaGraph_lparam=NFAGraph.singleCharNFAGraph(String.valueOf("("));
        NFAGraph nfaGraph_rparam=NFAGraph.singleCharNFAGraph(String.valueOf(")"));

        NFAGraph nfaGraph_plus=NFAGraph.singleCharNFAGraph(String.valueOf("+"));
        NFAGraph nfaGraph_minus=NFAGraph.singleCharNFAGraph(String.valueOf("-"));
        NFAGraph nfaGraph_multiple=NFAGraph.singleCharNFAGraph(String.valueOf("*"));
        NFAGraph nfaGraph_divide=NFAGraph.singleCharNFAGraph(String.valueOf("/"));
        NFAGraph nfaGraph_power=NFAGraph.andNFAGraph(NFAGraph.singleCharNFAGraph(String.valueOf("*")),NFAGraph.singleCharNFAGraph(String.valueOf("*")));
        NFAGraph nfaGraph_hashtag=NFAGraph.singleCharNFAGraph(String.valueOf("#"));

        NFAGraph language=NFAGraph.orNFAGraph(Arrays.asList(
                nfaGraph_digit,nfaGraph_lparam,nfaGraph_rparam,
                nfaGraph_plus,nfaGraph_minus,nfaGraph_multiple,nfaGraph_divide,nfaGraph_power,nfaGraph_hashtag));

        DFA_GRAPH_CALCULATOR = GraphUtils.dfaSimplify(GraphUtils.nfaToDfa(language));

    }
}
