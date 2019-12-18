package graph;

import regex.*;

import java.util.*;
import java.util.stream.Collectors;

public class GraphUtils {
        public static final Set<String> CHARSET = new HashSet<>(Arrays.asList(
            "Ø", "ε", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
            "+", "-", "*", "/", "<", "=", ">",
            "!", ";", "(", ")", "^", ",", "#", "&",
            "|", "%", "~", "[", "]", "{",
            "}",".", "?", ":", "!","'","\"","\\"
    ));

    public static List<String> textToStringList(DFAGraph dfaGraph, String text) {
        List<String> resultList = new ArrayList<>();
        text = text.replaceAll(" +", " ");
        String[] stringArray = text.split("");
        int size = stringArray.length;
        int currentPos = -1;
        String currentChar = "";
        int lastRecognizedPos = -1;
        String lastRecognizedString = "";
        String extendString = "";
        Node beginNode=dfaGraph.getBegin();
        Node currentNode = beginNode;
        Map<Node, List<Edge>> nodeListMap = GraphUtils.groupByNode(dfaGraph);

        List<Node> endNodes = dfaGraph.getEnds();
        scan:
        while (currentPos < size-1) {
            currentPos = currentPos + 1;
            currentChar = stringArray[currentPos];
            extendString = extendString + currentChar;
            List<Edge> edges = nodeListMap.get(currentNode);
            if (" ".equals(currentChar)){
                resultList.add(lastRecognizedString);
                lastRecognizedString="";
                extendString="";
                currentNode=beginNode;
                continue scan;


            }else if (edges == null) {
                resultList.add(lastRecognizedString);
                currentPos = lastRecognizedPos;
                lastRecognizedString = "";
                extendString = "";
                currentNode=beginNode;
                continue scan;
            } else {
                boolean edgeFound = false;
                for (Edge edge : edges) {
                    if (currentChar.equals(edge.getLabel())) {
                        Node toNode = edge.getTo();
                        currentNode = toNode;
                        edgeFound = true;
                        if (!endNodes.contains(toNode)) {
                            continue scan;
                        } else {
                            lastRecognizedString = extendString;
                            lastRecognizedPos = currentPos;
                            continue scan;
                        }

                    }
                }
                if (!edgeFound) {
                    resultList.add(lastRecognizedString);
                    currentPos = lastRecognizedPos;
                    lastRecognizedString = "";
                    extendString = "";
                    currentNode=beginNode;
                    continue scan;
                }
            }

        }
        return resultList;
    }

    public static NFAGraph RegexToNFAGraph(RegexBase reg) {
        if (StarRegex.class.isInstance(reg)) {
            RegexBase regex = ((StarRegex) reg).getRegex();
            return NFAGraph.starNFAGraph(RegexToNFAGraph(regex));

        } else if (AndRegex.class.isInstance(reg)) {
            RegexBase leftRegex = ((AndRegex) reg).getLeftRegex();
            RegexBase rightRegex = ((AndRegex) reg).getRightRegex();
            return NFAGraph.andNFAGraph(RegexToNFAGraph(leftRegex), RegexToNFAGraph(rightRegex));

        } else if (OrRegex.class.isInstance(reg)) {
            RegexBase leftRegex = ((OrRegex) reg).getLeftRegex();
            RegexBase rightRegex = ((OrRegex) reg).getRightRegex();
            return NFAGraph.orNFAGraph(RegexToNFAGraph(leftRegex), RegexToNFAGraph(rightRegex));

        } else {
            if (reg.getC().equals("Ø")) {
                return NFAGraph.emptyNFAGraph();
            } else if (reg.getC().equals("ε")) {
                return NFAGraph.epsilonNFAGraph();
            } else {
                return NFAGraph.singleCharNFAGraph(reg.getC());
            }
        }

    }

    public static Map<Node, List<Edge>> groupByNode(Graph graph) {
        List<Edge> edges = graph.getEdges();
        if (edges == null) {
            return null;
        }
        return edges.stream().collect(Collectors.groupingBy(Edge::getFrom));

    }

    private static Set<Node> epsilonClosure(NFAGraph graph, Set<Node> nodeSet) {
        Map<Node, List<Edge>> nodeEdgesMap = groupByNode(graph);

        Set<Node> visited = new HashSet<Node>();

        Deque<Node> nodes = new ArrayDeque<>(nodeSet);
        while (!nodes.isEmpty()) {
            Node pop = nodes.pop();
            if (!visited.contains(pop)) {
                visited.add(pop);
                List<Edge> edges = nodeEdgesMap.get(pop);
                if (edges != null) {
                    for (Edge edge : edges) {
                        if ((!visited.contains(edge.getTo())) && edge.getLabel() == null) {
                            nodes.push(edge.getTo());
                        }
                    }
                }
            }
        }

        return visited;
    }

    private static Set<Node> oneCharEpsilonClosure(NFAGraph graph, Set<Node> nodeSet, String c) {
        Set<Node> oneCharAwayNodeSet = new HashSet<>();
        for (Node node : nodeSet) {
            Map<Node, List<Edge>> nodeEdgesMap = groupByNode(graph);
            List<Edge> edges = nodeEdgesMap.get(node);
            if (edges != null) {
                for (Edge edge : edges) {
                    if (c.equals(edge.getLabel())) {
                        oneCharAwayNodeSet.add(edge.getTo());
                    }
                }
            }

        }
        if (oneCharAwayNodeSet.size() == 0) {
            return null;
        }
        return epsilonClosure(graph, oneCharAwayNodeSet);

    }

    public static DFAGraph nfaToDfa(NFAGraph nfaGraph) {
        Map<Set<Node>, Node> mapping = new HashMap<>();
        // 记录已被mapping的Set<graph.Node>,这样就不会重新为这个Set<graph.Node>创建node了
        Set<Set<Node>> mappedSubsetSet = new HashSet<>();


        DFAGraph dfaGraph = new DFAGraph();
        List<Node> nodes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        List<Node> ends = new ArrayList<>();

        Set<Set<Node>> visited = new HashSet<>();

        HashSet<Node> beginSet = new HashSet<>();
        beginSet.add(nfaGraph.getBegin());
        Set<Node> beginEpsilonClosure = epsilonClosure(nfaGraph, beginSet);
        Node beginNode = new Node(true, false);
        mapping.put(beginEpsilonClosure, beginNode);

        mappedSubsetSet.add(beginEpsilonClosure);

        dfaGraph.setBegin(beginNode);
        nodes.add(beginNode);

        Deque<Set<Node>> subsetStack = new ArrayDeque<>();
        subsetStack.push(beginEpsilonClosure);


        while (!subsetStack.isEmpty()) {
            Set<Node> pop = subsetStack.pop();
            if (!isContainSubset(visited, pop)) {
                visited.add(pop);

                for (String c : CHARSET) {
                    Set<Node> oneCharEpsilonClosure = oneCharEpsilonClosure(nfaGraph, pop, c);
                    if (oneCharEpsilonClosure != null) {
                        Set<Node> mappedSubset = sameSubset(mappedSubsetSet, oneCharEpsilonClosure);
                        Node node;
                        if (mappedSubset != null) {
                            node = mapping.get(mappedSubset);
                            nodes.add(node);
                        } else {
                            if (oneCharEpsilonClosure.contains(nfaGraph.getEnd())) {
                                node = new Node(false, true);
                                ends.add(node);
                            } else {
                                node = new Node(false, false);
                            }
                            nodes.add(node);

                            mapping.put(oneCharEpsilonClosure, node);
                            mappedSubsetSet.add(oneCharEpsilonClosure);
                        }

                        Edge newEdge = new Edge(mapping.get(pop), node, c);
                        edges.add(newEdge);


                        if (!isContainSubset(visited, oneCharEpsilonClosure)) {
                            subsetStack.push(oneCharEpsilonClosure);
                        }
                    }
                }
            }

        }
        dfaGraph.setNodes(nodes);
        dfaGraph.setEdges(edges);
        dfaGraph.setEnds(ends);
        return dfaGraph;

    }

    public static DFAGraph dfaSimplify(DFAGraph dfaGraph) {
        Set<Set<Node>> partition = new HashSet<>();
        Map<Node, Integer> nodeToSlotMap = new HashMap<>();
        Map<Node, List<Edge>> nodeListMap = groupByNode(dfaGraph);

        int slotCount = 0;
        Set<Node> ends = new HashSet<>(dfaGraph.getEnds());

        Set<Node> nonEnds = new HashSet<>(dfaGraph.getNodes());
        nonEnds.removeAll(ends);

        for (Node node : ends) {
            nodeToSlotMap.put(node, 1);
        }
        slotCount++;
        for (Node node : nonEnds) {
            nodeToSlotMap.put(node, 2);
        }
        slotCount++;

        //初始化
        partition.add(ends);
        partition.add(nonEnds);

        boolean modified = true;
        encore:
        while (modified) {
            modified = false;
            for (Set<Node> nodeSet : partition) {
                ArrayList<Node> nodesArray = new ArrayList<>(nodeSet);
                Integer currentSlot = nodeToSlotMap.get(nodesArray.get(0));

                for (String c : CHARSET) {
                    Map<Integer, Set<Node>> nodeInSameSlot = new HashMap<>();
                    for (Node node : nodeSet) {
                        List<Edge> edges = nodeListMap.get(node);
                        if (edges == null) {
                            Set<Node> nodes = nodeInSameSlot.get(0);
                            if (nodes == null) {
                                Set<Node> newSet = new HashSet<>();
                                newSet.add(node);
                                nodeInSameSlot.put(0, newSet);
                            } else {
                                nodes.add(node);
                            }
                        } else {
                            boolean found = false;
                            for (Edge edge : edges) {
                                if (c.equals(edge.getLabel())) {
                                    found = true;
                                    Node to = edge.getTo();
                                    Integer toSlot = nodeToSlotMap.get(to);

                                    Set<Node> nodes = nodeInSameSlot.get(toSlot);
                                    if (nodes == null) {
                                        Set<Node> newSet = new HashSet<>();
                                        newSet.add(node);
                                        nodeInSameSlot.put(toSlot, newSet);
                                    } else {
                                        nodes.add(node);
                                    }
                                    break;

                                }
                            }
                            if (found == false) {
                                Set<Node> nodes = nodeInSameSlot.get(0);
                                if (nodes == null) {
                                    Set<Node> newSet = new HashSet<>();
                                    newSet.add(node);
                                    nodeInSameSlot.put(0, newSet);
                                } else {
                                    nodes.add(node);
                                }
                            }
                        }


                    }
                    // 大于1说明需要分割
                    if (nodeInSameSlot.size() > 1) {
                        partition.remove(nodeSet);
                        int i = 0;
                        for (Map.Entry<Integer, Set<Node>> entry : nodeInSameSlot.entrySet()) {
                            partition.add(entry.getValue());
                            int slotNum;
                            if (i == 0) {
                                slotNum = currentSlot;
                            } else {
                                slotCount++;
                                slotNum = slotCount;
                            }
                            for (Node nodeTmp : entry.getValue()) {
                                nodeToSlotMap.put(nodeTmp, slotNum);
                            }
                            i++;
                        }
                        modified = true;
                        continue encore;
                    }
                }
            }
        }

//        List<graph.Node> oldNodes = dfaGraph.getNodes();
        List<Edge> oldEdges = dfaGraph.getEdges();
        Node oldBegin = dfaGraph.getBegin();
        List<Node> oldEnds = dfaGraph.getEnds();

        Integer oldBeginSlot = nodeToSlotMap.get(oldBegin);
        Set<Integer> oldEndSlotSet = new HashSet<>();
        for (Node end : oldEnds) {
            oldEndSlotSet.add(nodeToSlotMap.get(end));
        }


        List<Node> newNodes = new ArrayList<>();
        List<Edge> newEdges = new ArrayList<>();
        Node newBegin = new Node();
        // 为了去重
        Set<Node> newEnds = new HashSet<>();

        // 给edge编码，储存到set里
        Set<String> edgeCodeSet = new HashSet<>();

        // 先准备好全部的 partition.size()个node，放入map里
        Map<Integer, Node> nodeMap = new HashMap<>();
        for (int i = 1; i <= partition.size(); i++) {
            nodeMap.put(i, new Node(i, false, false));
        }

        for (Edge e : oldEdges) {
            int fromSlot = nodeToSlotMap.get(e.getFrom());
            int toSlot = nodeToSlotMap.get(e.getTo());
            String code = fromSlot + toSlot + e.getLabel();
            if (!edgeCodeSet.contains(code)) {
                edgeCodeSet.add(code);
                // 看from，to是不是起点，终点边,还是非起非终
                Node newFrom = nodeMap.get(fromSlot);
                if (newFrom.getStatus() == oldBeginSlot) {
                    newFrom.setBegin(true);
                    newBegin = newFrom;
                }
                if (oldEndSlotSet.contains(newFrom.getStatus())) {
                    newFrom.setEnd(true);
                    newEnds.add(newFrom);
                }

                Node newTo = nodeMap.get(toSlot);
                if (newTo.getStatus() == oldBeginSlot) {
                    newTo.setBegin(true);
                    newBegin = newTo;
                }
                if (oldEndSlotSet.contains(newTo.getStatus())) {
                    newTo.setEnd(true);
                    newEnds.add(newTo);
                }
                newEdges.add(new Edge(newFrom, newTo, e.getLabel()));
            }

        }

        for (Node node : nodeMap.values()) {
            newNodes.add(node);
        }

        DFAGraph graph = new DFAGraph();
        graph.setNodes(newNodes);
        graph.setEdges(newEdges);
        graph.setBegin(newBegin);
        graph.setEnds(new ArrayList<>(newEnds));

        return graph;
    }


    private static boolean isTwoSetIdentical(Set<Node> set1, Set<Node> set2) {
        if (set1.size() != set2.size()) {
            return false;
        }
        for (Node node1 : set1) {
            if (!set2.contains(node1)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isContainSubset(Set<Set<Node>> subsetSet, Set<Node> subset) {
        for (Set<Node> nodes : subsetSet) {
            if (isTwoSetIdentical(nodes, subset)) {
                return true;
            }
        }
        return false;
    }

    private static Set<Node> sameSubset(Set<Set<Node>> subsetSet, Set<Node> subset) {
        for (Set<Node> nodes : subsetSet) {
            if (isTwoSetIdentical(nodes, subset)) {
                return nodes;
            }
        }
        return null;
    }
}
