package graph;

public class Edge {
    private Node from;
    private Node to;
    // 如果label为null，说明epsilon
    private String label;

    public Edge() {
    }

    public Edge(Node from, Node to, String label) {
        this.from = from;
        this.to = to;
        this.label = label;
    }

    public Node getFrom() {
        return from;
    }

    public void setFrom(Node from) {
        this.from = from;
    }

    public Node getTo() {
        return to;
    }

    public void setTo(Node to) {
        this.to = to;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return from.getStatus() + String.valueOf(from.isBegin()) + String.valueOf(from.isEnd()) + "-->" + to.getStatus() + String.valueOf(to.isBegin()) + String.valueOf(to.isEnd()) + label;
    }
}
