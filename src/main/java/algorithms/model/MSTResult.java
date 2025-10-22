package algorithms.model;

import java.util.List;

public class MSTResult {
    private List<Edge> mst_edges;
    private int total_cost;
    private int operations_count;
    private double execution_time_ms;

    public MSTResult(List<Edge> mstEdges, int totalCost, int operationsCount, double executionTimeMs) {
        this.mst_edges = mstEdges;
        this.total_cost = totalCost;
        this.operations_count = operationsCount;
        this.execution_time_ms = executionTimeMs;
    }

    public List<Edge> getMst_edges() { return mst_edges; }
    public void setMst_edges(List<Edge> mst_edges) { this.mst_edges = mst_edges; }

    public int getTotal_cost() { return total_cost; }
    public void setTotal_cost(int total_cost) { this.total_cost = total_cost; }

    public int getOperations_count() { return operations_count; }
    public void setOperations_count(int operations_count) { this.operations_count = operations_count; }

    public double getExecution_time_ms() { return execution_time_ms; }
    public void setExecution_time_ms(double execution_time_ms) { this.execution_time_ms = execution_time_ms; }
}

