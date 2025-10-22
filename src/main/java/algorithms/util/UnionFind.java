package algorithms.util;

import java.util.*;

public class UnionFind {
    private Map<String, String> parent;
    private Map<String, Integer> rank;
    private int operations;

    public UnionFind(List<String> nodes) {
        parent = new HashMap<>();
        rank = new HashMap<>();
        operations = 0;

        for (String node : nodes) {
            parent.put(node, node);
            rank.put(node, 0);
            operations += 2; // Two put operations
        }
        operations += nodes.size(); // Loop iterations
    }

    public String find(String node) {
        operations++; // Method entry + get
        if (!parent.get(node).equals(node)) {
            operations++; // Comparison + recursive call
            parent.put(node, find(parent.get(node)));
            operations++; // Put operation
        }
        operations++; // Return get
        return parent.get(node);
    }

    public boolean union(String node1, String node2) {
        String root1 = find(node1);
        String root2 = find(node2);
        operations += 2; // Two find calls

        operations++; // Comparison
        if (root1.equals(root2)) {
            return false;
        }

        // Union by rank
        int rank1 = rank.get(root1);
        int rank2 = rank.get(root2);
        operations += 2; // Two get operations

        operations++; // First comparison
        if (rank1 < rank2) {
            parent.put(root1, root2);
            operations++; // Put operation
        } else {
            operations++; // Second comparison
            if (rank1 > rank2) {
                parent.put(root2, root1);
                operations++; // Put operation
            } else {
                parent.put(root2, root1);
                rank.put(root1, rank1 + 1);
                operations += 3; // Two puts and increment
            }
        }

        return true;
    }

    public int getOperations() {
        return operations;
    }
}