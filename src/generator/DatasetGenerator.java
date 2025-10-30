package generator;

import model.Entity;
import model.Project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatasetGenerator {
    private final DataGenerator generator;

    public DatasetGenerator(DataGenerator generator) {
        this.generator = generator;
    }

    public Map<String, List<List<Object>>> generate(Project project) {
        Map<String, List<List<Object>>> dataset = new HashMap<>();
        System.out.println("Génération du projet : " + project.getName());
        for (Entity entity : project.getEntities()) {
            List<List<Object>> data = generator.generateData(entity, project.getSize());
            dataset.put(entity.getName(), data);
            System.out.println("  - Entité '" + entity.getName() + "' : " + data.size() + " lignes générées");
        }
        return dataset;
    }
}
