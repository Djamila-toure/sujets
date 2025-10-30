package generator;

import java.util.List;
public interface ValueProvider {
    Object getValue();
    List<Object> getValues(int count);
}
