package algorithms.algorithm;

import algorithms.model.Edge;
import algorithms.model.MSTResult;
import java.util.*;

public class PrimMST {

    public static MSTResult findMST(List<String> nodes, Map<String, List<Edge>> adjacencyList) {
        long startTime = System.nanoTime();
        int operations = 0;

        List<Edge> mstEdges = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        PriorityQueue<Edge> minHeap = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));

        if (nodes.isEmpty()) {
            return new MSTResult(mstEdges, 0, operations, 0);
        }

        // Start with first node
        String startNode = nodes.get(0);
        visited.add(startNode);
        operations++; // add to visited

        // Add all edges from start node to heap
        List<Edge> startEdges = adjacencyList.get(startNode);
        for (Edge edge : startEdges) {
            minHeap.offer(edge);
            operations++; // heap offer
        }
        operations += startEdges.size(); // for loop iterations

        int totalCost = 0;

        while (!minHeap.isEmpty() && visited.size() < nodes.size()) {
            operations++; // while condition check

            Edge currentEdge = minHeap.poll();
            operations++; // heap poll

            String from = currentEdge.getFrom();
            String to = currentEdge.getTo();

            operations += 2; // two contains checks
            boolean fromVisited = visited.contains(from);
            boolean toVisited = visited.contains(to);

            String nextNode = null;
            if (fromVisited && !toVisited) {
                nextNode = to;
                operations++; // condition true
            } else if (toVisited && !fromVisited) {
                nextNode = from;
                operations++; // condition true
            }
            operations++; // if-else structure

            if (nextNode != null) {
                operations++; // null check

                visited.add(nextNode);
                operations++; // add to visited

                mstEdges.add(currentEdge);
                operations++; // add to MST edges

                totalCost += currentEdge.getWeight();
                operations++; // cost addition

                // Add edges from the new node to the heap
                List<Edge> nextEdges = adjacencyList.get(nextNode);
                for (Edge edge : nextEdges) {
                    operations++; // for loop iteration

                    operations++; // contains check
                    if (!visited.contains(edge.getTo())) {
                        minHeap.offer(edge);
                        operations++; // heap offer
                    }
                }
                operations += nextEdges.size(); // total edges processed
            }
        }

        long endTime = System.nanoTime();
        double executionTimeMs = (endTime - startTime) / 1_000_000.0;

        // Sort edges for consistent output
        mstEdges.sort((e1, e2) -> {
            int weightCompare = Integer.compare(e1.getWeight(), e2.getWeight());
            if (weightCompare != 0) return weightCompare;
            int fromCompare = e1.getFrom().compareTo(e2.getFrom());
            if (fromCompare != 0) return fromCompare;
            return e1.getTo().compareTo(e2.getTo());
        });

        return new MSTResult(mstEdges, totalCost, operations, executionTimeMs);
    }
}