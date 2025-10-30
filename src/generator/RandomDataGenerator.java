package generator;

import model.Attribute;
import model.Entity;

import java.text.SimpleDateFormat;
import java.util.*;

public class RandomDataGenerator implements DataGenerator {
    private final Random random = new Random();
    private final NameProvider defaultNameProvider;

    public RandomDataGenerator() {
        List<String> names = Arrays.asList("Marc", "Djamila", "Aurore", "David", "Emma", "Franck");
        this.defaultNameProvider = new NameProvider(names);
    }

    @Override
    public List<List<Object>> generateData(Entity entity, int size) {
        List<List<Object>> rows = new ArrayList<>();
        List<Attribute> attrs = entity.getAttributes();

        for (int i = 0; i < size; i++) {
            List<Object> row = new ArrayList<>();
            for (Attribute attr : attrs) {
                row.add(generateValue(attr));
            }
            rows.add(row);
        }
        return rows;
    }

    private Object generateValue(Attribute attr) {
        if (attr.hasEnumValues()) {
            List<Object> ev = attr.getEnumValues();
            return ev.get(random.nextInt(ev.size()));
        }

        String t = attr.getType();
        if (t == null) t = "String";

        switch (t) {
            case "String":
                String lower = attr.getName().toLowerCase();
                if (lower.contains("nom") || lower.contains("name") || lower.contains("prenom")) {
                    return defaultNameProvider.getValue();
                }
                return "Str" + random.nextInt(10000);
            case "Integer":
                Integer minI = attr.getMinValue() instanceof Integer ? (Integer) attr.getMinValue() : 0;
                Integer maxI = attr.getMaxValue() instanceof Integer ? (Integer) attr.getMaxValue() : 100;
                if (maxI < minI) { int tmp = minI; minI = maxI; maxI = tmp; }
                return minI + random.nextInt(Math.max(1, maxI - minI + 1));
            case "Float":
                Float minF = attr.getMinValue() instanceof Float ? (Float) attr.getMinValue() : 0.0f;
                Float maxF = attr.getMaxValue() instanceof Float ? (Float) attr.getMaxValue() : 100.0f;
                if (maxF < minF) { float tmp = minF; minF = maxF; maxF = tmp; }
                return minF + random.nextFloat() * (maxF - minF);
            case "Boolean":
                return random.nextBoolean();
            case "Date":
                long now = System.currentTimeMillis();
                long offset = (long) (random.nextDouble() * 1000L * 60 * 60 * 24 * 365);
                Date d = new Date(now - offset);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.format(d);
            default:
                return null;
        }
    }
}
