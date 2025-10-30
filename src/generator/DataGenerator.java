package generator;

import model.Entity;

import java.util.List;

public interface DataGenerator {
    List<List<Object>> generateData(Entity entity, int size);
}
