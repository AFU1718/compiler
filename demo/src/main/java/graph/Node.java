package graph;

import java.util.List;

public class Node {
    private int status;
    private boolean begin;
    private boolean end;
    private static int COUNT=0;

    public Node(boolean begin, boolean end) {
        COUNT=COUNT+1;
        this.status=COUNT;
        this.begin=begin;
        this.end=end;

    }
    public Node(int status,boolean begin, boolean end) {
        this.status=status;
        this.begin=begin;
        this.end=end;

    }

    public Node() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isBegin() {
        return begin;
    }

    public void setBegin(boolean begin) {
        this.begin = begin;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "graph.Node{" +
                "status=" + status +
                ", begin=" + begin +
                ", end=" + end +
                '}';
    }
}
