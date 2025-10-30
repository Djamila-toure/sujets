package model;

import java.util.List;

public class Project {
    private String name;
    private int size; // nombre de lignes/par entité
    private List<Entity> entities;

    public Project(String name, int size, List<Entity> entities) {
        this.name = name;
        this.size = size;
        this.entities = entities;
    }

    public String getName() { return name; }
    public int getSize() { return size; }
    public List<Entity> getEntities() { return entities; }
}
