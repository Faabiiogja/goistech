package com.goistech.core.models;

import java.util.List;
import java.util.Map;

public class PokemonData {

    private int id;
    private String name;
    private String imageUrl;
    private int height;
    private int weight;
    private List<String> types;
    private List<String> abilities;
    private Map<String, Integer> stats;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }

    public List<String> getTypes() { return types; }
    public void setTypes(List<String> types) { this.types = types; }

    public List<String> getAbilities() { return abilities; }
    public void setAbilities(List<String> abilities) { this.abilities = abilities; }

    public Map<String, Integer> getStats() { return stats; }
    public void setStats(Map<String, Integer> stats) { this.stats = stats; }
}
