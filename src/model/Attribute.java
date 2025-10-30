package model;

import java.util.List;

public class Attribute {
    private String name;
    private String type; // "String", "Integer", "Float", "Boolean", "Date", "Enum"
    private Object minValue;
    private Object maxValue;
    private List<Object> enumValues;

    public Attribute(String name, String type, Object minValue, Object maxValue, List<Object> enumValues) {
        this.name = name;
        this.type = type;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.enumValues = enumValues;
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public Object getMinValue() { return minValue; }
    public Object getMaxValue() { return maxValue; }
    public List<Object> getEnumValues() { return enumValues; }

    public boolean hasEnumValues() {
        return enumValues != null && !enumValues.isEmpty();
    }
}
