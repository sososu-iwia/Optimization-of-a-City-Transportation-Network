package algorithms;

import com.fasterxml.jackson.databind.ObjectMapper;
import algorithms.model.*;
import algorithms.algorithm.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class MSTApplication {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        try {
            String inputFile;
            if (args.length < 1) {
                System.out.println("No input file provided. Using default from resources...");
                inputFile = getResourceFilePath("input_example.json");
                if (inputFile == null) {
                    inputFile = "input_example.json";
                    createExampleInputFile();
                    System.out.println("Created example input file: " + inputFile);
                }
            } else {
                inputFile = args[0];
            }

            String outputFile = "output_example.json";
            processMST(inputFile, outputFile);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("\nPlease provide a valid JSON input file:");
            System.err.println("Usage: java MSTApplication <input_json_file>");
            System.exit(1);
        }
    }

    private static String getResourceFilePath(String fileName) {
        try {
            URL resource = MSTApplication.class.getClassLoader().getResource(fileName);
            if (resource != null) {
                return resource.getFile();
            }
        } catch (Exception e) {
            System.out.println("Resource file not found: " + fileName);
        }
        return null;
    }

    private static void processMST(String inputFile, String outputFile) throws IOException {
        System.out.println("Processing input file: " + inputFile);

        InputData inputData;
        if (inputFile.contains("resources")) {
            try (InputStream inputStream = MSTApplication.class.getClassLoader().getResourceAsStream("input_example.json")) {
                if (inputStream == null) {
                    throw new IOException("Input file not found in resources: input_example.json");
                }
                inputData = mapper.readValue(inputStream, InputData.class);
            }
        } else {
            File input = new File(inputFile);
            if (!input.exists()) {
                throw new IOException("Input file not found: " + inputFile);
            }
            inputData = mapper.readValue(input, InputData.class);
        }

        if (inputData.getGraphs() == null || inputData.getGraphs().isEmpty()) {
            throw new IllegalArgumentException("No graphs found in input file");
        }

        System.out.println("Found " + inputData.getGraphs().size() + " graph(s) to process");




        List<Result> results = new ArrayList<>();
        for (Graph graph : inputData.getGraphs()) {
            System.out.println("Processing graph ID: " + graph.getId());
            Result result = processGraph(graph);
            results.add(result);
        }
        OutputData outputData = new OutputData(results);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputFile), outputData);

        System.out.println("✓ Results successfully written to " + outputFile);
        printSummary(results);
    }

    private static void createExampleInputFile() throws IOException {
        InputData exampleData = new InputData();

        List<Graph> graphs = new ArrayList<>();


        Graph graph1 = new Graph();
        graph1.setId(1);
        graph1.setNodes(Arrays.asList("A", "B", "C", "D", "E"));
        graph1.setEdges(Arrays.asList(
                new Edge("A", "B", 4),
                new Edge("A", "C", 3),
                new Edge("B", "C", 2),
                new Edge("B", "D", 5),
                new Edge("C", "D", 7),
                new Edge("C", "E", 8),
                new Edge("D", "E", 6)
        ));

        // Graph 2
        Graph graph2 = new Graph();
        graph2.setId(2);
        graph2.setNodes(Arrays.asList("A", "B", "C", "D"));
        graph2.setEdges(Arrays.asList(
                new Edge("A", "B", 1),
                new Edge("A", "C", 4),
                new Edge("B", "C", 2),
                new Edge("C", "D", 3),
                new Edge("B", "D", 5)
        ));

        graphs.add(graph1);
        graphs.add(graph2);
        exampleData.setGraphs(graphs);

        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("input_example.json"), exampleData);
    }

    private static Result processGraph(Graph graph) {
        validateGraph(graph);


        Map<String, List<Edge>> adjacencyList = createAdjacencyList(graph);
        MSTResult primResult = PrimMST.findMST(graph.getNodes(), adjacencyList);
        MSTResult kruskalResult = KruskalMST.findMST(graph.getNodes(), graph.getEdges());
        if (primResult.getTotal_cost() != kruskalResult.getTotal_cost()) {
            System.err.println("Warning: MST costs differ for graph " + graph.getId() +
                    " (Prim: " + primResult.getTotal_cost() +
                    ", Kruskal: " + kruskalResult.getTotal_cost() + ")");
        } else {
            System.out.println("✓ Graph " + graph.getId() + " - Both algorithms agree on MST cost: " + primResult.getTotal_cost());
        }

        return new Result(
                graph.getId(),
                new InputStats(graph.getNodes().size(), graph.getEdges().size()),
                primResult,
                kruskalResult
        );
    }

    private static void validateGraph(Graph graph) {
        if (graph.getNodes() == null || graph.getNodes().isEmpty()) {
            throw new IllegalArgumentException("Graph " + graph.getId() + " has no nodes");
        }
        if (graph.getEdges() == null) {
            throw new IllegalArgumentException("Graph " + graph.getId() + " has no edges");
        }

        Set<String> nodeSet = new HashSet<>(graph.getNodes());
        if (nodeSet.size() != graph.getNodes().size()) {
            throw new IllegalArgumentException("Graph " + graph.getId() + " has duplicate nodes");
        }

        for (Edge edge : graph.getEdges()) {
            if (!graph.getNodes().contains(edge.getFrom())) {
                throw new IllegalArgumentException("Edge from node not found: " + edge.getFrom());
            }
            if (!graph.getNodes().contains(edge.getTo())) {
                throw new IllegalArgumentException("Edge to node not found: " + edge.getTo());
            }
            if (edge.getWeight() < 0) {
                throw new IllegalArgumentException("Edge weight cannot be negative: " + edge.getWeight());
            }
        }
    }

    private static Map<String, List<Edge>> createAdjacencyList(Graph graph) {
        Map<String, List<Edge>> adjacencyList = new HashMap<>();

        for (String node : graph.getNodes()) {
            adjacencyList.put(node, new ArrayList<>());
        }

        for (Edge edge : graph.getEdges()) {
            adjacencyList.get(edge.getFrom()).add(edge);
            adjacencyList.get(edge.getTo()).add(new Edge(edge.getTo(), edge.getFrom(), edge.getWeight()));
        }

        return adjacencyList;
    }

    private static void printSummary(List<Result> results) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("FINAL SUMMARY");
        System.out.println("=".repeat(50));

        for (Result result : results) {
            System.out.println("Graph " + result.getGraph_id() + ":");
            System.out.println("  Input: " + result.getInput_stats().getVertices() +
                    " vertices, " + result.getInput_stats().getEdges() + " edges");
            System.out.println("  Prim:     Cost=" + result.getPrim().getTotal_cost() +
                    ", Operations=" + result.getPrim().getOperations_count() +
                    ", Time=" + String.format("%.3f", result.getPrim().getExecution_time_ms()) + "ms");
            System.out.println("  Kruskal:  Cost=" + result.getKruskal().getTotal_cost() +
                    ", Operations=" + result.getKruskal().getOperations_count() +
                    ", Time=" + String.format("%.3f", result.getKruskal().getExecution_time_ms()) + "ms");
            System.out.println();
        }
    }
}