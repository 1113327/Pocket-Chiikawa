package entity;

public class WildMonster {
    int id;
    double probability;

    WildMonster(int id, double probability) {
        this.id = id;
        this.probability = probability;
    }

    public int getId() {
        return id;
    }

    public double getProbability() {
        return probability;
    }
    
}
