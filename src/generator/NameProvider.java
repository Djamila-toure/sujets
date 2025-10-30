package generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NameProvider implements ValueProvider {
    private final List<String> names;
    private final Random random = new Random();

    public NameProvider(List<String> names) {
        this.names = names;
    }

    @Override
    public Object getValue() {
        if (names == null || names.isEmpty()) return "Name";
        return names.get(random.nextInt(names.size()));
    }

    @Override
    public List<Object> getValues(int count) {
        List<Object> out = new ArrayList<>();
        for (int i = 0; i < count; i++) out.add(getValue());
        return out;
    }
}
