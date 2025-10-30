package export;

import model.Project;

import java.util.List;
import java.util.Map;

public interface Exporter {
    void export(Project project, Map<String, List<List<Object>>> data, String outputPath) throws Exception;
}
