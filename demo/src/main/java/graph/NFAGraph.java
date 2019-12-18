package graph;

import java.util.ArrayList;
import java.util.List;

public class NFAGraph extends Graph {
    private Node begin;
    private Node end;

    public static NFAGraph orNFAGraph(List<NFAGraph> graphList) {
        NFAGraph resultGraph=emptyNFAGraph();
        for (NFAGraph nfaGraph:graphList) {
            resultGraph=orNFAGraph(resultGraph,nfaGraph);
        }
       return resultGraph;
    }

    public static NFAGraph orNFAGraph(NFAGraph graph1, NFAGraph graph2) {
        Node begin1 = graph1.getBegin();
        Node end1 = graph1.getEnd();
        List<Node> nodes1 = graph1.getNodes();
        begin1.setBegin(false);
        end1.setEnd(false);
        List<Edge> edges1 = graph1.getEdges();

        Node begin2 = graph2.getBegin();
        Node end2 = graph2.getEnd();
        List<Node> nodes2 = graph2.getNodes();
        begin2.setBegin(false);
        end2.setEnd(false);
        List<Edge> edges2 = graph2.getEdges();

        NFAGraph orNFAGraph = new NFAGraph();
        Node node1 = new Node(true, false);
        Node node2 = new Node(false, true);
        List<Node> nodes = new ArrayList<Node>();
        nodes.add(node1);
        nodes.add(node2);
        nodes.addAll(nodes1);
        nodes.addAll(nodes2);
        orNFAGraph.setNodes(nodes);

        List<Edge> edges = new ArrayList<Edge>();
        edges.add(new Edge(node1, begin1, null));
        edges.add(new Edge(node1, begin2, null));
        edges.add(new Edge(end1, node2, null));
        edges.add(new Edge(end2, node2, null));
        if (edges1!=null){
            edges.addAll(edges1);
        }
        if (edges2!=null){
            edges.addAll(edges2);
        }
        orNFAGraph.setEdges(edges);

        orNFAGraph.setBegin(node1);
        orNFAGraph.setEnd(node2);

        return orNFAGraph;
    }
    public static NFAGraph andNFAGraph(List<NFAGraph> graphList) {
        NFAGraph resultGraph=epsilonNFAGraph();
        for (NFAGraph nfaGraph:graphList) {
            resultGraph=andNFAGraph(resultGraph,nfaGraph);
        }
        return resultGraph;
    }

    public static NFAGraph andNFAGraph(NFAGraph graph1, NFAGraph graph2){
        Node begin1 = graph1.getBegin();
        Node end1 = graph1.getEnd();
        List<Node> nodes1 = graph1.getNodes();
        end1.setEnd(false);
        List<Edge> edges1 = graph1.getEdges();

        Node begin2 = graph2.getBegin();
        Node end2 = graph2.getEnd();
        List<Node> nodes2 = graph2.getNodes();
        begin2.setBegin(false);
        List<Edge> edges2 = graph2.getEdges();

        NFAGraph andNFAGraph = new NFAGraph();

        List<Node> nodes = new ArrayList<Node>();
        nodes.addAll(nodes1);
        nodes.addAll(nodes2);
        andNFAGraph.setNodes(nodes);

        List<Edge> edges = new ArrayList<Edge>();
        edges.add(new Edge(end1, begin2, null));
        if (edges1!=null){
            edges.addAll(edges1);
        }
        if (edges2!=null){
            edges.addAll(edges2);
        };
        andNFAGraph.setEdges(edges);

        andNFAGraph.setBegin(begin1);
        andNFAGraph.setEnd(end2);

        return andNFAGraph;

    }

    public static NFAGraph starNFAGraph(NFAGraph graph){
        Node begin = graph.getBegin();
        Node end = graph.getEnd();
        List<Node> nodeList = graph.getNodes();
        begin.setBegin(false);
        end.setEnd(false);
        List<Edge> edgeList = graph.getEdges();

        NFAGraph starNFAGraph = new NFAGraph();
        Node node1 = new Node(true, false);
        Node node2 = new Node(false, true);
        List<Node> nodes = new ArrayList<Node>();
        nodes.add(node1);
        nodes.add(node2);
        nodes.addAll(nodeList);
        starNFAGraph.setNodes(nodes);

        List<Edge> edges = new ArrayList<Edge>();
        edges.add(new Edge(node1, begin, null));
        edges.add(new Edge(end, node2, null));
        edges.add(new Edge(node1, node2, null));
        edges.add(new Edge(end, begin, null));
        edges.addAll(edgeList);
        starNFAGraph.setEdges(edges);

        starNFAGraph.setBegin(node1);
        starNFAGraph.setEnd(node2);

        return starNFAGraph;
    }

        public static NFAGraph emptyNFAGraph() {
        NFAGraph nfa = new NFAGraph();

        Node node1 = new Node(true, false);
        Node node2 = new Node(false, true);
        List<Node> nodeList = new ArrayList<Node>();
        nodeList.add(node1);
        nodeList.add(node2);
        nfa.setNodes(nodeList);

        nfa.setEdges(null);
        nfa.setBegin(node1);
        nfa.setEnd(node2);
        return nfa;
    }

    public static NFAGraph epsilonNFAGraph() {
        NFAGraph nfa = new NFAGraph();

        Node node = new Node(true, true);
        List<Node> nodeList = new ArrayList<Node>();
        nodeList.add(node);
        nfa.setNodes(nodeList);

        List<Edge> edgeList = new ArrayList<Edge>();
        Edge edge = new Edge(node, node, null);
        edgeList.add(edge);
        nfa.setEdges(edgeList);

        nfa.setBegin(node);
        nfa.setEnd(node);
        return nfa;
    }

    public static NFAGraph singleCharNFAGraph(String c) {
        NFAGraph nfa = new NFAGraph();

        Node node1 = new Node(true, false);
        Node node2 = new Node(false, true);
        List<Node> nodeList = new ArrayList<Node>();
        nodeList.add(node1);
        nodeList.add(node2);
        nfa.setNodes(nodeList);

        List<Edge> edgeList = new ArrayList<Edge>();
        Edge edge = new Edge(node1, node2, c);
        edgeList.add(edge);
        nfa.setEdges(edgeList);

        nfa.setBegin(node1);
        nfa.setEnd(node2);
        return nfa;
    }


    public Node getBegin() {
        return begin;
    }

    public void setBegin(Node begin) {
        this.begin = begin;
    }

    public Node getEnd() {
        return end;
    }

    public void setEnd(Node end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "graph.NFAGraph{" +
                "nodes=" + getNodes() +
                ", edges=" + getNodes() +
                ", begin=" + begin +
                ", end=" + end +
                '}';
    }
}
