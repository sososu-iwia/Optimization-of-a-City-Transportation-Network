package algorithms.algorithm;

import algorithms.model.Edge;
import algorithms.model.MSTResult;
import algorithms.util.UnionFind;
import java.util.*;

public class KruskalMST {

    public static MSTResult findMST(List<String> nodes, List<Edge> edges) {
        long startTime = System.nanoTime();
        int operations = 0;

        List<Edge> mstEdges = new ArrayList<>();
        UnionFind uf = new UnionFind(nodes);

        // Sort edges by weight - count actual operations
        List<Edge> sortedEdges = new ArrayList<>(edges);

        // Count sort operations (approximately n*log(n) comparisons)
        int sortComparisons = (int)(edges.size() * Math.log(edges.size()) / Math.log(2));
        operations += sortComparisons;

        // Actual sorting
        sortedEdges.sort(Comparator.comparingInt(Edge::getWeight));
        operations += edges.size(); // for the sorting overhead

        int totalCost = 0;
        int edgesUsed = 0;

        for (Edge edge : sortedEdges) {
            operations++; // for loop iteration

            if (edgesUsed == nodes.size() - 1) {
                operations++; // break condition check
                break;
            }

            boolean added = uf.union(edge.getFrom(), edge.getTo());
            operations++; // union call

            if (added) {
                mstEdges.add(edge);
                operations++; // add to list

                totalCost += edge.getWeight();
                operations++; // cost addition

                edgesUsed++;
                operations++; // increment
            }
        }

        // Add Union-Find operations
        operations += uf.getOperations();

        long endTime = System.nanoTime();
        double executionTimeMs = (endTime - startTime) / 1_000_000.0;

        return new MSTResult(mstEdges, totalCost, operations, executionTimeMs);
    }
}