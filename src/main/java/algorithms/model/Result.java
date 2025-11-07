package algorithms.model;
public class Result {
    private int graph_id;
    private InputStats input_stats;
    private MSTResult prim;
    private MSTResult kruskal;
    public Result(int graphId, InputStats inputStats, MSTResult prim, MSTResult kruskal) {
        this.graph_id = graphId;
        this.input_stats = inputStats;
        this.prim = prim;
        this.kruskal = kruskal;
    }
    public int getGraph_id() { return graph_id; }
    public void setGraph_id(int graph_id) { this.graph_id = graph_id; }
    public InputStats getInput_stats() { return input_stats; }
    public void setInput_stats(InputStats input_stats) { this.input_stats = input_stats; }
    public MSTResult getPrim() { return prim; }
    public void setPrim(MSTResult prim) { this.prim = prim; }
    public MSTResult getKruskal() { return kruskal; }
    public void setKruskal(MSTResult kruskal) { this.kruskal = kruskal; }
}
