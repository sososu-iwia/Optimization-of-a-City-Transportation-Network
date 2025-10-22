package algorithms.model;
public class Edge {
    private String from;
    private String to;
    private int weight;

    public Edge() {}

    public Edge(String from, String to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }

    @Override
    public String toString() {
        return "Edge{" + from + " - " + to + " (" + weight + ")}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Edge edge = (Edge) obj;
        return weight == edge.weight &&
                ((from.equals(edge.from) && to.equals(edge.to)) ||
                        (from.equals(edge.to) && to.equals(edge.from)));
    }

    @Override
    public int hashCode() {
        // Consider edges undirected for hashCode
        String node1 = from.compareTo(to) < 0 ? from : to;
        String node2 = from.compareTo(to) < 0 ? to : from;
        return (node1 + node2).hashCode() + weight;
    }
}
