package entity;

public class MonsterInfo {
    private final String name;
    private final String type;
    private final int minLevel;
    private final String imagePath;

    public MonsterInfo(String name, String type, int minLevel, String imagePath) {
        this.name = name;
        this.type = type;
        this.minLevel = minLevel;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public String getImagePath() {
        return imagePath;
    }
}