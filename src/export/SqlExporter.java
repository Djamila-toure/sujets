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

public class SqlExporter implements Exporter {
    @Override
    public void export(Project project, Map<String, List<List<Object>>> data, String outputPath) throws IOException {
        Path base = Paths.get(outputPath);
        if (!Files.exists(base)) Files.createDirectories(base);

        Path file = base.resolve(project.getName().replaceAll("\\s+", "_") + ".sql");
        StringBuilder sb = new StringBuilder();

        for (Entity entity : project.getEntities()) {
            String table = entity.getName();
            List<String> cols = entity.getAttributes().stream().map(Attribute::getName).collect(Collectors.toList());
            List<List<Object>> rows = data.get(entity.getName());
            if (rows == null) continue;

            for (List<Object> row : rows) {
                StringBuilder values = new StringBuilder();
                for (int i = 0; i < cols.size(); i++) {
                    Object v = i < row.size() ? row.get(i) : null;
                    if (v == null) values.append("NULL");
                    else values.append("'").append(v.toString().replace("'", "''")).append("'");
                    if (i < cols.size() - 1) values.append(", ");
                }
                sb.append("INSERT INTO ").append(table).append(" (")
                        .append(String.join(", ", cols)).append(") VALUES (")
                        .append(values).append(");").append(System.lineSeparator());
            }
        }

        Files.write(file, sb.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("SQL exporté : " + file.toAbsolutePath());
    }
}
