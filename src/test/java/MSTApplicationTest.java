import algorithms.model.*;
import algorithms.algorithm.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class MSTApplicationTest {

    @Test
    public void testPrimAndKruskalProduceSameCost() {
        List<String> nodes = Arrays.asList("A", "B", "C", "D");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1),
                new Edge("A", "C", 4),
                new Edge("B", "C", 2),
                new Edge("C", "D", 3),
                new Edge("B", "D", 5)
        );

        Map<String, List<Edge>> adjacencyList = new HashMap<>();
        for (String node : nodes) {
            adjacencyList.put(node, new ArrayList<>());
        }
        for (Edge edge : edges) {
            adjacencyList.get(edge.getFrom()).add(edge);
            adjacencyList.get(edge.getTo()).add(new Edge(edge.getTo(), edge.getFrom(), edge.getWeight()));
        }

        MSTResult primResult = PrimMST.findMST(nodes, adjacencyList);
        MSTResult kruskalResult = KruskalMST.findMST(nodes, edges);

        assertEquals(primResult.getTotal_cost(), kruskalResult.getTotal_cost());
        assertEquals(6, primResult.getTotal_cost());
    }

    @Test
    public void testSingleNodeGraph() {
        List<String> nodes = Collections.singletonList("A");
        List<Edge> edges = Collections.emptyList();

        Map<String, List<Edge>> adjacencyList = new HashMap<>();
        adjacencyList.put("A", new ArrayList<>());

        MSTResult primResult = PrimMST.findMST(nodes, adjacencyList);
        MSTResult kruskalResult = KruskalMST.findMST(nodes, edges);

        assertEquals(0, primResult.getTotal_cost());
        assertEquals(0, kruskalResult.getTotal_cost());
        assertTrue(primResult.getMst_edges().isEmpty());
        assertTrue(kruskalResult.getMst_edges().isEmpty());
    }
}
