package export;

import model.Attribute;
import model.Entity;
import model.Project;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.Map;

public class XmlExporter implements Exporter {
    @Override
    public void export(Project project, Map<String, List<List<Object>>> data, String outputPath) throws IOException {
        Path base = Paths.get(outputPath);
        if (!Files.exists(base)) Files.createDirectories(base);

        Path file = base.resolve(project.getName().replaceAll("\\s+", "_") + ".xml");
        StringBuilder sb = new StringBuilder();
        sb.append("<project name=\"").append(project.getName()).append("\">").append(System.lineSeparator());

        for (Entity entity : project.getEntities()) {
            String en = entity.getName();
            sb.append("  <entity name=\"").append(en).append("\">").append(System.lineSeparator());
            List<Attribute> attrs = entity.getAttributes();
            List<List<Object>> rows = data.get(en);
            if (rows != null) {
                for (List<Object> row : rows) {
                    sb.append("    <row>").append(System.lineSeparator());
                    for (int i = 0; i < attrs.size(); i++) {
                        Attribute a = attrs.get(i);
                        Object val = i < row.size() ? row.get(i) : null;
                        sb.append("      <").append(a.getName()).append(">");
                        if (val != null) sb.append(escape(val.toString()));
                        sb.append("</").append(a.getName()).append(">").append(System.lineSeparator());
                    }
                    sb.append("    </row>").append(System.lineSeparator());
                }
            }
            sb.append("  </entity>").append(System.lineSeparator());
        }

        sb.append("</project>").append(System.lineSeparator());
        Files.write(file, sb.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("XML exporté : " + file.toAbsolutePath());
    }

    private String escape(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
