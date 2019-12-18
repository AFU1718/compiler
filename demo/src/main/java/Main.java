import graph.DFAGraph;
import graph.NFAGraph;
import graph.GraphUtils;

import java.util.*;

public class Main {
    public static void main(String[] args) {
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

        DFAGraph dfaGraph = GraphUtils.nfaToDfa(language);
        DFAGraph dfaGraphSimplify = GraphUtils.dfaSimplify(dfaGraph);

        List<String> stringList = GraphUtils.textToStringList(dfaGraphSimplify, "** # ");
        for (String s:stringList) {
            System.out.println(s);
        }


//        RegexBase r=new OrRegex(new StarRegex(new RegexBase("d")),new RegexBase("h"));
//
////        graph.NFAGraph nfaGraph = graph.NFAGraph.andNFAGraph(graph.NFAGraph.starNFAGraph(graph.NFAGraph.singleCharGraph("w")),graph.NFAGraph.orNFAGraph(graph.NFAGraph.singleCharGraph("h"), graph.NFAGraph.singleCharGraph("d")));
////        graph.NFAGraph nfaGraph = graph.NFAGraph.orNFAGraph(graph.NFAGraph.starNFAGraph(graph.NFAGraph.singleCharGraph("w")),graph.NFAGraph.andNFAGraph(graph.NFAGraph.singleCharGraph("h"), graph.NFAGraph.singleCharGraph("d")));
//        graph.NFAGraph nfaGraph1 = graph.NFAGraph.starNFAGraph(graph.NFAGraph.orNFAGraph(graph.NFAGraph.singleCharGraph("a"), graph.NFAGraph.singleCharGraph("b")));
//        graph.NFAGraph nfaGraph2 = graph.NFAGraph.orNFAGraph(graph.NFAGraph.andNFAGraph(graph.NFAGraph.singleCharGraph("a"), graph.NFAGraph.singleCharGraph("a")),
//                graph.NFAGraph.andNFAGraph(graph.NFAGraph.singleCharGraph("b"), graph.NFAGraph.singleCharGraph("b")));
//        graph.NFAGraph nfaGraph3 = graph.NFAGraph.starNFAGraph(graph.NFAGraph.orNFAGraph(graph.NFAGraph.singleCharGraph("a"), graph.NFAGraph.singleCharGraph("b")));
//        graph.NFAGraph nfaGraph = graph.NFAGraph.andNFAGraph(nfaGraph1,graph.NFAGraph.andNFAGraph(nfaGraph2,nfaGraph3));
//
//        System.out.println(nfaGraph);
//        System.out.println("~~~~~~~~~~~~~~~");
//
////        System.out.println("~~~~~~~~~~~~~~~");
////        Set<graph.Node> begin =new HashSet<>();
////        begin.add(nfaGraph.getBegin());
////        Set<graph.Node> nodes = graph.Utils.epsilonClosure(nfaGraph, begin);
////        System.out.println(nodes);
////        System.out.println("~~~~~~~~~~~~~~~");
////        System.out.println(graph.Utils.oneCharEpsilonClosure(nfaGraph, nodes,"w"));
//        graph.DFAGraph dfaGraph = graph.Utils.nfaToDfa(nfaGraph);
//        Map<graph.Node, List<graph.Edge>> nodeListMap = graph.Utils.groupByNode(dfaGraph);
//        System.out.println(nodeListMap);
//        System.out.println("~~~~~~~~~~~~~~~");
//        System.out.println(dfaGraph);
//        System.out.println("111111111111111111111111111111111111111");
//        graph.Utils.dfaSimplify(dfaGraph);





    }
}
