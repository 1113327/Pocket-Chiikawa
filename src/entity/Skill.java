package entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Skill {
    private String name;
    private int damage;
    private int pp;
    private String type;
    private String effectType;
    private BufferedImage spriteImage;

    public Skill(String name, int damage, int pp, String type, String effectType, String spriteImagePath) {
        this.name = name;
        this.damage = damage;
        this.pp = pp;
        this.type = type;
        this.effectType = effectType;
        try {
            this.spriteImage = ImageIO.read(new URL(spriteImagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getTypeMultiplier(String attackerType, String defenderType) {
        switch (attackerType) {
            case "fire":
                if (defenderType.equals("grass")) return 2.0;
                break;
            case "grass":
                if (defenderType.equals("water")) return 2.0;
                break;
            case "water":
                if (defenderType.equals("fire") || defenderType.equals("ground")) return 2.0;
                break;
            case "ground":
                if (defenderType.equals("electric")) return 2.0;
                break;
            case "electric":
                if (defenderType.equals("water")) return 2.0;
                break;
        }
        return 1.0;
    }

    public int calculateDamage(int attackerLevel, int attackerCurrentHealth, String defenderType) {
        // 計算傷害
        double levelFactor = 1 + attackerLevel / 10.0;
        double healthFactor = attackerCurrentHealth / 100.0;
        double typeMultiplier = getTypeMultiplier(this.type, defenderType);
        return (int) Math.floor(this.damage * levelFactor * healthFactor * typeMultiplier);
    }

    public void drawEffect(Graphics g, int startX, int startY, int endX, int endY, double progress, boolean flip) {
        int x = (int) (startX + (endX - startX) * progress);
        int y = (int) (startY + (endY - startY) * progress);
    
        if (flip) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.translate(x * 2, 0);
            g2d.scale(-1, 1);
        }
        g.drawImage(spriteImage, x, y, 40, 40, null);
        if (flip) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.scale(-1, 1); // 重置翻轉
            g2d.translate(-x * 2, 0);
        }
    }

    // Getters and setters for the fields
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getPp() {
        return pp;
    }

    public void setPp(int pp) {
        this.pp = pp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEffectType() {
        return effectType;
    }

    public void setEffectType(String effectType) {
        this.effectType = effectType;
    }

    public BufferedImage getSpriteImage() {
        return spriteImage;
    }

    public void setSpriteImage(BufferedImage spriteImage) {
        this.spriteImage = spriteImage;
    }
}
