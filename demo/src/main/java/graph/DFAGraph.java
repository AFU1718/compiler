package graph;

import java.util.List;

public class DFAGraph extends Graph {
    private Node begin;
    private List<Node> ends;

    public Node getBegin() {
        return begin;
    }

    public void setBegin(Node begin) {
        this.begin = begin;
    }

    public List<Node> getEnds() {
        return ends;
    }

    public void setEnds(List<Node> ends) {
        this.ends = ends;
    }

    @Override
    public String toString() {
        return "graph.DFAGraph{" +
                "nodes=" + getNodes() +
                ", edges=" + getEdges() +
                ", begin=" + begin +
                ", ends=" + ends +
                '}';
    }
}
