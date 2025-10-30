package export;

import model.Attribute;
import model.Entity;
import model.Project;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.Map;

public class JsonExporter implements Exporter {
    @Override
    public void export(Project project, Map<String, List<List<Object>>> data, String outputPath) throws IOException {
        Path base = Paths.get(outputPath);
        if (!Files.exists(base)) Files.createDirectories(base);

        Path file = base.resolve(project.getName().replaceAll("\\s+", "_") + ".json");

        StringBuilder sb = new StringBuilder();
        sb.append("{").append(System.lineSeparator());

        List<Entity> entities = project.getEntities();
        for (int eidx = 0; eidx < entities.size(); eidx++) {
            Entity entity = entities.get(eidx);
            String entityName = entity.getName();
            sb.append("  \"").append(entityName).append("\": [").append(System.lineSeparator());

            List<List<Object>> rows = data.get(entityName);
            List<Attribute> attrs = entity.getAttributes();
            if (rows != null) {
                for (int i = 0; i < rows.size(); i++) {
                    List<Object> row = rows.get(i);
                    sb.append("    {");
                    for (int j = 0; j < attrs.size(); j++) {
                        Attribute a = attrs.get(j);
                        Object val = j < row.size() ? row.get(j) : null;
                        sb.append("\"").append(a.getName()).append("\": ");
                        if (val == null) sb.append("null");
                        else sb.append("\"").append(escape(val.toString())).append("\"");
                        if (j < attrs.size() - 1) sb.append(", ");
                    }
                    sb.append("}");
                    if (i < rows.size() - 1) sb.append(",");
                    sb.append(System.lineSeparator());
                }
            }

            sb.append("  ]");
            if (eidx < entities.size() - 1) sb.append(",");
            sb.append(System.lineSeparator());
        }

        sb.append("}").append(System.lineSeparator());
        Files.write(file, sb.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("JSON exporté : " + file.toAbsolutePath());
    }

    private String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
