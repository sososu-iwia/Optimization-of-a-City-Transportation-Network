package algorithms.model;
import java.util.List;
public class OutputData {
    private List<Result> results;
    public OutputData(List<Result> results) {
        this.results = results;
    }
    public List<Result> getResults() { return results; }
    public void setResults(List<Result> results) { this.results = results; }
}
