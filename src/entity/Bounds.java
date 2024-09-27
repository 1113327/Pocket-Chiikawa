package entity;

import java.util.List;

public class Bounds {
    int x, y, width, height, minLevel, maxLevel;
    List<WildMonster> enemyMonsters;

    Bounds(int x, int y, int width, int height, int minLevel, int maxLevel, List<WildMonster> enemyMonsters) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.enemyMonsters = enemyMonsters;
    }
    public boolean contains(int targetX, int targetY) {
        return targetX >= x && targetX < x + width && targetY >= y && targetY < y + height;
    }

    public List<WildMonster> getMonsters() {
        return enemyMonsters;
    }
}