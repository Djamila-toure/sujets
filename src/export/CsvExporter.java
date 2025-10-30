package export;

import model.Attribute;
import model.Entity;
import model.Project;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvExporter implements Exporter {
    @Override
    public void export(Project project, Map<String, List<List<Object>>> data, String outputPath) throws IOException {
        Path base = Paths.get(outputPath);
        if (!Files.exists(base)) Files.createDirectories(base);

        for (Entity entity : project.getEntities()) {
            String entityName = entity.getName();
            Path file = base.resolve(entityName + ".csv");

            // header
            List<String> headers = entity.getAttributes().stream().map(Attribute::getName).collect(Collectors.toList());
            StringBuilder sb = new StringBuilder();
            sb.append(String.join(",", headers)).append(System.lineSeparator());

            // rows
            List<List<Object>> rows = data.get(entityName);
            if (rows != null) {
                for (List<Object> row : rows) {
                    String line = row.stream().map(o -> o == null ? "" : o.toString()).collect(Collectors.joining(","));
                    sb.append(line).append(System.lineSeparator());
                }
            }

            Files.write(file, sb.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("CSV exporté : " + file.toAbsolutePath());
        }
    }
}
